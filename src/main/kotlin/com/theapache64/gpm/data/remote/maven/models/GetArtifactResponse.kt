package com.theapache64.gpm.data.remote.maven.models

import com.squareup.moshi.Json


data class GetArtifactResponse(
    @Json(name = "response")
    val response: Response,
    @Json(name = "responseHeader")
    val responseHeader: ResponseHeader,
    @Json(name = "spellcheck")
    val spellcheck: Spellcheck
) {
    data class Response(
        @Json(name = "docs")
        val docs: List<Doc>,
        @Json(name = "numFound")
        val numFound: Int, // 6
        @Json(name = "start")
        val start: Int // 0
    ) {
        data class Doc(
            @Json(name = "a")
            val a: String, // retrofit
            @Json(name = "ec")
            val ec: List<String>,
            @Json(name = "g")
            val g: String, // com.squareup.retrofit2
            @Json(name = "id")
            val id: String, // com.squareup.retrofit2:retrofit
            @Json(name = "latestVersion")
            val latestVersion: String, // 2.8.1
            @Json(name = "p")
            val p: String, // jar
            @Json(name = "repositoryId")
            val repositoryId: String, // central
            @Json(name = "text")
            val text: List<String>,
            @Json(name = "timestamp")
            val timestamp: Long, // 1585158234000
            @Json(name = "versionCount")
            val versionCount: Int // 20
        )
    }

    data class ResponseHeader(
        @Json(name = "params")
        val params: Params,
        @Json(name = "QTime")
        val qTime: Int, // 0
        @Json(name = "status")
        val status: Int // 0
    ) {
        data class Params(
            @Json(name = "core")
            val core: String,
            @Json(name = "fl")
            val fl: String, // id,g,a,latestVersion,p,ec,repositoryId,text,timestamp,versionCount
            @Json(name = "indent")
            val indent: String, // off
            @Json(name = "q")
            val q: String, // a:"retrofit"
            @Json(name = "rows")
            val rows: String, // 1
            @Json(name = "sort")
            val sort: String, // score desc,timestamp desc,g asc,a asc
            @Json(name = "spellcheck")
            val spellcheck: String, // true
            @Json(name = "spellcheck.count")
            val spellcheckCount: String, // 5
            @Json(name = "start")
            val start: String,
            @Json(name = "version")
            val version: String, // 2.2
            @Json(name = "wt")
            val wt: String // json
        )
    }

    data class Spellcheck(
        @Json(name = "suggestions")
        val suggestions: List<Any>
    )
}