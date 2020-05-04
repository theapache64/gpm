package com.theapache64.gpm.data.repos

import com.theapache64.gpm.data.remote.maven.MavenApiInterface
import com.theapache64.gpm.data.remote.maven.models.ArtifactInfo
import com.theapache64.gpm.data.remote.maven.models.SearchResult
import com.theapache64.gpm.utils.removeNewLinesAndMultipleSpaces
import org.apache.commons.text.StringEscapeUtils
import java.io.File
import java.text.SimpleDateFormat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MavenRepo @Inject constructor(
    private val mavenApiInterface: MavenApiInterface
) {
    companion object {
        private val SEARCH_RESULT_REGEX by lazy {
            "<div class=\"im\">.+?(?<number>\\d+?)\\. <\\/span><a href=\"\\/artifact\\/(?<groupId>.+?)\\/(?<artifactId>.+?)\">(?<name>.+?)<\\/a>.+?<b>(?<usages>.+?)<\\/b> usages<\\/a>.+?im-description\">(?<description>.+?)<div.+?Last Release on (?<lastRelease>.+?)<\\/div>".toRegex()
        }

        private val ARTIFACT_VERSION_REGEX by lazy {
            "<tr>.+?vbtn release\">(?<version>.+?)<\\/a><\\/td><td><a class=\"b lic\" href=\"(?<repoUrl>.+?)\">(?<repoName>.+?)<\\/a>".toRegex()
        }

        //Apr 29, 2020
        private val SEARCH_RESULT_DATE_FORMAT = SimpleDateFormat("MMM dd, yyyy")
    }

    suspend fun search(dependencyName: String): List<SearchResult> {

        val htmlResp = mavenApiInterface.search(dependencyName).removeNewLinesAndMultipleSpaces()
        val matches = SEARCH_RESULT_REGEX.findAll(htmlResp)
        val searchResults = mutableListOf<SearchResult>()
        for (match in matches) {

            val (
                number,
                groupId,
                artifactId,
                name,
                usages,
                description,
                lastRelease
            ) = match.destructured

            searchResults.add(
                SearchResult(
                    number.trim().toInt(),
                    name.trim(),
                    groupId.trim(),
                    artifactId.trim(),
                    StringEscapeUtils.unescapeHtml4(description.trim()),
                    usages.trim().replace(",", "").toInt(),
                    SEARCH_RESULT_DATE_FORMAT.parse(lastRelease.trim())
                )
            )
        }
        return searchResults
    }

    suspend fun getLatestVersion(groupId: String, artifactId: String): ArtifactInfo? {
        val htmlResp = mavenApiInterface.getArtifact(groupId, artifactId).removeNewLinesAndMultipleSpaces()
        val results = ARTIFACT_VERSION_REGEX.find(htmlResp)
        return if (results != null) {
            val (version, repoUrl, repoName) = results.destructured
            ArtifactInfo(version, repoName, repoUrl)
        } else {
            null
        }
    }


}