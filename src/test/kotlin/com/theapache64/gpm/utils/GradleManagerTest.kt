package com.theapache64.gpm.utils

import com.theapache64.gpm.core.TransactionManager
import com.theapache64.gpm.core.gm.GradleDependency
import com.theapache64.gpm.core.gm.GradleManager
import com.theapache64.gpm.data.remote.gpm.models.GpmDependency
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

    @InjectFromComponent
    lateinit var transactionManager: TransactionManager

    @Before
    fun setUp() {
        this.gradleManager = GradleManager(transactionManager, sampleGradleFile)
    }

    @Test
    fun whenGetDependenciesSize_then10() {
        gradleManager.parseDependencies().size.should.equal(37)
        gradleManager.addDependency(
            "my-artifact",
            GradleDependency.Type.IMP,
            GpmDependency(
                "myArtifact",
                GradleDependency.Type.IMP.keyword,
                "https://mylib.docs",
                "jcenter",
                "https://github.com/userx/myArtifact",
                "myGroup",
                "My Lib",
                "Some description",
                "1.0.0"
            )
        )
        gradleManager.parseDependencies().size.should.equal(38)
    }
}