package com.theapache64.gpm.commands.subcommands.uninstall

import com.theapache64.gpm.commands.base.BaseCommand
import com.theapache64.gpm.core.gm.GradleDep
import com.theapache64.gpm.di.modules.GradleModule
import com.theapache64.gpm.di.modules.TransactionModule
import kotlinx.coroutines.runBlocking
import picocli.CommandLine
import javax.inject.Inject

@CommandLine.Command(
    name = "uninstall",
    aliases = ["u"],
    description = ["To uninstall a dependency"]
)
class Uninstall(isFromTest: Boolean = false) : BaseCommand<Int>(isFromTest) {

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
    lateinit var depName: String

    @Inject
    lateinit var uninstallViewModel: UninstallViewModel

    init {
        DaggerUninstallComponent
            .builder()
            .gradleModule(GradleModule(isFromTest = false))
            .transactionModule(TransactionModule(isFromTest = false))
            .build()
            .inject(this)
    }

    override fun call(): Int = runBlocking {
        uninstallViewModel.call(this@Uninstall)
    }

    fun onNoDepInstalled(depType: GradleDep.Type, depName: String) {
        println("⚠️ No dependency named '$depName' installed as '${depType.key}' using gpm. You might have installed it manually.")
    }

    fun onBeforeDepRemove(depType: GradleDep.Type, depName: String) {
        println("🗑️ Removing '${depType.key}' of '$depName'...")
    }
}