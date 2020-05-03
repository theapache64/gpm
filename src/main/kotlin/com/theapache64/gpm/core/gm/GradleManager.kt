package com.theapache64.gpm.core.gm

import java.io.File
import java.io.IOException
import java.lang.IllegalArgumentException

/**
 * Currently supports 'implementation' and 'testImplementation' only.
 */
class GradleManager constructor(
    private val gradleFile: File
) {


    companion object {
        private val DEPENDENCY_REGEX by lazy {
            "(?<type>androidTestImplementation|testImplementation|implementation)\\s*\\(?[\"'](?<groupId>.+?):(?<artifactId>.+?):(?<version>.+?)[\"']\\)?".toRegex()
        }
    }

    fun parseDependencies(): List<GradleDependency> {
        val deps = mutableListOf<GradleDependency>()
        val fileContent = gradleFile.readText()
        val matchResults = DEPENDENCY_REGEX.findAll(fileContent)

        for (result in matchResults) {

            val type = result.groups["type"]!!.value
            val groupId = result.groups["groupId"]!!.value
            val artifactId = result.groups["artifactId"]!!.value
            val version = result.groups["version"]!!.value

            val gdType = GradleDependency.Type.values().find { it.keyword == type }
                ?: throw IllegalArgumentException("Couldn't find dependency type for '$type.'")

            deps.add(
                GradleDependency(
                    gdType,
                    groupId,
                    artifactId,
                    version
                )
            )
        }

        return deps
    }

    /**
     * To add dependency
     */
    fun addDependency(newDep: GradleDependency) {
        var fileContent = gradleFile.readText()
        val lastDependency = DEPENDENCY_REGEX.findAll(fileContent).lastOrNull()
        if (lastDependency != null) {
            val fullMatch = lastDependency.value
            val matchPlusNew = "$fullMatch\n    ${newDep.getFullSignature()}"
            fileContent = fileContent.replace(fullMatch, matchPlusNew)
            gradleFile.writeText(fileContent)
        } else {
            throw IOException("Couldn't add dependency '$newDep'. Unable to find dependency ")
        }
    }

}