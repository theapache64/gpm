package com.theapache64.gpm.utils


object StringUtils {

    fun getClosingIndexOf(text: String, openingChar: Char, openingIndex: Int, closingChar: Char): Int {
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

    fun breakOnAndComment(charLimit: Int, input: String): String {
        if (input.length < charLimit) {
            return input
        }

        return getChunked(input, charLimit).joinToString("\n// ")
    }

    private fun getChunked(input: String, charLimit: Int): List<String> {
        val chunks = mutableListOf<String>()
        val words = input.split(" ")
        val builder = StringBuilder()
        for (word in words) {
            builder.append(word).append(" ")
            if (builder.length >= charLimit) {
                // move to chunk
                val newLine = builder.toString().trim()
                chunks.add(newLine)
                builder.clear()
            }
        }
        if (builder.isNotEmpty()) {
            chunks.add(builder.toString().trim())
        }
        return chunks
    }

}