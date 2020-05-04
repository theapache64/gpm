package com.theapache64.gpm.data.repos

import com.theapache64.gpm.data.remote.gpm.GpmApiInterface
import com.theapache64.gpm.data.remote.gpm.models.GpmDependency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class GpmRepo @Inject constructor(
    private val gpmApiInterface: GpmApiInterface,
    private val mavenRepo: MavenRepo
) {

    /**
     * To get dependency from GPM github registry
     */
    suspend fun getDependency(dependencyName: String): GpmDependency? =
        try {
            val dep = gpmApiInterface.getDependency(dependencyName)
            val versionInfo = mavenRepo.getLatestVersion(dep.groupId, dep.artifactId)
            require(versionInfo != null) { "Couldn't get version info for '$dependencyName'" }
            dep.version = versionInfo.version
            dep
        } catch (e: HttpException) {
            null
        }
}