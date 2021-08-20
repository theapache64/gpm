package com.theapache64.gpm.di.modules

import com.theapache64.gpm.core.TransactionManager
import com.theapache64.gpm.core.gm.GradleManager
import com.theapache64.gpm.di.GradleFile
import dagger.Module
import dagger.Provides
import java.io.File
import kotlin.system.exitProcess

@Module(includes = [TransactionModule::class])
class GradleModule(
    private val isFromTest: Boolean,
    private val modulePath: String?
) {

    @Provides
    @GradleFile
    fun gradleFile(): File {

        @Suppress("ConstantConditionIf")
        return if (isFromTest) {
            val modPath = if (!modulePath.isNullOrBlank()) {
                "$modulePath/"
            } else {
                ""
            }
            val tempGradleFile = File("${modPath}src/test/resources/temp.build.gradle")
            val sampleFile = File("src/test/resources/sample.build.gradle")
            tempGradleFile.delete()
            sampleFile.copyTo(tempGradleFile)
            tempGradleFile

        } else {
            if (modulePath != null) {
                val androidGradleFile = File("$modulePath/build.gradle")
                val androidKtsGradleFile = File("$modulePath/build.gradle.kts")
                when {
                    androidGradleFile.exists() -> {
                        // android project
                        androidGradleFile
                    }

                    androidKtsGradleFile.exists() -> {
                        androidKtsGradleFile
                    }

                    else -> {
                        val currentDir = File(System.getProperty("user.dir"))

                        println(
                            """
                        Couldn't find `$modulePath` inside '${currentDir.absolutePath}'.
                        Are you sure that you're executing the command from project root?
                    """.trimIndent()
                        )

                        exitProcess(0)
                    }

                }
            } else {
                findGradleFile()
            }
        }
    }

    private fun findGradleFile(): File {
        val androidGradleFile = File("app/build.gradle")
        val androidKtsGradleFile = File("app/build.gradle.kts")
        val jvmGradleFile = File("build.gradle")
        val jvmKtsGradleFile = File("build.gradle.kts")

        return when {
            androidGradleFile.exists() -> {
                // android project
                androidGradleFile
            }

            androidKtsGradleFile.exists() -> {
                androidKtsGradleFile
            }

            jvmGradleFile.exists() -> {
                jvmGradleFile
            }

            jvmKtsGradleFile.exists() -> {
                jvmKtsGradleFile
            }

            else -> {

                /**
                 * SMART END
                 */

                val currentDir = File(System.getProperty("user.dir"))

                println(
                    """
                        Invalid directory '${currentDir.absolutePath}'.
                        Are you sure that you're executing the command from project root?
                    """.trimIndent()
                )

                exitProcess(0)
            }
        }
    }


    @Provides
    fun provideGradleManager(
        @GradleFile gradleFile: File,
        transactionManager: TransactionManager
    ): GradleManager {
        return GradleManager(transactionManager, gradleFile)
    }
}