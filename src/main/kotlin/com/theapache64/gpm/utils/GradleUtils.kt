package com.theapache64.gpm.utils

object GradleUtils {
    fun getFullSignature(
        typeKeyword: String,
        groupId: String,
        artifactId: String,
        version: String,
        isGradleKts: Boolean
    ): String {

        val quote = if (version.startsWith("$")) {
            "\""
        } else {
            "'"
        }

        return if (isGradleKts) {
            // kotlin script
            "$typeKeyword($quote$groupId:$artifactId:$version$quote)"
        } else {
            // normal gradle
            "$typeKeyword $quote$groupId:$artifactId:$version$quote"
        }
    }
}