package com.theapache64.gpm.utils

object GradleUtils {
    fun getFullSignature(
        typeKeyword: String,
        groupId: String,
        artifactId: String,
        version: String
    ): String {

        val quote = if (version.startsWith("$")) {
            "\""
        } else {
            "'"
        }

        return "$typeKeyword $quote$groupId:$artifactId:$version$quote"
    }
}