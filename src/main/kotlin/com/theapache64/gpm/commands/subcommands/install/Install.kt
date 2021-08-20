package com.theapache64.gpm.commands.subcommands.install

import com.theapache64.gpm.commands.base.BaseCommand
import com.theapache64.gpm.commands.gpm.Gpm
import com.theapache64.gpm.core.gm.GradleDep
import com.theapache64.gpm.di.modules.CommandModule
import com.theapache64.gpm.di.modules.GradleModule
import com.theapache64.gpm.di.modules.TransactionModule
import com.theapache64.gpm.utils.StringUtils
import kotlinx.coroutines.runBlocking
import picocli.CommandLine
import javax.inject.Inject

@CommandLine.Command(
    name = "install",
    aliases = ["i"],
    description = ["To install the dependency"]
)
class Install(isFromTest: Boolean = false) : BaseCommand<Int>(isFromTest) {


    @CommandLine.ParentCommand
    var parent: Gpm? = null

    @CommandLine.Option(
        names = ["-S", "-s", "--save"],
        description = ["To install the dependency as 'implementation'"]
    )
    var isSave: Boolean = false

    @CommandLine.Option(
        names = ["-FS", "-fs", "--force-search"],
        description = ["To skip gpm registry search check and quick search with other repos"]
    )
    var isForceSearch: Boolean = true

    @CommandLine.Option(
        names = ["-D", "-d", "--save-dev"],
        description = ["To install the dependency as 'testImplementation'"]
    )
    var isSaveDev: Boolean = false

    @CommandLine.Option(
        names = ["-DA", "-da", "--save-dev-android"],
        description = ["To install the dependency as 'androidTestImplementation'"]
    )
    var isSaveDevAndroid: Boolean = false

    @CommandLine.Option(
        names = ["-K", "-k", "--kapt"],
        description = ["To install the dependency as 'kapt'"]
    )
    var isKapt: Boolean = false

    @CommandLine.Parameters(index = "0", description = ["Dependency name"])
    lateinit var depName: String

    @Inject
    lateinit var installViewModel: InstallViewModel
    private var modulePath: String? = null

    override fun call(): Int = runBlocking {
        modulePath = StringUtils.modulePathToFilePath(parent?.modulePath)
        DaggerInstallComponent
            .builder()
            .commandModule(CommandModule(isFromTest = false))
            .gradleModule(GradleModule(isFromTest = false, modulePath))
            .transactionModule(TransactionModule(false))
            .build()
            .inject(this@Install)

        installViewModel.call(this@Install)
    }

    fun onBeforeGetDep() {
        if (modulePath != null) {
            println("➡️ Module: $modulePath")
        }

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

    fun onAfterDependenciesAdded(newlyAddedLines: String) {
        val coloredLines = CommandLine.Help.Ansi.AUTO.string("@|bold,green $newlyAddedLines|@")
        println("✅ Added $coloredLines to build.gradle!")
    }
}
