package com.theapache64.gpm.core.gm

import com.theapache64.gpm.core.TransactionManager
import com.theapache64.gpm.data.remote.gpm.models.GpmDependency
import com.theapache64.gpm.utils.GradleUtils
import com.theapache64.gpm.utils.StringUtils
import com.theapache64.gpm.utils.insertAt
import java.io.File
import java.lang.IllegalArgumentException

/**
 * Currently supports 'implementation' and 'testImplementation' only.
 */
class GradleManager constructor(
    private val transactionManager: TransactionManager,
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
    fun addDependency(
        type: GradleDependency.Type,
        newGpmDep: GpmDependency
    ) {

        val fileContent = gradleFile.readText()
        val name = newGpmDep.name
        val description = newGpmDep.description


        val fullSignature = GradleUtils.getFullSignature(
            type.keyword,
            newGpmDep.groupId,
            newGpmDep.artifactId,
            newGpmDep.version!!
        )

        if (fileContent.contains(KEY_DEPENDENCIES)) {

            val newDepSign = "\n\t//$name:$description\n\t$fullSignature\n"

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
                    $fullSignature
                }
                
            """.trimIndent()
            gradleFile.appendText(firstDependency)
        }

        transactionManager.add(type, newGpmDep)
    }

}