package com.theapache64.gpm.commands.subcommands

import com.theapache64.gpm.commands.base.BaseViewModel
import com.theapache64.gpm.core.gm.GradleDependency
import com.theapache64.gpm.core.gm.GradleManager
import com.theapache64.gpm.data.remote.gpm.models.GpmDependency
import com.theapache64.gpm.data.remote.maven.models.SearchResult
import com.theapache64.gpm.data.repos.GpmRepo
import com.theapache64.gpm.data.repos.MavenRepo
import picocli.CommandLine
import javax.inject.Inject

class InstallViewModel @Inject constructor(
    private val gpmRepo: GpmRepo,
    private val mavenRepo: MavenRepo,
    private val gradleManager: GradleManager
) : BaseViewModel<Install>() {

    companion object {
        const val RESULT_REPO_FOUND = 200
        const val RESULT_REPO_NOT_FOUND = 404
    }

    override suspend fun call(command: Install): Int {

        val dependencyName = command.dependencyName.trim().toLowerCase()

        // first get from
        val gpmDependency = getDependency(command, dependencyName)
            ?: return RESULT_REPO_NOT_FOUND


        val depTypes = mutableListOf<GradleDependency.Type>().apply {

            if (command.isSave) {
                add(GradleDependency.Type.IMP)
            }

            if (command.isSaveDev) {
                add(GradleDependency.Type.TEST_IMP)
            }

            if (command.isSaveDevAndroid) {
                add(GradleDependency.Type.AND_TEST_IMP)
            }

            // Still empty
            if (isEmpty() && !gpmDependency.defaultType.isNullOrBlank()) {
                // setting default dependency
                val depType = GradleDependency.Type.values().find { it.keyword == gpmDependency.defaultType.trim() }
                    ?: throw IllegalArgumentException("Invalid default type '${gpmDependency.defaultType}'")

                add(depType)
            }

            // Still Empty?
            if (isEmpty()) {
                // adding default
                add(GradleDependency.Type.IMP)
            }
        }

        require(depTypes.isNotEmpty()) { "Dependency type can't be empty" }

        // Getting latest version

        // Adding each dependency
        for (depType in depTypes) {
            gradleManager.addDependency(
                GradleDependency(
                    depType,
                    gpmDependency.groupId,
                    gpmDependency.artifactId,
                    gpmDependency.version!!
                )
            )
        }

        return RESULT_REPO_FOUND
    }

    private suspend fun getDependency(install: Install, dependencyName: String): GpmDependency? {
        var gpmDependency = gpmRepo.getDependency(dependencyName)

        if (gpmDependency == null) {
            // Searching for maven
            gpmDependency = getFromMaven(install, dependencyName)
        }

        return gpmDependency
    }

    private suspend fun getFromMaven(install: Install, dependencyName: String): GpmDependency? {

        val mavenDeps = mavenRepo.search(dependencyName)

        if (mavenDeps.isNotEmpty()) {
            val mostUsed = mavenDeps.maxBy { it.usage }!!
            val selDepId = install.chooseIndex(
                mavenDeps.map {
                    val text = "${it.groupId}:${it.artifactId}"
                    if (it == mostUsed) {
                        // color text
                        CommandLine.Help.Ansi.AUTO.string("@|bold,green $text|@")
                    } else {
                        //normal text
                        text
                    }
                }
            )
            val selectedMavenDep = mavenDeps[selDepId - 1]

            // Getting last version
            val artifactInfo = mavenRepo.getLatestVersion(
                selectedMavenDep.groupId,
                selectedMavenDep.artifactId
            )

            require(artifactInfo != null) { "Failed to artifact information for $selectedMavenDep" }

            return GpmDependency(
                selectedMavenDep.artifactId,
                GradleDependency.Type.IMP.keyword,
                selectedMavenDep.url,
                artifactInfo.repoName,
                null,
                selectedMavenDep.groupId,
                selectedMavenDep.name,
                artifactInfo.version
            )
        } else {
            return null
        }
    }

}