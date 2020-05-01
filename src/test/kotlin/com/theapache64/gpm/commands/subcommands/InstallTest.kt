package com.theapache64.gpm.commands.subcommands

import com.theapache64.gpm.commands.Gpm
import com.winterbe.expekt.should
import org.junit.Test
import picocli.CommandLine
import java.io.PrintWriter
import java.io.StringWriter

class InstallTest {

    @Test
    fun test() {
        val gpm = Gpm()
        val cmd = CommandLine(gpm)
        cmd.out = PrintWriter(StringWriter())
        val exitCode = cmd.execute("install", "okhttp")
        exitCode.should.equal(100)
    }
}