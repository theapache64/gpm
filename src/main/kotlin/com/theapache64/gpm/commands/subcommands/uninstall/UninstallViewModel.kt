package com.theapache64.gpm.commands.subcommands.uninstall

import com.theapache64.gpm.commands.base.BaseInstallUninstallViewModel
import com.theapache64.gpm.core.TransactionManager
import com.theapache64.gpm.core.gm.GradleDep
import com.theapache64.gpm.core.gm.GradleManager
import javax.inject.Inject

class UninstallViewModel @Inject constructor(
    private val tm: TransactionManager,
    private val gradleManager: GradleManager
) : BaseInstallUninstallViewModel<Uninstall>() {

    companion object {
        const val RESULT_DEP_UNINSTALLED = 123
        const val RESULT_NO_DEP_INSTALLED = 134
    }

    override suspend fun call(command: Uninstall): Int {

        val depName = command.depName.trim().toLowerCase()

        val depTypes = getDepTypes(
            command.isSave,
            command.isSaveDev,
            command.isSaveDevAndroid,
            GradleDep.Type.IMP.key
        )

        for (depType in depTypes) {

            command.onBeforeDepRemove(depType, depName)
            val installedDeps = tm.getInstalled(depType.key, depName)
            println("Size is ${installedDeps.size}")

            if (installedDeps.isEmpty()) {
                command.onNoDepInstalled(depType, depName)
                return RESULT_NO_DEP_INSTALLED
            }

            val depToRemove = if (installedDeps.size > 1) {
                // multiple, choose one
                val selDepIndex = command.chooseIndex(installedDeps.map {
                    "${it.type} ${it.gpmDep.groupId}:${it.gpmDep.artifactId}"
                })

                installedDeps[selDepIndex]

            } else {
                installedDeps.first()
            }

            gradleManager.removeDep(depToRemove)
        }

        return RESULT_DEP_UNINSTALLED
    }

}