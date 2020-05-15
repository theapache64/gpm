package com.theapache64.gpm.commands.subcommands

import com.theapache64.gpm.commands.subcommands.install.DaggerInstallComponent
import com.theapache64.gpm.commands.subcommands.install.Install
import com.theapache64.gpm.commands.subcommands.install.InstallComponent
import com.theapache64.gpm.commands.subcommands.install.InstallViewModel
import com.theapache64.gpm.di.modules.CommandModule
import com.theapache64.gpm.di.modules.GradleModule
import com.theapache64.gpm.di.modules.NetworkModule
import com.theapache64.gpm.di.modules.TransactionModule
import com.theapache64.gpm.runBlockingUnitTest
import com.winterbe.expekt.should
import it.cosenonjaviste.daggermock.DaggerMock
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import picocli.CommandLine
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter

class InstallTest {


    private lateinit var installCmd: CommandLine
    private val install = Install(true)


    private lateinit var tempBuildGradle: File
    private lateinit var tempGpmJson: File

    @get:Rule
    val daggerRule = DaggerMock.rule<InstallComponent>(NetworkModule()) {
        customizeBuilder<DaggerInstallComponent.Builder> {
            it.gradleModule(GradleModule(isFromTest = true))
                .transactionModule(TransactionModule(true))
                .commandModule(CommandModule(true))
        }
        set {
            tempBuildGradle = it.gradleFile()
            tempGpmJson = it.gpmJsonFile()
            it.inject(install)
        }
    }


    @Before
    fun setUp() = runBlockingUnitTest {

        this.installCmd = CommandLine(install)
        installCmd.out = PrintWriter(StringWriter())
    }

    @Test
    fun `Install default`() {
        val exitCode = installCmd.execute("okhttp")
        exitCode.should.equal(InstallViewModel.RESULT_DEP_INSTALLED)
        tempBuildGradle.readText().should.contain("implementation 'com.squareup.okhttp3:okhttp:")
    }

    @Test
    fun `Install default another`() {
        val exitCode = installCmd.execute("progressbar")
        exitCode.should.equal(InstallViewModel.RESULT_DEP_INSTALLED)
        /*
        tempBuildGradle.readText().should.contain("implementation 'com.squareup.okhttp3:okhttp:")*/
    }

    @Test
    fun `Install non existing registry`() {
        val exitCode = installCmd.execute("retrofit")
        exitCode.should.equal(InstallViewModel.RESULT_DEP_INSTALLED)
        tempBuildGradle.readText().should.contain("implementation 'com.squareup.retrofit2:retrofit")
    }

    @Test
    fun `Install --save`() {
        val exitCode = installCmd.execute("--save", "okhttp")
        exitCode.should.equal(InstallViewModel.RESULT_DEP_INSTALLED)
        tempBuildGradle.readText().should.contain("implementation 'com.squareup.okhttp3:okhttp:")
    }

    @Test
    fun `Install --save-dev`() {
        val exitCode = installCmd.execute("--save-dev", "okhttp")
        exitCode.should.equal(InstallViewModel.RESULT_DEP_INSTALLED)
        tempBuildGradle.readText().should.contain("testImplementation 'com.squareup.okhttp3:okhttp:")
    }

    @Test
    fun `Install --save-dev-android`() {
        val exitCode = installCmd.execute("--save-dev-android", "okhttp")
        exitCode.should.equal(InstallViewModel.RESULT_DEP_INSTALLED)
        tempBuildGradle.readText().should.contain("androidTestImplementation 'com.squareup.okhttp3:okhttp:")
    }

    @Test
    fun `Install --kapt`() {
        val exitCode = installCmd.execute("--kapt", "dagger-compiler")
        exitCode.should.equal(InstallViewModel.RESULT_DEP_INSTALLED)
        tempBuildGradle.readText().should.contain("kapt 'com.squareup.dagger:dagger-compiler:")
    }


    @Test
    fun `Install not existing library`() {
        val exitCode = installCmd.execute("gdfhdfghdfghfdg")
        exitCode.should.equal(InstallViewModel.RESULT_REPO_NOT_FOUND)
    }

    @After
    fun tearDown() {
        tempBuildGradle.delete()
        tempGpmJson.delete()
    }

}