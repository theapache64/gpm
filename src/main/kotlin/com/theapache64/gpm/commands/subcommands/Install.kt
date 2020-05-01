package com.theapache64.gpm.commands.subcommands

import com.theapache64.gpm.data.repos.GpmRepo
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

    companion object {
        const val RESULT_REPO_FOUND = 200
        const val RESULT_REPO_NOT_FOUND = 404
    }

    @Inject
    lateinit var gpmRepo: GpmRepo

    init {
        DaggerInstallComponent.create().inject(this)
    }

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

    override fun call(): Int = runBlocking {

        dependencyName = dependencyName.trim().toLowerCase()

        // first get from
        val gpmDependency = gpmRepo.getDependency(dependencyName) ?: return@runBlocking RESULT_REPO_NOT_FOUND

        // if all save flags are null, then isSave will be enabled
        if (isSave == null && isSaveDev == null && isSaveDevAndroid == null) {
            isSave = true
        }

        RESULT_REPO_FOUND
    }

}