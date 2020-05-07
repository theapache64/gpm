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

    @InjectFromComponent
    private lateinit var mavenRepo: MavenRepo

    private lateinit var gmpRepo: GpmRepo

    @Before
    fun setUp() {
        gmpRepo = GpmRepo(gpmApiInterface, mavenRepo)
    }


    @Test
    fun `Valid search`() = runBlockingUnitTest {
        val dep = gmpRepo.getDep("okhttp")
        dep.should.not.`null`
        dep!!.name.should.equal("OkHttp")
        dep.github.should.equal("square/okhttp")
        dep.docs.should.equal("https://square.github.io/okhttp/")
        dep.groupId.should.equal("com.squareup.okhttp3")
        dep.artifactId.should.equal("okhttp")
        dep.getFrom.should.equal("maven")
        dep.defaultType.should.equal("implementation")
    }

    @Test
    fun `Invalid search`() = runBlockingUnitTest {
        gmpRepo.getDep("fghdfghfgh").should.`null`
    }


}