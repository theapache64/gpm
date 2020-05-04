package com.theapache64.gpm.di.modules

import com.squareup.moshi.Moshi
import com.theapache64.gpm.core.TransactionManager
import com.theapache64.gpm.utils.GpmConfig
import com.theapache64.gpm.core.gm.GradleManager
import dagger.Module
import dagger.Provides
import java.io.File

@Module(includes = [MoshiModule::class])
class GradleModule {

    @Provides
    fun gradleFile(): File {

        @Suppress("ConstantConditionIf")
        val filePath = if (GpmConfig.IS_DEBUG_MODE) {
            val sampleFile = File("assets/sample.build.gradle")
            val tempGradleFile = File("assets/temp.build.gradle")
            tempGradleFile.delete()
            sampleFile.copyTo(tempGradleFile)
            tempGradleFile.absolutePath
        } else {
            "app/build.gradle"
        }

        return File(filePath)
    }

    @Provides
    fun provideTransactionManager(moshi: Moshi): TransactionManager {
        return TransactionManager(moshi)
    }

    @Provides
    fun provideGradleManager(gradleFile: File, transactionManager: TransactionManager): GradleManager {
        return GradleManager(transactionManager, gradleFile)
    }
}