package com.theapache64.gpm.commands.subcommands

import com.theapache64.gpm.core.gm.GradleDependency
import com.theapache64.gpm.data.repos.GpmRepo
import com.theapache64.gpm.di.components.DaggerInstallComponent
import com.theapache64.gpm.core.gm.GradleManager
import kotlinx.coroutines.runBlocking
import picocli.CommandLine
import java.lang.IllegalArgumentException
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
        const val RESULT_REPO_DAMAGED = 666
    }

    @Inject
    lateinit var gpmRepo: GpmRepo

    @Inject
    lateinit var gradleManager: GradleManager

    init {
        DaggerInstallComponent.create().inject(this)
    }

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

    override fun call(): Int = runBlocking {

        dependencyName = dependencyName.trim().toLowerCase()

        // first get from
        val gpmDependency = gpmRepo.getDependency(dependencyName)
            ?: return@runBlocking RESULT_REPO_NOT_FOUND

        val depTypes = mutableListOf<GradleDependency.Type>().apply {

            if (isSave) {
                add(GradleDependency.Type.IMP)
            }

            if (isSaveDev) {
                add(GradleDependency.Type.TEST_IMP)
            }

            if (isSaveDevAndroid) {
                add(GradleDependency.Type.AND_TEST_IMP)
            }

            // Still empty
            if (isEmpty() && !gpmDependency.defaultType.isNullOrBlank()) {
                // setting default dependency
                val depType = GradleDependency.Type.values().find { it.keyword == gpmDependency.defaultType.trim() }
                    ?: throw IllegalArgumentException("Invalid default type '${gpmDependency.defaultType}'")

                add(depType)
            }

            // Still Empty?
            if (isEmpty()) {
                // adding default
                add(GradleDependency.Type.IMP)
            }
        }

        require(depTypes.isNotEmpty()) { "Dependency type can't be empty" }

        // Getting latest version

        // Adding each dependency
        for (depType in depTypes) {
            gradleManager.addDependency(
                GradleDependency(
                    depType,
                    gpmDependency.groupId,
                    gpmDependency.artifactId,
                    ""
                )
            )
        }


        RESULT_REPO_FOUND
    }

}