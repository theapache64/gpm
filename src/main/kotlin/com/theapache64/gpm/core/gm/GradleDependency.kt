package com.theapache64.gpm.core.gm

data class GradleDependency(
    val type: Type,
    val groupId: String,
    val artifactId: String,
    val version: String
) {
    enum class Type(val keyword: String) {
        IMP("implementation"),
        TEST_IMP("testImplementation"),
        AND_TEST_IMP("androidTestImplementation")
    }
}