package com.theapache64.gpm.commands.subcommands

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.theapache64.gpm.commands.subcommands.install.Install
import com.theapache64.gpm.commands.subcommands.install.InstallViewModel
import com.theapache64.gpm.core.gm.GradleDep
import com.theapache64.gpm.data.remote.gpm.GpmApiInterface
import com.theapache64.gpm.data.remote.gpm.models.GpmDep
import com.theapache64.gpm.data.remote.maven.MavenApiInterface
import com.theapache64.gpm.di.components.DaggerInstallComponent
import com.theapache64.gpm.di.components.InstallComponent
import com.theapache64.gpm.di.modules.GradleModule
import com.theapache64.gpm.di.modules.NetworkModule
import com.theapache64.gpm.runBlockingUnitTest
import com.winterbe.expekt.should
import it.cosenonjaviste.daggermock.DaggerMock
import it.cosenonjaviste.daggermock.InjectFromComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import picocli.CommandLine
import retrofit2.HttpException
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter

class InstallTest {

    companion object {
        private const val OKHTTP = "okhttp"
        private const val RETROFIT = "retrofit"
        private const val INVALID_REPO = "^%&^%some-invalid-repo&^%"
    }

    private lateinit var installCmd: CommandLine

    private val install = Install()

    @get:Rule
    val daggerRule = DaggerMock.rule<InstallComponent>(NetworkModule()) {
        customizeBuilder<DaggerInstallComponent.Builder> {
            it.gradleModule(GradleModule(true))
        }
        set {
            it.inject(install)
        }
    }

    @InjectFromComponent
    lateinit var tempBuildGradle: File

    private var fakeGpmApi: GpmApiInterface = mock()
    private var fakeMavenApi: MavenApiInterface = mock()

    @Before
    fun setUp() = runBlockingUnitTest {

        whenever(fakeGpmApi.getDependency(OKHTTP)).thenReturn(
            GpmDep(
                "okhttp",
                GradleDep.Type.IMP.key,
                "https://square.github.io/okhttp/",
                "jcenter",
                "https://github.com/square/okhttp/",
                "com.squareup.okhttp3",
                "OkHttp",
                "Square’s meticulous HTTP client for Java and Kotlin.\n",
                "4.6.0"
            )
        )

        whenever(fakeGpmApi.getDependency(RETROFIT)).thenReturn(
            GpmDep(
                "retrofit",
                GradleDep.Type.IMP.key,
                "https://square.github.io/retrofit/",
                "jcenter",
                "https://github.com/square/retrofit/",
                "com.squareup.retrofit2",
                "Retrofit",
                "Square’s meticulous type-safe HTTP client for Java and Kotlin.\n",
                "2.8.1"
            )
        )

        whenever(fakeMavenApi.getArtifact(any(), any())).thenReturn(
            File("src/test/resources/okhttp.mavenrepository.com.html").readText()
        )

        whenever(fakeMavenApi.search(INVALID_REPO)).thenReturn("")
        whenever(fakeGpmApi.getDependency(INVALID_REPO)).thenThrow(HttpException::class.java)

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
    fun `Install not existing library`() {
        val exitCode = installCmd.execute(INVALID_REPO)
        exitCode.should.equal(InstallViewModel.RESULT_REPO_NOT_FOUND)
    }

}