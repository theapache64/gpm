package com.theapache64.gpm.utils

import com.theapache64.gpm.core.gm.GradleDependency
import com.winterbe.expekt.should
import org.junit.Assert.*
import org.junit.Test

class GradleUtilsTest {
    @Test
    fun `Full signature for implementation`() {

        val gradleDependency = GradleUtils.getFullSignature(
            GradleDependency.Type.IMP.keyword,
            "org.mockito",
            "mockito",
            "3.3.3"
        )

        gradleDependency.should.equal(
            "implementation 'org.mockito:mockito:3.3.3'"
        )
    }

    @Test
    fun `Full signature for testImplementation`() {

        val gradleDependency = GradleUtils.getFullSignature(
            GradleDependency.Type.TEST_IMP.keyword,
            "org.mockito",
            "mockito",
            "3.3.3"
        )

        gradleDependency.should.equal(
            "testImplementation 'org.mockito:mockito:3.3.3'"
        )
    }

    @Test
    fun `Full signature for androidTestImplementation`() {

        val gradleDependency = GradleUtils.getFullSignature(
            GradleDependency.Type.AND_TEST_IMP.keyword,
            "org.mockito",
            "mockito",
            "3.3.3"
        )

        gradleDependency.should.equal(
            "androidTestImplementation 'org.mockito:mockito:3.3.3'"
        )
    }
}