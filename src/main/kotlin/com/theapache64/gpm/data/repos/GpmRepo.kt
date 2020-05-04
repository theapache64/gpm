package com.theapache64.gpm.data.repos

import com.theapache64.gpm.data.remote.gpm.GpmApiInterface
import com.theapache64.gpm.data.remote.gpm.models.GpmDependency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class GpmRepo @Inject constructor(
    private val gpmApiInterface: GpmApiInterface
) {

    /**
     * To get dependency from GPM github registry
     */
    suspend fun getDependency(dependencyName: String): GpmDependency? = try {
        gpmApiInterface.getDependency(dependencyName)
    } catch (e: HttpException) {
        null
    }
}