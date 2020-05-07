package com.theapache64.gpm.di.modules

import com.theapache64.gpm.core.TransactionManager
import com.theapache64.gpm.core.gm.GradleManager
import dagger.Module
import dagger.Provides
import java.io.File
import com.theapache64.gpm.di.GpmJsonFile
import com.theapache64.gpm.di.GradleFile

@Module(includes = [TransactionModule::class])
class GradleModule(
    private val isFromTest: Boolean
) {

    @Provides
    @GradleFile
    fun gradleFile(): File {

        @Suppress("ConstantConditionIf")
        return if (isFromTest) {
            val tempGradleFile = File("src/test/resources/temp.build.gradle")
            val sampleFile = File("src/test/resources/sample.build.gradle")
            tempGradleFile.delete()
            sampleFile.copyTo(tempGradleFile)
            tempGradleFile

        } else {

            val androidGradleFile = File("app/build.gradle")
            val jvmGradleFile = File("build.gradle")

            when {
                androidGradleFile.exists() -> {
                    // android project
                    androidGradleFile
                }

                jvmGradleFile.exists() -> {
                    jvmGradleFile
                }

                else -> {
                    throw IllegalArgumentException("Invalid directory. Are you sure that you're executing the command from project root?")
                }
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