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
            command.onBeforeAddDependency(depType)
            gradleManager.addDep(
                depName,
                depType,
                gpmDep
            )
        }


        return RESULT_DEP_INSTALLED
    }

    private suspend fun getDep(
        install: Install,
        depName: String
    ): GpmDep? {

        install.onBeforeSearchingInGpmRegistry()
        var gpmDep = gpmRepo.getDep(depName)

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

            val mostUsed = mavenDeps.maxBy { it.usage ?: 0 }!!
            val selDepIndex = if (mavenDeps.size > 1) {

                val index = install.chooseIndex(
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

                index
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