package com.theapache64.gpm.data.repos

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.theapache64.gpm.data.remote.gpm.GpmApiInterface
import com.theapache64.gpm.di.components.InstallComponent
import com.theapache64.gpm.di.modules.NetworkModule
import com.winterbe.expekt.should
import it.cosenonjaviste.daggermock.DaggerMock
import it.cosenonjaviste.daggermock.DaggerMockRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GpmRepoTest {

    private lateinit var gpmApiInterface: GpmApiInterface

    @get:Rule
    val daggerMockRule = DaggerMock.rule<InstallComponent>(NetworkModule()) {
        set {
            gpmApiInterface = it.gpmApiInterface()
        }
    }

    private val validRepoName = "okhttp"
    private val invalidRepoName = "fgsdfgsdfj"

    private lateinit var gmpRepo: GpmRepo

    @Before
    fun setUp() = runBlockingTest {
        gmpRepo = GpmRepo(gpmApiInterface)
    }

    @Test
    fun whenValidRepo_thenSuccess() = runBlocking {
        gmpRepo.getDependency(validRepoName).should.not.`null`
        Unit
    }


    @Test(expected = RuntimeException::class)
    fun whenValidRepo_thenError() = runBlockingTest {
        gmpRepo.getDependency(invalidRepoName).should.not.`null`
    }


}