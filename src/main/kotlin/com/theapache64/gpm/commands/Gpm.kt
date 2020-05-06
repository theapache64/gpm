package com.theapache64.gpm.commands

import com.theapache64.gpm.commands.subcommands.install.Install
import com.theapache64.gpm.commands.subcommands.uninstall.Uninstall
import picocli.CommandLine
import javax.inject.Inject
import javax.inject.Singleton

@CommandLine.Command(
    name = "gpm",
    version = ["v1.0.0-alpha01"],
    subcommands = [
        Install::class,
        Uninstall::class
    ]
)
@Singleton
class Gpm @Inject constructor() {
}