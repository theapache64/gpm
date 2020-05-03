package com.theapache64.gpm.core.gm

import com.winterbe.expekt.should
import org.junit.Test

class GradleDependencyTest {

    @Test
    fun `Full signature for implementation`() {

        val gradleDependency = GradleDependency(
            GradleDependency.Type.IMP,
            "com.squareup.okhttp3",
            "okhttp",
            "4.6.0"
        )

        gradleDependency.getFullSignature().should.equal(
            "implementation 'com.squareup.okhttp3:okhttp:4.6.0'"
        )
    }

    @Test
    fun `Full signature for testImplementation`() {

        val gradleDependency = GradleDependency(
            GradleDependency.Type.TEST_IMP,
            "org.mockito",
            "mockito",
            "3.3.3"
        )

        gradleDependency.getFullSignature().should.equal(
            "testImplementation 'org.mockito:mockito:3.3.3'"
        )
    }

    @Test
    fun `Full signature for androidTestImplementation`() {

        val gradleDependency = GradleDependency(
            GradleDependency.Type.AND_TEST_IMP,
            "org.mockito",
            "mockito",
            "3.3.3"
        )

        gradleDependency.getFullSignature().should.equal(
            "androidTestImplementation 'org.mockito:mockito:3.3.3'"
        )
    }
}