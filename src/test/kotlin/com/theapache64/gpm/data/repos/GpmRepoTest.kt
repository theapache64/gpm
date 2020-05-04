package com.theapache64.gpm.data.repos

import com.theapache64.gpm.data.remote.gpm.GpmApiInterface
import com.theapache64.gpm.rules.MyDaggerMockRule
import com.theapache64.gpm.runBlockingUnitTest
import com.winterbe.expekt.should
import it.cosenonjaviste.daggermock.InjectFromComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GpmRepoTest {

    @get:Rule
    val daggerMockRule = MyDaggerMockRule()

    @InjectFromComponent
    private lateinit var gpmApiInterface: GpmApiInterface

    private lateinit var gmpRepo: GpmRepo


    @Before
    fun setUp() {
        gmpRepo = GpmRepo(gpmApiInterface)
    }


    @Test
    fun `Valid search`() = runBlockingUnitTest {
        val dependency = gmpRepo.getDependency("okhttp")
        dependency.should.not.`null`
        dependency!!.name.should.equal("OkHttp")
        dependency.github.should.equal("square/okhttp")
        dependency.docs.should.equal("https://square.github.io/okhttp/")
        dependency.groupId.should.equal("com.squareup.okhttp3")
        dependency.artifactId.should.equal("okhttp")
        dependency.getFrom.should.equal("maven")
        dependency.defaultType.should.equal("implementation")
    }

    @Test
    fun `Invalid search`() = runBlockingUnitTest {
        gmpRepo.getDependency("fghdfghfgh").should.`null`
    }


}