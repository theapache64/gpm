package com.theapache64.gpm.data.repos

import com.theapache64.gpm.data.remote.maven.MavenApiInterface
import com.theapache64.gpm.data.remote.maven.models.ArtifactInfo
import com.theapache64.gpm.data.remote.maven.models.SearchResult
import com.theapache64.gpm.utils.removeNewLinesAndMultipleSpaces
import org.apache.commons.text.StringEscapeUtils
import java.text.SimpleDateFormat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MavenRepo @Inject constructor(
    private val mavenApiInterface: MavenApiInterface
) {
    companion object {
        private val SEARCH_RESULT_REGEX by lazy {
            "<div class=\"im\">.+?(?<number>\\d+?)\\. <\\/span><a href=\"\\/artifact\\/(?<groupId>.+?)\\/(?<artifactId>.+?)\">(?<name>.+?)<\\/a>.+?(?:<b>(?<usages>.+?)<\\/b> usages<\\/a>.+?)?im-description\">(?<description>.+?)<div.+?Last Release on (?<lastRelease>.+?)<\\/div".toRegex()
        }

        private val ARTIFACT_VERSION_REGEX by lazy {
            "<tr>.+?vbtn release\">(?<version>.+?)<\\/a>.+?<a class=\"b lic\" href=\"(?<repoUrl>.+?)\">(?<repoName>.+?)<\\/a>".toRegex()
        }

        //Apr 29, 2020
        private val SEARCH_RESULT_DATE_FORMAT = SimpleDateFormat("MMM dd, yyyy")
    }

    suspend fun search(depName: String): List<SearchResult> {
        val searchHtml = mavenApiInterface.search(depName)
        val htmlResp = searchHtml.removeNewLinesAndMultipleSpaces()
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

            val usage = usages.trim().replace(",", "").let {
                if (it.isEmpty()) {
                    null
                } else {
                    it.toInt()
                }
            }
            searchResults.add(
                SearchResult(
                    number.trim().toInt(),
                    name.trim(),
                    groupId.trim(),
                    artifactId.trim(),
                    StringEscapeUtils.unescapeHtml4(description.trim()),
                    usage,
                    SEARCH_RESULT_DATE_FORMAT.parse(lastRelease.trim())
                )
            )
        }
        return searchResults
    }

    /**
     * To get latest version of the given artifactId from the given group
     */
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