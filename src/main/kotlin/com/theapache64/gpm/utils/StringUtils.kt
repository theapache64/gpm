package com.theapache64.gpm.utils


object StringUtils {

    fun getClosingIndexOf(text: String, openingChar: Char, openingIndex: Int, closingChar : Char): Int {
        var closePos: Int = openingIndex
        var counter = 1
        while (counter > 0) {
            val c: Char = text[++closePos]
            if (c == openingChar) {
                counter++
            } else if (c == closingChar) {
                counter--
            }
        }
        return closePos
    }

}