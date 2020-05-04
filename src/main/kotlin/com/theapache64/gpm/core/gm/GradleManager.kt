package com.theapache64.gpm.core.gm

import com.theapache64.gpm.data.remote.gpm.models.GpmDependency
import com.theapache64.gpm.utils.StringUtils
import com.theapache64.gpm.utils.insertAt
import java.io.File
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

        private const val KEY_DEPENDENCIES = "dependencies"
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
    @Throws(IndexOutOfBoundsException::class)
    fun addDependency(name: String, description: String, newDep: GradleDependency) {

        val fileContent = gradleFile.readText()

        if (fileContent.contains(KEY_DEPENDENCIES)) {

            val newDepSign = "\n\t//$name:$description\n\t${newDep.getFullSignature()}\n"

            // Appending dependency
            val depIndex = fileContent.indexOf(KEY_DEPENDENCIES)
            val openIndex = fileContent.indexOf('{', depIndex)
            val closingIndex = StringUtils.getClosingIndexOf(fileContent, '{', openIndex, '}')
            val newContent = fileContent.insertAt(closingIndex, newDepSign)
            gradleFile.writeText(newContent)


        } else {

            // Adding first dependency
            val firstDependency = """
                
                // Project Dependencies
                dependencies {
                
                    // $name : $description
                    ${newDep.getFullSignature()}
                }
                
            """.trimIndent()
            gradleFile.appendText(firstDependency)
        }
    }

}