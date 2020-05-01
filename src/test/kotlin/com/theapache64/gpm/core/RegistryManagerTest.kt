package com.theapache64.gpm.core

import com.winterbe.expekt.should
import org.junit.Assert.*
import org.junit.Test

class RegistryManagerTest {
    @Test
    fun givenValidRepoName_whenGetDependency_thenReturnValidDependency() {
        val dependency = RegistryManager.getDependency("okhttp")
        dependency.should.not.`null`
    }
}