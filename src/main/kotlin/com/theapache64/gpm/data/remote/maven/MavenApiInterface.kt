package com.theapache64.gpm.data.remote.maven

import com.theapache64.gpm.data.remote.maven.models.GetArtifactResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MavenApiInterface {
    @GET("select?q=a:\"{artifactName}\"&rows=1&wt=json")
    suspend fun getArtifact(@Path("{artifactName}") artifactName: String): GetArtifactResponse
}