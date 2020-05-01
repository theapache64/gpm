package com.theapache64.gpm.core.base

import com.theapache64.gpm.models.Dependency

interface BaseDependencyCollector {
    fun getDependency(name: String): Dependency?
}