package com.theapache64.gpm.data.repos

import com.theapache64.gpm.data.remote.gpm.GpmApiInterface
import com.theapache64.gpm.data.remote.gpm.models.GpmDependency
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GpmRepo @Inject constructor(
    private val gpmApiInterface: GpmApiInterface
) : BaseDependencyRepo {
    /**
     * To get dependency from GPM github registry
     */
    override suspend fun getDependency(dependencyName: String): GpmDependency? {
        return try {
            gpmApiInterface.getDependency(dependencyName)
        } catch (e: HttpException) {
            null
        }
    }
}