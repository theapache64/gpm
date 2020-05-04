package com.theapache64.gpm.utils

import com.theapache64.gpm.core.gm.GradleDependency
import com.theapache64.gpm.core.gm.GradleManager
import com.theapache64.gpm.rules.MyDaggerMockRule
import com.winterbe.expekt.should
import it.cosenonjaviste.daggermock.InjectFromComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

class GradleManagerTest {

    private lateinit var gradleManager: GradleManager

    @get:Rule
    val daggerRule = MyDaggerMockRule()

    @InjectFromComponent
    lateinit var sampleGradleFile: File

    @Before
    fun setUp() {
        this.gradleManager = GradleManager(sampleGradleFile)
    }

    @Test
    fun whenGetDependenciesSize_then10() {
        gradleManager.parseDependencies().size.should.equal(37)
        gradleManager.addDependency(
            "My Artifact",
            "Library for doing some things",
            GradleDependency(
                GradleDependency.Type.IMP,
                "myGroup",
                "myArtifact",
                "myVersion"
            )
        )
        gradleManager.parseDependencies().size.should.equal(38)
    }
}