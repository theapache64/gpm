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

        // Uninstall dep
        val uninstallExitCode = cmd.execute("uninstall", "okhttp")
        uninstallExitCode.should.equal(UninstallViewModel.RESULT_DEP_UNINSTALLED)
    }

    @Test
    fun `Uninstall dependency which is manually added`() {
        // Uninstall dep
        val uninstallExitCode = cmd.execute("uninstall", "robolectric")
        uninstallExitCode.should.equal(UninstallViewModel.RESULT_NO_DEP_INSTALLED)
    }

    @Test
    fun `Uninstall not installed dependency`() {
        val uninstallExitCode = cmd.execute("uninstall", "invalid-library")
        uninstallExitCode.should.equal(UninstallViewModel.RESULT_NO_DEP_INSTALLED)
    }

    @Test
    fun `Uninstall dependency which installed through same dependency name`() {


        val uninstallExitCode = cmd.execute("uninstall", "same-name")
        uninstallExitCode.should.equal(UninstallViewModel.RESULT_DEP_UNINSTALLED)
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