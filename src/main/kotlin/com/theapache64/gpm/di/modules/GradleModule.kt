package com.theapache64.gpm.di.modules

import com.squareup.moshi.Moshi
import com.theapache64.gpm.core.TransactionManager
import com.theapache64.gpm.utils.GpmConfig
import com.theapache64.gpm.core.gm.GradleManager
import dagger.Module
import dagger.Provides
import java.io.File
import java.lang.IllegalArgumentException

@Module(includes = [TransactionModule::class])
class GradleModule(
    val isDeleteTempFile: Boolean
) {

    @Provides
    fun gradleFile(): File {

        @Suppress("ConstantConditionIf")
        return if (GpmConfig.IS_DEBUG_MODE) {

            val tempGradleFile = File("src/test/resources/temp.build.gradle")

            if (!tempGradleFile.exists() || isDeleteTempFile) {
                val sampleFile = File("src/test/resources/sample.build.gradle")
                tempGradleFile.delete()
                sampleFile.copyTo(tempGradleFile)
            }

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
    fun provideGradleManager(gradleFile: File, transactionManager: TransactionManager): GradleManager {
        return GradleManager(transactionManager, gradleFile)
    }
}