package com.theapache64.gpm.data.repos

import com.theapache64.gpm.data.remote.GpmApiInterface
import com.theapache64.gpm.models.Dependency
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GpmRepo @Inject constructor(
    private val gpmApiInterface: GpmApiInterface
) {
    /**
     * To get dependency from GPM github registry
     */
    suspend fun getDependency(dependencyName: String): Dependency? {
        return try {
            gpmApiInterface.getDependency(dependencyName)
        } catch (e: HttpException) {
            null
        }
    }
}