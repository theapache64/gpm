package com.theapache64.gpm.commands.subcommands.install

import com.theapache64.gpm.commands.base.BaseInstallUninstallViewModel
import com.theapache64.gpm.core.gm.GradleDep
import com.theapache64.gpm.core.gm.GradleManager
import com.theapache64.gpm.data.remote.gpm.models.GpmDep
import com.theapache64.gpm.data.repos.GpmRepo
import com.theapache64.gpm.data.repos.MavenRepo
import picocli.CommandLine
import javax.inject.Inject

class InstallViewModel @Inject constructor(
    private val gpmRepo: GpmRepo,
    private val mavenRepo: MavenRepo,
    private val gradleManager: GradleManager/*,
    private val progressBar: ProgressBar*/
) : BaseInstallUninstallViewModel<Install>() {

    companion object {
        const val RESULT_DEP_INSTALLED = 200
        const val RESULT_REPO_NOT_FOUND = 404
    }

    override suspend fun call(command: Install): Int {

        val depName = command.depName.trim().toLowerCase()

        // first get from
        //progressBar.set(20, "Searching '$depName'")
        val gpmDep = getDep(command, depName)
            ?: return RESULT_REPO_NOT_FOUND


        val depTypes = getDepTypes(
            command.isSave,
            command.isSaveDev,
            command.isSaveDevAndroid,
            gpmDep.defaultType
        )

        require(depTypes.isNotEmpty()) { "Dependency type can't be empty" }

        // Getting latest version

        // Adding each dependency
        //progressBar.set(50, "Installing '$depName'")
        for (depType in depTypes) {
            gradleManager.addDep(
                depName,
                depType,
                gpmDep
            )
        }

        //progressBar.set(100, "Done")
        return RESULT_DEP_INSTALLED
    }

    private suspend fun getDep(install: Install, depName: String): GpmDep? {
        var gpmDep = gpmRepo.getDep(depName)

        if (gpmDep == null) {
            // Searching for maven
            gpmDep = getFromMaven(install, depName)
        }

        return gpmDep
    }

    private suspend fun getFromMaven(install: Install, depName: String): GpmDep? {

        val mavenDeps = mavenRepo.search(depName)

        if (mavenDeps.isNotEmpty()) {
            val mostUsed = mavenDeps.maxBy { it.usage }!!
            val selDepIndex = install.chooseIndex(
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
            val selMavenDep = mavenDeps[selDepIndex]

            // Getting last version
            val artifactInfo = mavenRepo.getLatestVersion(
                selMavenDep.groupId,
                selMavenDep.artifactId
            )

            require(artifactInfo != null) { "Failed to artifact information for $selMavenDep" }

            return GpmDep(
                selMavenDep.artifactId,
                GradleDep.Type.IMP.key,
                selMavenDep.url,
                artifactInfo.repoName,
                null,
                selMavenDep.groupId,
                selMavenDep.name,
                selMavenDep.description,
                artifactInfo.version
            )
        } else {
            return null
        }
    }

}