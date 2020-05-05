package com.theapache64.gpm.commands

import com.theapache64.gpm.commands.subcommands.install.Install
import com.theapache64.gpm.commands.subcommands.uninstall.Uninstall
import picocli.CommandLine

@CommandLine.Command(
    name = "gpm",
    version = ["v1.0.0-alpha01"],
    subcommands = [
        Install::class,
        Uninstall::class
    ]
)
class Gpm {
}