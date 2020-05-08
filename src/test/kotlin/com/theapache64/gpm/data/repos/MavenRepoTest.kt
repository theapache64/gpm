package com.theapache64.gpm.data.repos

import com.theapache64.gpm.data.remote.maven.MavenApiInterface
import com.theapache64.gpm.rules.MyDaggerMockRule
import com.theapache64.gpm.runBlockingUnitTest
import com.winterbe.expekt.should
import it.cosenonjaviste.daggermock.InjectFromComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException

class MavenRepoTest {

    @get:Rule
    val daggerMockRule = MyDaggerMockRule()

    @InjectFromComponent
    private lateinit var mavenApiInterface: MavenApiInterface

    private lateinit var mavenRepo: MavenRepo

    @Before
    fun setUp() {
        mavenRepo = MavenRepo(mavenApiInterface)
    }

    @Test
    fun `Valid search`() = runBlockingUnitTest {
        val dep = mavenRepo.search("okhttp")
        dep.size.should.above(1)
    }

    @Test
    fun `Invalid search`() = runBlockingUnitTest {
        val dep = mavenRepo.search("dsfgdfgdsf")
        dep.size.should.equal(0)
    }

    @Test
    fun `Getting valid artifact information`() = runBlockingUnitTest {
        val info = mavenRepo.getLatestVersion("org.junit.jupiter", "junit-jupiter-api")
        info.should.not.`null`
        info!!.version.should.not.empty
        info.repoName.should.equal("Central")
        info.repoUrl.should.equal("/repos/central")
    }

    @Test
    fun `Hey yo`() = runBlockingUnitTest {
        val info = mavenRepo.getLatestVersion("me.tongfei", "progressbar")
        info.should.not.`null`
        info!!.version.should.not.empty
    }


    @Test(expected = HttpException::class)
    fun `Getting invalid artifact information`() = runBlockingUnitTest {
        val info = mavenRepo.getLatestVersion("gfh", "jhj")
        info.should.`null`
    }

}