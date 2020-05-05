package com.theapache64.gpm.commands.subcommands.install

import com.theapache64.gpm.commands.base.BaseCommand
import com.theapache64.gpm.di.components.DaggerInstallComponent
import com.theapache64.gpm.di.modules.GradleModule
import com.theapache64.gpm.utils.GpmConfig
import com.theapache64.gpm.utils.InputUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import picocli.CommandLine
import java.util.concurrent.Callable
import javax.inject.Inject

@CommandLine.Command(
    name = "install",
    aliases = ["i"],
    description = ["To install the dependency"]
)
class Install : BaseCommand<Int>() {

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
    lateinit var depName: String

    @Inject
    lateinit var installViewModel: InstallViewModel

    init {
        DaggerInstallComponent
            .builder()
            .gradleModule(GradleModule(true))
            .build()
            .inject(this)
    }

    override fun call(): Int = runBlocking {
        installViewModel.call(this@Install)
    }
}