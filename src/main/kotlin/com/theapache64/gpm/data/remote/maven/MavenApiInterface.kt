package com.theapache64.gpm.data.remote.maven

import retrofit2.http.GET
import retrofit2.http.Query

interface MavenApiInterface {

    @GET("search")
    suspend fun search(
        @Query("q") query: String
    ): String

}