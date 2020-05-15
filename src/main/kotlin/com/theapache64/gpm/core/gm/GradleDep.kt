package com.theapache64.gpm.core.gm

data class GradleDep(
    val type: Type,
    val groupId: String,
    val artifactId: String,
    val version: String
) {
    enum class Type(val key: String) {
        IMP("implementation"),
        TEST_IMP("testImplementation"),
        AND_TEST_IMP("androidTestImplementation"),
        KAPT("kapt")
    }
}