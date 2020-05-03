package com.theapache64.gpm.utils

import com.theapache64.gpm.core.gm.GradleDependency
import com.theapache64.gpm.core.gm.GradleManager
import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import java.io.File

class GradleManagerTest {

    private lateinit var gradleManager: GradleManager
    private val sampleGradleFile = File("assets/sample.build.gradle")
    private val tempGradleFile = File("assets/build.gradle")

    @Before
    fun setUp() {
        tempGradleFile.delete()
        println(sampleGradleFile.absolutePath)
        sampleGradleFile.copyTo(tempGradleFile)
        this.gradleManager = GradleManager(tempGradleFile)
    }

    @Test
    fun whenGetDependenciesSize_then10() {
        gradleManager.parseDependencies().size.should.equal(37)
        gradleManager.addDependency(
            GradleDependency(
                GradleDependency.Type.IMP,
                "myGroup",
                "myArtifcat",
                "myVersion"
            )
        )
        gradleManager.parseDependencies().size.should.equal(38)
    }
}