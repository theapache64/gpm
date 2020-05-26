package com.theapache64.gpm.commands.subcommands.install

import com.sun.org.apache.xpath.internal.operations.Bool
import com.theapache64.gpm.commands.base.BaseCommand
import com.theapache64.gpm.core.gm.GradleDep
import com.theapache64.gpm.data.remote.gpm.models.GpmDep
import com.theapache64.gpm.di.modules.CommandModule
import com.theapache64.gpm.di.modules.GradleModule
import com.theapache64.gpm.di.modules.TransactionModule
import kotlinx.coroutines.runBlocking
import picocli.CommandLine
import javax.inject.Inject

@CommandLine.Command(
    name = "install",
    aliases = ["i"],
    description = ["To install the dependency"]
)
class Install(isFromTest: Boolean = false) : BaseCommand<Int>(isFromTest) {

    @CommandLine.Option(
        names = ["-S", "--save"],
        description = ["To install the dependency as 'implementation'"]
    )
    var isSave: Boolean = false

    @CommandLine.Option(
        names = ["-FS", "--force-search"],
        description = ["To skip gpm registry search check and quick search with other repos"]
    )
    var isForceSearch: Boolean = false

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

    @CommandLine.Option(
        names = ["-K", "--kapt"],
        description = ["To install the dependency as 'kapt'"]
    )
    var isKapt: Boolean = false

    @CommandLine.Parameters(index = "0", description = ["Dependency name"])
    lateinit var depName: String

    @Inject
    lateinit var installViewModel: InstallViewModel

    init {
        DaggerInstallComponent
            .builder()
            .commandModule(CommandModule(isFromTest = false))
            .gradleModule(GradleModule(isFromTest = false))
            .transactionModule(TransactionModule(false))
            .build()
            .inject(this)
    }

    override fun call(): Int = runBlocking {
        installViewModel.call(this@Install)
    }

    fun onBeforeGetDep() {
        println("🔍 Searching for '$depName'")
    }

    fun onDepGot() {
        println("✔️ Found dependency")
    }

    fun onBeforeSearchingInGpmRegistry() {
        println("🔍 Searching in gpm registry for '$depName'...")
    }

    fun onBeforeSearchingInMavenCentral() {
        println("🔍 Searching in maven for '$depName'")
    }

    fun onDepNotFoundAnywhere() {
        println("❌ Couldn't find dependency with name '$depName'")
    }

    fun onBeforeAddDependency(depType: GradleDep.Type) {
        println("⌨️ Adding ${depType.key} to build.gradle...")
    }
}