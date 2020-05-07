package com.theapache64.gpm.utils

import com.winterbe.expekt.should
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
}