package com.theapache64.gpm.data.repos

import com.theapache64.gpm.data.remote.maven.MavenApiInterface
import com.theapache64.gpm.data.remote.gpm.models.GpmDependency
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MavenRepo @Inject constructor(
    private val mavenApiInterface: MavenApiInterface
) : BaseDependencyRepo {

    override suspend fun getDependency(dependencyName: String): GpmDependency? {
        TODO("")
        /*val result = mavenApiInterface.getArtifact(dependencyName)
        if (result.response.docs.isNotEmpty()) {
            // got artifact
            return GpmDependency(

            )
        }*/
    }

}