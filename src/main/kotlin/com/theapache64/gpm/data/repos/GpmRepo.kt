package com.theapache64.gpm.data.repos

import com.theapache64.gpm.data.remote.gpm.GpmApiInterface
import com.theapache64.gpm.data.remote.gpm.models.GpmDep
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GpmRepo @Inject constructor(
    private val gpmApiInterface: GpmApiInterface,
    private val mavenRepo: MavenRepo
) {

    /**
     * To get dependency from GPM github registry
     */
    suspend fun getDep(depName: String): GpmDep? {
        return try {
            val dep = gpmApiInterface.getDependency(depName)
            if (dep == null) {
                null
            } else {
                val versionInfo = mavenRepo.getLatestVersion(dep.groupId, dep.artifactId)
                require(versionInfo != null) { "Couldn't get version info for '$depName'" }
                dep.version = versionInfo.version
                dep
            }
        } catch (e: Exception) {
            null
        }
    }

}