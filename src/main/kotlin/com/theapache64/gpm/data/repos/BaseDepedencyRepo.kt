package com.theapache64.gpm.data.repos

import com.theapache64.gpm.data.remote.gpm.models.GpmDependency

interface BaseDependencyRepo {
    suspend fun getDependency(dependencyName: String): GpmDependency?
}