package com.theapache64.gpm.data.remote.gpm.models

import com.squareup.moshi.Json

data class GpmDependency(
    @Json(name = "artifactId")
    val artifactId: String, // okhttp
    @Json(name = "default_type")
    val defaultType: String?, // implementation
    @Json(name = "docs")
    val docs: String, // https://square.github.io/okhttp/
    @Json(name = "get_from")
    val getFrom: String, // maven
    @Json(name = "github")
    val github: String, // square/okhttp
    @Json(name = "groupId")
    val groupId: String, // com.squareup.okhttp3
    @Json(name = "name")
    val name: String // OkHttp
)