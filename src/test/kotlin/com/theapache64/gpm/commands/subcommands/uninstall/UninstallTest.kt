package com.theapache64.gpm.commands.subcommands.uninstall

import com.theapache64.gpm.core.gm.GradleDep
import com.theapache64.gpm.core.gm.GradleManager
import com.theapache64.gpm.data.remote.gpm.models.GpmDep
import com.theapache64.gpm.di.components.DaggerUninstallComponent
import com.theapache64.gpm.di.components.UninstallComponent
import com.theapache64.gpm.di.modules.GradleModule
import com.theapache64.gpm.di.modules.TransactionModule
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

class UninstallTest {

    private lateinit var gm: GradleManager
    private lateinit var gpmJsonFile: File
    private lateinit var tempBuildGradle: File
    private lateinit var uninstallCmd: CommandLine
    private val uninstall = Uninstall(true)

    @get:Rule
    val daggerMock = DaggerMock.rule<UninstallComponent>() {
        customizeBuilder<DaggerUninstallComponent.Builder> {
            it.gradleModule(GradleModule(true))
                .transactionModule(TransactionModule(true))
        }
        set {
            tempBuildGradle = it.gradleFile()
            gpmJsonFile = it.gpmJsonFile()
            gm = it.gradleManager()
            it.inject(uninstall)
        }
    }


    @Before
    fun setUp() {
        this.uninstallCmd = CommandLine(uninstall).apply {
            out = PrintWriter(StringWriter())
        }
    }

    @After
    fun tearDown() {
        tempBuildGradle.delete()
        gpmJsonFile.delete()
    }

    @Test
    fun `Uninstall installed dependency`() {

        // Adding manual dependency
        gm.addDep(
            "materialcolors",
            GradleDep.Type.IMP,
            GpmDep(
                "materialcolors",
                GradleDep.Type.IMP.key,
                "https://materialcolors.github.io/materialcolors/",
                "jcenter",
                "https://github.com/square/materialcolors/",
                "com.theah64.materialcolors",
                "Material Colors",
                "A material color library",
                "1.0.0"
            )
        )


        // Uninstall dep
        val uninstallExitCode = uninstallCmd.execute("materialcolors")
        uninstallExitCode.should.equal(UninstallViewModel.RESULT_DEP_UNINSTALLED)
    }

    @Test
    fun `Uninstall dependency which is manually added`() {
        // Uninstall dep
        val uninstallExitCode = uninstallCmd.execute("robolectric")
        uninstallExitCode.should.equal(UninstallViewModel.RESULT_NO_DEP_INSTALLED)
    }

    @Test
    fun `Uninstall not installed dependency`() {
        val uninstallExitCode = uninstallCmd.execute("invalid-library")
        uninstallExitCode.should.equal(UninstallViewModel.RESULT_NO_DEP_INSTALLED)
    }

    @Test
    fun `Uninstall dependency which installed through same dependency name`() {

        gm.addDep(
            "same-name",
            GradleDep.Type.IMP,
            GpmDep(
                "same-name-1",
                GradleDep.Type.IMP.key,
                "",
                "",
                "",
                "com.theah64.same-name",
                "Same Name 1",
                "Same Name 1",
                "1.0.0"
            )
        )

        gm.addDep(
            "same-name",
            GradleDep.Type.IMP,
            GpmDep(
                "same-name-2",
                GradleDep.Type.IMP.key,
                "",
                "",
                "",
                "com.theah64.same-name",
                "Same Name 2",
                "Same Name 2",
                "1.0.0"
            )
        )


        val uninstallExitCode = uninstallCmd.execute("same-name")
        uninstallExitCode.should.equal(UninstallViewModel.RESULT_DEP_UNINSTALLED)
    }

    @Test
    fun `Uninstall testImplementation`() {

        val libName = "some-testing-library"
        gm.addDep(
            libName,
            GradleDep.Type.TEST_IMP,
            GpmDep(
                libName,
                GradleDep.Type.TEST_IMP.key,
                "",
                "",
                "",
                "com.theah64.some-testing-library",
                "Some Testing Library",
                "Some testing lib for JVM",
                "1.0.0"
            )
        )


        val uninstallExitCode = uninstallCmd.execute("--save-dev", libName)
        uninstallExitCode.should.equal(UninstallViewModel.RESULT_DEP_UNINSTALLED)
    }

    @Test
    fun `Uninstall androidTestImplementation`() {
        val libName = "some-testing-library"
        gm.addDep(
            libName,
            GradleDep.Type.AND_TEST_IMP,
            GpmDep(
                libName,
                GradleDep.Type.AND_TEST_IMP.key,
                "",
                "",
                "",
                "com.theah64.some-testing-library",
                "Some Testing Library",
                "Some testing lib for Android",
                "1.0.0"
            )
        )


        val uninstallExitCode = uninstallCmd.execute("--save-dev-android", libName)
        uninstallExitCode.should.equal(UninstallViewModel.RESULT_DEP_UNINSTALLED)
    }
}