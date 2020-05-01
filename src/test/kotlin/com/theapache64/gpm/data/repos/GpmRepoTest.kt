package com.theapache64.gpm.data.repos

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.theapache64.gpm.data.remote.GpmApiInterface
import com.winterbe.expekt.should
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GpmRepoTest {

    private val validRepoName = "okhttp"
    private val invalidRepoName = "fgsdfgsdfj"

    private lateinit var gmpRepo: GpmRepo

    @Before
    fun setUp() = runBlockingTest {
        val fakeApiInterface: GpmApiInterface = mock()

        whenever(fakeApiInterface.getDependency(validRepoName))
            .thenReturn(mock())

        whenever(fakeApiInterface.getDependency(invalidRepoName))
            .thenThrow(RuntimeException())

        gmpRepo = GpmRepo(fakeApiInterface)
    }

    @Test
    fun whenValidRepo_thenSuccess() = runBlockingTest {
        gmpRepo.getDependency(validRepoName).should.not.`null`
    }

    @Test(expected = RuntimeException::class)
    fun whenValidRepo_thenError() = runBlockingTest {
        gmpRepo.getDependency(invalidRepoName).should.not.`null`
    }


}