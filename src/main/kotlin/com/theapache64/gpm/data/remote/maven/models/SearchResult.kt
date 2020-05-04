package com.theapache64.gpm.data.remote.maven.models

import java.util.*

data class SearchResult(
    val id: Int,
    val name: String,
    val groupId: String,
    val artifactId: String,
    val description: String,
    val usage: Int,
    val lastRelease: Date
) {
    val url = "https://mvnrepository.com/artifact/$groupId/$artifactId"
}

