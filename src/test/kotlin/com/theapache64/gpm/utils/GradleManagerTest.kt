package com.theapache64.gpm.utils

import com.theapache64.gpm.commands.subcommands.install.InstallComponent
import com.theapache64.gpm.core.TransactionManager
import com.theapache64.gpm.core.gm.GradleDep
import com.theapache64.gpm.core.gm.GradleManager
import com.theapache64.gpm.data.remote.gpm.models.GpmDep
import com.theapache64.gpm.di.modules.GradleModule
import com.theapache64.gpm.di.modules.TransactionModule
import com.winterbe.expekt.should
import it.cosenonjaviste.daggermock.DaggerMock
import it.cosenonjaviste.daggermock.InjectFromComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

class GradleManagerTest {

    private lateinit var gradleManager: GradleManager
    private lateinit var gradleFile: File


    @get:Rule
    val daggerRule = DaggerMock.rule<InstallComponent>(
        GradleModule(true),
        TransactionModule(true)
    ) {
        set {
            gradleFile = it.gradleFile()
        }
    }

    @InjectFromComponent
    lateinit var tm: TransactionManager

    @Before
    fun setUp() {
        this.gradleManager = GradleManager(tm, gradleFile)
    }

    @Test
    fun whenGetDependenciesSize_then10() {
        gradleManager.parseDeps().size.should.equal(37)
        gradleManager.addDep(
            "my-artifact",
            GradleDep.Type.IMP,
            GpmDep(
                "myArtifact",
                GradleDep.Type.IMP.key,
                "https://mylib.docs",
                "jcenter",
                "https://github.com/userx/myArtifact",
                "myGroup",
                "My Lib",
                "Some description",
                "1.0.0"
            )
        )
        gradleManager.parseDeps().size.should.equal(38)
    }
}