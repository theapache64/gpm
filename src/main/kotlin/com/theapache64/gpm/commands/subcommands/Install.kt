package com.theapache64.gpm.commands.subcommands

import picocli.CommandLine
import java.util.concurrent.Callable

@CommandLine.Command(
    name = "install",
    aliases = ["i"],
    description = ["To install the dependency"]
)
class Install : Callable<Int> {

    @CommandLine.Option(
        names = ["-S", "--save"],
        description = ["To install the dependency as 'implementation'"]
    )
    var isSave: Boolean? = null

    @CommandLine.Option(
        names = ["-D", "--save-dev"],
        description = ["To install the dependency as 'testImplementation'"]
    )
    var isSaveDev: Boolean? = null

    @CommandLine.Option(
        names = ["-DA", "--save-dev-android"],
        description = ["To install the dependency as 'androidTestImplementation'"]
    )
    var isSaveDevAndroid: Boolean? = null

    @CommandLine.Parameters(index = "0", description = ["Dependency name"])
    lateinit var dependencyName: String

    override fun call(): Int {

        // if all save flags are null, then isSave will be enabled
        if (isSave == null && isSaveDev == null && isSaveDevAndroid == null) {
            isSave = true
        }

        return 0
    }

}