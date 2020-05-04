package com.theapache64.gpm.commands.subcommands

import com.theapache64.gpm.di.components.DaggerInstallComponent
import kotlinx.coroutines.runBlocking
import picocli.CommandLine
import java.util.concurrent.Callable
import javax.inject.Inject

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
    var isSave: Boolean = false

    @CommandLine.Option(
        names = ["-D", "--save-dev"],
        description = ["To install the dependency as 'testImplementation'"]
    )
    var isSaveDev: Boolean = false

    @CommandLine.Option(
        names = ["-DA", "--save-dev-android"],
        description = ["To install the dependency as 'androidTestImplementation'"]
    )
    var isSaveDevAndroid: Boolean = false

    @CommandLine.Parameters(index = "0", description = ["Dependency name"])
    lateinit var dependencyName: String

    @Inject
    lateinit var installViewModel: InstallViewModel

    init {
        DaggerInstallComponent.create().inject(this)
    }

    override fun call(): Int = runBlocking {
        installViewModel.call(this@Install)
    }

    fun chooseIndex(map: List<String>): Int {
        TODO("Not yet implemented")
    }

}