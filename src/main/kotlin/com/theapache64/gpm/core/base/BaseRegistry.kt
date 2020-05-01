package com.theapache64.gpm.core.base

import com.theapache64.gpm.models.Dependency

interface BaseRegistry {
    fun getDependencyUrl(name: String): String
    fun getDependency(name: String): Dependency?
}