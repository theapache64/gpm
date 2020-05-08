package com.theapache64.gpm.core.gm

import com.theapache64.gpm.core.TransactionManager
import com.theapache64.gpm.data.remote.gpm.models.GpmDep
import com.theapache64.gpm.models.GpmFileData
import com.theapache64.gpm.utils.GradleUtils
import com.theapache64.gpm.utils.StringUtils
import com.theapache64.gpm.utils.insertAt
import java.io.File
import java.io.IOException

/**
 * Currently supports 'implementation' and 'testImplementation' only.
 */
class GradleManager constructor(
    private val transactionManager: TransactionManager,
    private val gradleFile: File
) {

    companion object {
        private val DEP_REGEX by lazy {
            "(?<type>androidTestImplementation|testImplementation|implementation)\\s*\\(?[\"'](?<groupId>.+?):(?<artifactId>.+?):(?<version>.+?)[\"']\\)?".toRegex()
        }

        private const val KEY_DEP = "dependencies"
    }

    fun parseDeps(): List<GradleDep> {
        val deps = mutableListOf<GradleDep>()
        val fileContent = gradleFile.readText()
        val matchResults = DEP_REGEX.findAll(fileContent)

        for (result in matchResults) {

            val type = result.groups["type"]!!.value
            val groupId = result.groups["groupId"]!!.value
            val artifactId = result.groups["artifactId"]!!.value
            val version = result.groups["version"]!!.value

            val gdType = GradleDep.Type.values().find { it.key == type }
                ?: throw IllegalArgumentException("Couldn't find dependency type for '$type.'")

            deps.add(
                GradleDep(
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
    fun addDep(
        installedName: String,
        type: GradleDep.Type,
        newGpmDep: GpmDep
    ) {

        val fileContent = gradleFile.readText()
        val name = newGpmDep.name
        val description = newGpmDep.description


        val fullSignature = GradleUtils.getFullSignature(
            type.key,
            newGpmDep.groupId,
            newGpmDep.artifactId,
            newGpmDep.version!!
        )

        if (fileContent.contains(KEY_DEP)) {

            val newDepSign = "\n\t// $name:$description\n\t$fullSignature\n"

            // Appending dependency
            val depIndex = fileContent.indexOf(KEY_DEP)
            val openIndex = fileContent.indexOf('{', depIndex)
            val closingIndex = StringUtils.getClosingIndexOf(fileContent, '{', openIndex, '}')
            val newContent = fileContent.insertAt(closingIndex, newDepSign)
            gradleFile.writeText(newContent)


        } else {

            // Adding first dependency
            val firstDep = """
                
                // Project Dependencies
                dependencies {
                
                    // $name : $description
                    $fullSignature
                }
                
            """.trimIndent()
            gradleFile.appendText(firstDep)
        }

        transactionManager.add(installedName, type, newGpmDep)
    }

    /**
     * To remove dependency
     */
    fun removeDep(depToRemove: GpmFileData.AddedDep) {

        val name = depToRemove.gpmDep.name
        val description = depToRemove.gpmDep.description
        val groupId = depToRemove.gpmDep.groupId
        val artifactId = depToRemove.gpmDep.artifactId

        val depRegEx = ("(?:\\s*\\/\\/\\s$name:$description)?\n" +
                "\\s*${depToRemove.type}\\s*\\(?['\"]$groupId:$artifactId:.+?['\"]\\)?").toRegex()

        val fileContent = gradleFile.readText()
        val newFileContent = fileContent.replace(depRegEx, "")

        if (fileContent.length != newFileContent.length) {
            // dep removed, update gradle file
            gradleFile.writeText(newFileContent)

            // remove the transaction also
            transactionManager.remove(depToRemove)
        } else {
            throw IOException(
                """
                Failed to remove dependency. 
                Couldn't find `${depToRemove.type} '${groupId}:$artifactId:...'` in ${gradleFile.name}
                Signature might have changed.
            """.trimIndent()
            )
        }
    }

}