package com.theapache64.gpm.core.registries.gpm

import com.theapache64.gpm.models.Dependency
import retrofit2.http.GET

interface GpmApiInterface {

    @GET("https://raw.githubusercontent.com/theapache64/gpm/master/registry/{name}.json")
    suspend fun getDependency(): Dependency

}