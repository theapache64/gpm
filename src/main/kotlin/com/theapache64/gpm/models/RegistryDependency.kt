package com.theapache64.gpm.models

import com.squareup.moshi.Json

data class RegistryDependency(
    @Json(name = "default_type")
    val defaultType: String, // implementation
    @Json(name = "dependency_signature")
    val dependencySignature: String, // com.squareup.okhttp3:okhttp
    @Json(name = "docs")
    val docs: String, // https://square.github.io/okhttp/
    @Json(name = "github")
    val github: String, // square/okhttp
    @Json(name = "latest_version")
    val latestVersion: String, // 4.6.0
    @Json(name = "name")
    val name: String // OkHttp
)