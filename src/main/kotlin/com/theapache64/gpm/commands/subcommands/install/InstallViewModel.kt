package com.theapache64.gpm.commands.subcommands.install

import com.theapache64.gpm.commands.base.BaseInstallUninstallViewModel
import com.theapache64.gpm.core.gm.GradleDep
import com.theapache64.gpm.core.gm.GradleManager
import com.theapache64.gpm.data.remote.gpm.models.GpmDep
import com.theapache64.gpm.data.repos.GpmRepo
import com.theapache64.gpm.data.repos.MavenRepo
import com.theapache64.gpm.utils.GradleUtils
import picocli.CommandLine
import javax.inject.Inject

const val RESET_COLOR = "\u001b[0m" // Text Reset
const val GREEN_BOLD = "\u001b[1;32m" // GREEN

class InstallViewModel @Inject constructor(
    private val gpmRepo: GpmRepo,
    private val mavenRepo: MavenRepo,
    private val gradleManager: GradleManager
) : BaseInstallUninstallViewModel<Install>() {


    companion object {
        const val RESULT_DEP_INSTALLED = 200
        const val RESULT_REPO_NOT_FOUND = 404
    }

    override suspend fun call(command: Install): Int {

        val depName = command.depName.trim().toLowerCase()

        // first get from
        command.onBeforeGetDep()
        val gpmDep = getDep(command, depName)
            ?: return RESULT_REPO_NOT_FOUND

        command.onDepGot()
        val depTypes = getDepTypes(
            command.isSave,
            command.isSaveDev,
            command.isSaveDevAndroid,
            command.isKapt,
            gpmDep.defaultType
        )

        require(depTypes.isNotEmpty()) { "Dependency type can't be empty" }

        // Adding each dependency
        for (depType in depTypes) {
            if(command.isPrintOnly){
                // Only print. No files need to be modified
                val depSign = GradleUtils.getFullSignature(
                    depType.key,
                    gpmDep.groupId,
                    gpmDep.artifactId,
                    gpmDep.version!!,
                    isGradleKts = true
                )
                val coloredDepsSign = CommandLine.Help.Ansi.AUTO.string("@|bold,green $depSign|@")
                println("✅ -> $coloredDepsSign")
            }else{
                // Modify files
                command.onBeforeAddDependency(depType)
                val newlyAddedLines = gradleManager.addDep(
                    depName,
                    depType,
                    gpmDep
                )
                command.onAfterDependenciesAdded(newlyAddedLines)
            }
        }


        return RESULT_DEP_INSTALLED
    }

    private suspend fun getDep(
        install: Install,
        depName: String
    ): GpmDep? {
        var gpmDep = if (install.isForceSearch) {
            // dont look at gpm repo
            null
        } else {
            install.onBeforeSearchingInGpmRegistry()
            gpmRepo.getDep(depName)
        }

        if (gpmDep == null) {
            // Searching for maven
            install.onBeforeSearchingInMavenCentral()
            gpmDep = getFromMaven(install, depName)
        }

        if (gpmDep == null) {
            install.onDepNotFoundAnywhere()
        }

        return gpmDep
    }

    private suspend fun getFromMaven(install: Install, depName: String): GpmDep? {
        val mavenDeps = mavenRepo.search(depName)

        if (mavenDeps.isNotEmpty()) {

            val mostUsed = mavenDeps.maxByOrNull { it.usage ?: 0 }!!
            val selDepIndex = if (mavenDeps.size > 1) {

                val choosables = mavenDeps.map {
                    val text = "${it.groupId}:${it.artifactId}"
                    if (it == mostUsed) {
                        // color text
                        "$GREEN_BOLD$text${RESET_COLOR}"
                    } else {
                        //normal text
                        text
                    }
                }
                install.chooseIndex(choosables)
            } else {
                0
            }

            val selMavenDep = mavenDeps[selDepIndex]

            // Getting latest version
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