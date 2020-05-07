package com.theapache64.gpm.utils

fun String.removeNewLinesAndMultipleSpaces(): String {
    return this.replace("\n", "")
        .replace("\r", "")
        .replace("\\s{2,}".toRegex(), " ")
}

fun String.insertAt(position: Int, text: String): String {
    return this.substring(0, position) + text + this.substring(
        position,
        this.length
    )
}