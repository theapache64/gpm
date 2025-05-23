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
    version = ["v1.0.7"],
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

    @CommandLine.Parameters(
        index = "0",
        description = ["Module path or directory, separated and started by ':'. eg: :app , :feature:login"],
        defaultValue = ""
    )
    var modulePath: String? = null

    @Inject
    lateinit var viewModel: GpmViewModel

    override fun call(): Int = runBlocking {
        viewModel.call(this@Gpm)
    }
}