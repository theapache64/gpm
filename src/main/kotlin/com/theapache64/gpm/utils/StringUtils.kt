package com.theapache64.gpm.utils

fun String.removeNewLinesAndMultipleSpaces(): String {
    return this.replace("\n", "")
        .replace("\r", "")
        .replace("\\s{2,}".toRegex(), " ")
}