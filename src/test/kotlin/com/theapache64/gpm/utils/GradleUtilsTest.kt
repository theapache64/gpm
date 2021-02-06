package com.theapache64.gpm.utils

import com.theapache64.gpm.core.gm.GradleDep
import com.winterbe.expekt.should
import org.junit.Test

class GradleUtilsTest {


    @Test
    fun `Full signature for implementation`() {

        val gradleDependency = GradleUtils.getFullSignature(
            GradleDep.Type.IMP.key,
            "org.mockito",
            "mockito",
            "3.3.3",
            false
        )

        gradleDependency.should.equal(
            "implementation 'org.mockito:mockito:3.3.3'"
        )
    }

    @Test
    fun `Full signature for testImplementation`() {

        val gradleDependency = GradleUtils.getFullSignature(
            GradleDep.Type.TEST_IMP.key,
            "org.mockito",
            "mockito",
            "3.3.3",
            false
        )

        gradleDependency.should.equal(
            "testImplementation 'org.mockito:mockito:3.3.3'"
        )
    }

    @Test
    fun `Full signature for androidTestImplementation`() {

        val gradleDependency = GradleUtils.getFullSignature(
            GradleDep.Type.AND_TEST_IMP.key,
            "org.mockito",
            "mockito",
            "3.3.3",
            false
        )

        gradleDependency.should.equal(
            "androidTestImplementation 'org.mockito:mockito:3.3.3'"
        )
    }


    @Test
    fun `Full signature for implementation - kts`() {

        val gradleDependency = GradleUtils.getFullSignature(
            GradleDep.Type.IMP.key,
            "org.mockito",
            "mockito",
            "3.3.3",
            true
        )

        gradleDependency.should.equal(
            "implementation(\"org.mockito:mockito:3.3.3\")"
        )
    }

    @Test
    fun `Full signature for testImplementation - kts`() {

        val gradleDependency = GradleUtils.getFullSignature(
            GradleDep.Type.TEST_IMP.key,
            "org.mockito",
            "mockito",
            "3.3.3",
            true
        )

        gradleDependency.should.equal(
            "testImplementation(\"org.mockito:mockito:3.3.3\")"
        )
    }

    @Test
    fun `Full signature for androidTestImplementation - kts`() {

        val gradleDependency = GradleUtils.getFullSignature(
            GradleDep.Type.AND_TEST_IMP.key,
            "org.mockito",
            "mockito",
            "3.3.3",
            true
        )

        gradleDependency.should.equal(
            "androidTestImplementation(\"org.mockito:mockito:3.3.3\")"
        )
    }
}