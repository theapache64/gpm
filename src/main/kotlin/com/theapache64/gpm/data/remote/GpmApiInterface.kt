package com.theapache64.gpm.data.remote

import com.theapache64.gpm.models.Dependency
import retrofit2.http.GET
import retrofit2.http.Path

interface GpmApiInterface {

    @GET("master/registry/{name}.json")
    suspend fun getDependency(
        @Path("name") repoName: String
    ): Dependency

}