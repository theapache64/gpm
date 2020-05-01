package com.theapache64.gpm.models

import com.google.gson.annotations.SerializedName


data class Dependency(
    @SerializedName("default_type")
    val defaultType: String, // implementation
    @SerializedName("dependency_signature")
    val dependencySignature: String, // com.squareup.okhttp3:okhttp
    @SerializedName("docs")
    val docs: String, // https://square.github.io/okhttp/
    @SerializedName("github")
    val github: String, // square/okhttp
    @SerializedName("latest_version")
    val latestVersion: String, // 4.6.0
    @SerializedName("name")
    val name: String // OkHttp
)