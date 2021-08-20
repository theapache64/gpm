package com.theapache64.gpm.utils

import com.winterbe.expekt.should
import org.junit.Assert.assertEquals
import org.junit.Test

class StringUtilsTest {

    @Test
    fun `Test nested braces`() {
        val input = """
            {
                x 
                {
                  y  
                }
            }
        """.trimIndent()

        // open = 13, close = 29
        val endingIndex = StringUtils.getClosingIndexOf(input, '{', 13, '}')
        endingIndex.should.equal(29)
    }


    @Test
    fun `Comment break - single-line`() {
        val input = """
            This library allows you to use your PC webcam.
        """.trimIndent()
        val expectedOutput = """
            This library allows you to use your PC webcam.
        """.trimIndent()
        val actualOutput = StringUtils.breakOnAndComment(80, input)
        assertEquals(actualOutput, expectedOutput)
    }

    @Test
    fun `Comment break - multiline`() {
        val input = """
            This library allows you to use your PC webcam, IP or network cameras directly from Java. It's compatible with most operating systems (Windows, Linux, MacOS).
        """.trimIndent()
        val expectedOutput = """
            This library allows you to use your PC webcam, IP or network cameras directly from
            // Java. It's compatible with most operating systems (Windows, Linux, MacOS).
        """.trimIndent()
        val actualOutput = StringUtils.breakOnAndComment(80, input)
        assertEquals(actualOutput, expectedOutput)
    }

    @Test
    fun `modulePath to filePath conversion`() {
        StringUtils.apply {
            modulePathToFilePath(null).should.`null`
            modulePathToFilePath("").should.`null`
            modulePathToFilePath(" ").should.`null`
            modulePathToFilePath(":").should.`null`
            modulePathToFilePath("app:feature").should.`null`
            modulePathToFilePath(":app").should.equal("app")
            modulePathToFilePath(":app:feature").should.equal("app/feature")
            modulePathToFilePath(":app:feature:x:y:z").should.equal("app/feature/x/y/z")
        }
    }
}