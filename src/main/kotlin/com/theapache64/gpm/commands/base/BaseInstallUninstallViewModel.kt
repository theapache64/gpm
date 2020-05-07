package com.theapache64.gpm.commands.base

import com.theapache64.gpm.core.gm.GradleDep

abstract class BaseInstallUninstallViewModel<T> : BaseViewModel<T>() {

    fun getDepTypes(
        isSave: Boolean,
        isSaveDev: Boolean,
        isSaveDevAndroid: Boolean,
        defaultType: String?
    ): List<GradleDep.Type> = mutableListOf<GradleDep.Type>().apply {

        if (isSave) {
            add(GradleDep.Type.IMP)
        }

        if (isSaveDev) {
            add(GradleDep.Type.TEST_IMP)
        }

        if (isSaveDevAndroid) {
            add(GradleDep.Type.AND_TEST_IMP)
        }

        // Still empty
        if (isEmpty() && !defaultType.isNullOrBlank()) {
            // setting default dependency
            val depType = GradleDep.Type.values().find { it.key == defaultType.trim() }
                ?: throw IllegalArgumentException("Invalid default type '${defaultType}'")

            add(depType)
        }

        // Still Empty?
        if (isEmpty()) {
            // adding default
            add(GradleDep.Type.IMP)
        }
    }

}