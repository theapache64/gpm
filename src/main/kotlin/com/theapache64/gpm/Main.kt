package com.theapache64.gpm

import com.theapache64.gpm.commands.gpm.Gpm
import picocli.CommandLine
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    val cmd = CommandLine(Gpm(false))
    if (args.isEmpty()) {
        cmd.usage(System.out)
    } else {
        val exitCode = cmd.execute(*args)
        exitProcess(exitCode)
    }
}