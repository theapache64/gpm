package com.theapache64.gpm.commands.subcommands

import com.theapache64.gpm.commands.Gpm
import com.theapache64.gpm.rules.MyDaggerMockRule
import com.winterbe.expekt.should
import it.cosenonjaviste.daggermock.InjectFromComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import picocli.CommandLine
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter

class InstallTest {

    private lateinit var cmd: CommandLine

    @get:Rule
    val daggerRule = MyDaggerMockRule()

    @InjectFromComponent
    lateinit var tempBuildGradle: File

    @Before
    fun setUp() {
        val gpm = Gpm()
        this.cmd = CommandLine(gpm)
        cmd.out = PrintWriter(StringWriter())
    }

    @Test
    fun `Install default`() {
        val exitCode = cmd.execute("install", "okhttp")
        exitCode.should.equal(InstallViewModel.RESULT_REPO_FOUND)
        tempBuildGradle.readText().should.contain("implementation 'com.squareup.okhttp3:okhttp:")
    }

    @Test
    fun `Install non existing registry`() {
        val exitCode = cmd.execute("install", "retrofit")
        exitCode.should.equal(InstallViewModel.RESULT_REPO_FOUND)
        tempBuildGradle.readText().should.contain("implementation 'com.squareup.retrofit2:retrofit")
    }

    @Test
    fun `Install --save`() {
        val exitCode = cmd.execute("install", "--save", "okhttp")
        exitCode.should.equal(InstallViewModel.RESULT_REPO_FOUND)
        tempBuildGradle.readText().should.contain("implementation 'com.squareup.okhttp3:okhttp:")
    }

    @Test
    fun `Install --save-dev`() {
        val exitCode = cmd.execute("install", "--save-dev", "okhttp")
        exitCode.should.equal(InstallViewModel.RESULT_REPO_FOUND)
        tempBuildGradle.readText().should.contain("testImplementation 'com.squareup.okhttp3:okhttp:")
    }

    @Test
    fun `Install --save-dev-android`() {
        val exitCode = cmd.execute("install", "--save-dev-android", "okhttp")
        exitCode.should.equal(InstallViewModel.RESULT_REPO_FOUND)
        tempBuildGradle.readText().should.contain("androidTestImplementation 'com.squareup.okhttp3:okhttp:")
    }

    @Test
    fun `Install not existing library`() {
        val exitCode = cmd.execute("install", "fghdfghfgh")
        exitCode.should.equal(InstallViewModel.RESULT_REPO_NOT_FOUND)
    }


    @Test
    fun whenInvalidRepoInstall_thenError() {
        val exitCode = cmd.execute("install", "ghdfghfgh")
        exitCode.should.equal(InstallViewModel.RESULT_REPO_NOT_FOUND)
    }
}