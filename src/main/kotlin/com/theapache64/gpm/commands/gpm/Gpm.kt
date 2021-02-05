package com.theapache64.gpm.commands.gpm

import com.theapache64.gpm.commands.base.BaseCommand
import com.theapache64.gpm.commands.subcommands.docs.Docs
import com.theapache64.gpm.commands.subcommands.install.Install
import com.theapache64.gpm.commands.subcommands.uninstall.Uninstall
import kotlinx.coroutines.runBlocking
import picocli.CommandLine
import javax.inject.Inject
import javax.inject.Singleton

@CommandLine.Command(
    name = "gpm",
    version = ["v1.0.1"],
    mixinStandardHelpOptions = true,
    subcommands = [
        Install::class,
        Uninstall::class,
        Docs::class
    ]
)
@Singleton
class Gpm constructor(isFromTest: Boolean = false) : BaseCommand<Int>(isFromTest) {

    init {
        DaggerGpmComponent.create().inject(this)
    }

    @Inject
    lateinit var viewModel: GpmViewModel

    override fun call(): Int = runBlocking {
        viewModel.call(this@Gpm)
    }
}