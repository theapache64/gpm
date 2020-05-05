package com.theapache64.gpm.commands.subcommands.uninstall

import com.theapache64.gpm.di.components.DaggerUninstallComponent
import com.theapache64.gpm.di.components.UninstallComponent
import kotlinx.coroutines.runBlocking
import picocli.CommandLine
import java.util.concurrent.Callable
import javax.inject.Inject

@CommandLine.Command(
    name = "uninstall",
    aliases = ["u"],
    description = ["To uninstall a dependency"]
)
class Uninstall : Callable<Int> {

    @CommandLine.Option(
        names = ["-S", "--save"],
        description = ["To uninstall the dependency defined as 'implementation'"]
    )
    var isSave: Boolean = false

    @CommandLine.Option(
        names = ["-D", "--save-dev"],
        description = ["To uninstall the dependency defined as 'testImplementation'"]
    )
    var isSaveDev: Boolean = false

    @CommandLine.Option(
        names = ["-DA", "--save-dev-android"],
        description = ["To uninstall the dependency defined as 'androidTestImplementation'"]
    )
    var isSaveDevAndroid: Boolean = false

    @CommandLine.Parameters(index = "0", description = ["Dependency name"])
    lateinit var dependencyName: String

    @Inject
    lateinit var uninstallViewModel: UninstallViewModel

    init {
        DaggerUninstallComponent.create().inject(this)
    }

    override fun call(): Int = runBlocking {
        uninstallViewModel.call(this@Uninstall)
    }
}