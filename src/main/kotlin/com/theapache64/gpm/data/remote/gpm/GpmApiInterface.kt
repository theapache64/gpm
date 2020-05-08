package com.theapache64.gpm.data.remote.gpm

import com.theapache64.gpm.data.remote.gpm.models.GpmDep
import retrofit2.http.GET
import retrofit2.http.Path

interface GpmApiInterface {

    @GET("registry/{name}.json")
    suspend fun getDependency(
        @Path("name") repoName: String
    ): GpmDep?

}