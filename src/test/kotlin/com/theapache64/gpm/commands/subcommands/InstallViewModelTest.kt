package com.theapache64.gpm.commands.subcommands

import com.theapache64.gpm.commands.Gpm
import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import picocli.CommandLine
import java.io.PrintWriter
import java.io.StringWriter

class InstallViewModelTest {

    private lateinit var cmd: CommandLine

    @Before
    fun setUp() {
        val gpm = Gpm()
        this.cmd = CommandLine(gpm)
        cmd.out = PrintWriter(StringWriter())
    }

    @Test
    fun whenValidRepoInstall_thenSuccess() {
        val exitCode = cmd.execute("install", "okhttp")
        exitCode.should.equal(InstallViewModel.RESULT_REPO_FOUND)
    }

    @Test
    fun whenInvalidRepoInstall_thenError() {
        val exitCode = cmd.execute("install", "ghdfghfgh")
        exitCode.should.equal(InstallViewModel.RESULT_REPO_NOT_FOUND)
    }
}