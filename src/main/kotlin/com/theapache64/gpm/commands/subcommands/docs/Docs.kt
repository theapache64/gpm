package com.theapache64.gpm.commands.subcommands.docs

import com.theapache64.gpm.commands.base.BaseCommand
import com.theapache64.gpm.di.modules.TransactionModule
import kotlinx.coroutines.runBlocking
import picocli.CommandLine
import javax.inject.Inject

@CommandLine.Command(
    name = "docs",
    aliases = ["d"],
    description = ["To open library docs"]
)
class Docs(
    isFromTest: Boolean = false
) : BaseCommand<Int>(isFromTest) {
    @CommandLine.Parameters(index = "0", description = ["Dependency name"])
    lateinit var depName: String

    @Inject
    lateinit var docsViewModel: DocsViewModel

    init {
        DaggerDocsComponent.builder()
            .transactionModule(TransactionModule(isFromTest))
            .build()
            .inject(this)
    }

    override fun call(): Int = runBlocking {
        docsViewModel.call(this@Docs)
    }

    fun onDocUrl(docsUrl: String) {
        println("Docs -> $docsUrl")
    }
}