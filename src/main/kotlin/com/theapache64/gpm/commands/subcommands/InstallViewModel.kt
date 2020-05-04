package com.theapache64.gpm.commands.subcommands

import com.theapache64.gpm.commands.base.BaseViewModel
import com.theapache64.gpm.core.gm.GradleDependency
import com.theapache64.gpm.core.gm.GradleManager
import com.theapache64.gpm.data.remote.gpm.models.GpmDependency
import com.theapache64.gpm.data.remote.maven.models.SearchResult
import com.theapache64.gpm.data.repos.GpmRepo
import com.theapache64.gpm.data.repos.MavenRepo
import javax.inject.Inject

class InstallViewModel @Inject constructor(
    private val gpmRepo: GpmRepo,
    private val mavenRepo: MavenRepo,
    private val gradleManager: GradleManager
) : BaseViewModel<Install>() {

    companion object {
        const val RESULT_REPO_FOUND = 200
        const val RESULT_REPO_NOT_FOUND = 404
        const val RESULT_REPO_DAMAGED = 666
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
                    ""
                )
            )
        }


        return RESULT_REPO_FOUND
    }

    private suspend fun getDependency(install: Install, dependencyName: String): GpmDependency? {
        var gpmDependency = gpmRepo.getDependency(dependencyName)

        /*if (gpmDependency == null) {
            val mavenDeps = mavenRepo.getDependencies(dependencyName)
            val depIndex = install.chooseIndex(
                mavenDeps.map { "${it.groupId}:${it.artifactId}:${it.latestVersion}" }
            )

        }*/

        return gpmDependency
    }

}