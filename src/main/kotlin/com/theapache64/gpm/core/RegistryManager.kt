package com.theapache64.gpm.core

import com.theapache64.gpm.core.registries.gpm.GpmRegistry
import com.theapache64.gpm.models.RegistryDependency

object RegistryManager {

    private val dependencyCollectors = listOf(
        GpmRegistry
    )

    /**
     * To get dependency from the available collectors
     */
    fun getDependency(name: String): RegistryDependency? {

        for (depCol in dependencyCollectors) {
            val dependency = depCol.getDependency(name)
            if (dependency != null) {
                return dependency
            }
        }

        return null
    }

}