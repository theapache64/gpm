package com.theapache64.gpm.core

import com.theapache64.gpm.models.Dependency

object RegistryManager {


    private val dependencyCollectors = listOf(
        GpmDependencyCollector
    )

    /**
     * To get dependency from the available collectors
     */
    fun getDependency(name: String): Dependency? {

        for (depCol in dependencyCollectors) {
            val dependency = depCol.getDependency(name)
            if (dependency != null) {
                return dependency
            }
        }

        return null
    }

}