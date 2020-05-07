package com.theapache64.gpm.commands.subcommands.docs

import com.theapache64.gpm.core.TransactionManager
import com.theapache64.gpm.core.gm.GradleDep
import com.theapache64.gpm.data.remote.gpm.models.GpmDep
import com.theapache64.gpm.di.modules.TransactionModule
import com.winterbe.expekt.should
import it.cosenonjaviste.daggermock.DaggerMock
import it.cosenonjaviste.daggermock.InjectFromComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import picocli.CommandLine

class DocsTest {

    private lateinit var docsCmd: CommandLine
    private val docs = Docs(true)

    @InjectFromComponent
    private lateinit var tm: TransactionManager

    @get:Rule
    val daggerRule = DaggerMock.rule<DocsComponent>(TransactionModule(true)) {
        set {
            tm = it.transactionManager()
        }
    }

    @Before
    fun setUp() {
        this.docsCmd = CommandLine(docs)
    }

    @Test
    fun `Opening docs for installed dependency`() {
        // Adding fake transaction
        tm.add(
            "okhttp",
            GradleDep.Type.IMP,
            GpmDep(
                "okhttp",
                GradleDep.Type.IMP.key,
                "https://square.github.io/okhttp/",
                "jcenter",
                "https://github.com/square/okhttp/",
                "com.square.okhttp3",
                "Material Colors",
                "A networking library",
                "1.0.0"
            )
        )

        val exitCode = docsCmd.execute("okhttp")
        exitCode.should.equal(DocsViewModel.RESULT_DOC_FOUND)

    }

    @Test
    fun `Opening docs for not installed dependency`() {

    }

    @Test
    fun `Opening docs for dependency installed with similar name`() {

    }
}