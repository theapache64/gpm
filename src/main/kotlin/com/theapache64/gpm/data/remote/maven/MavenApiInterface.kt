package com.theapache64.gpm.data.remote.maven

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MavenApiInterface {

    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Header("User-Agent") userAgent : String = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36"
    ): String

    @GET("artifact/{groupId}/{artifactId}")
    suspend fun getArtifact(
        @Path("groupId") groupId: String,
        @Path("artifactId") artifactId: String,
        @Header("User-Agent") userAgent : String = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36"
    ): String

}