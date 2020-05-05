package com.theapache64.gpm.commands.subcommands.uninstall

import com.theapache64.gpm.commands.Gpm
import com.theapache64.gpm.commands.subcommands.install.InstallViewModel
import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import picocli.CommandLine
import java.io.PrintWriter
import java.io.StringWriter

class UninstallTest {

    private lateinit var cmd: CommandLine

    @Before
    fun setUp() {
        this.cmd = CommandLine(Gpm()).apply {
            out = PrintWriter(StringWriter())
        }
    }

    @Test
    fun `Uninstall installed dependency`() {
        // Install dependency
        val installExitCode = cmd.execute("install", "okhttp")
        installExitCode.should.equal(InstallViewModel.RESULT_DEP_INSTALLED)
    }

    @Test
    fun `Uninstall dependency which is manually added`() {

    }

    @Test
    fun `Uninstall not installed dependency`() {

    }

    @Test
    fun `Uninstall dependency which installed through same dependency name`() {

    }

    @Test
    fun `Uninstall testImplementation`() {

    }

    @Test
    fun `Uninstall androidTestImplementation`() {

    }

    @Test
    fun `Uninstall modified dependency`() {

    }
}