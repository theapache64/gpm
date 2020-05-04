package com.theapache64.gpm.commands.subcommands

import com.theapache64.gpm.di.components.DaggerInstallComponent
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

    suspend fun chooseIndex(items: List<String>): Int {
        println("Choose: ")
        items.forEachIndexed() { index: Int, string: String ->
            println("${index + 1}) $string")
        }
        @Suppress("ConstantConditionIf")
        return if (GpmConfig.IS_DEBUG_MODE) {
            delay(1000)
            val randomInput = (1..items.size).random()
            println("Random Input: $randomInput")
            randomInput
        } else {
            InputUtils.getInt("Choose one", 1, items.size)
        }
    }

}