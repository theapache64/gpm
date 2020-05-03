package com.theapache64.gpm.core.base

import com.theapache64.gpm.data.remote.gpm.models.GpmDependency

interface BaseRegistry {
    fun getDependency(name: String): GpmDependency?
}