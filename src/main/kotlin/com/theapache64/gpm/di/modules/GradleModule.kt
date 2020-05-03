package com.theapache64.gpm.di.modules

import com.theapache64.gpm.utils.GpmConfig
import com.theapache64.gpm.core.gm.GradleManager
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named

@Module
class GradleModule {

    @Provides
    @Named("gradle_file")
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
    fun provideGradleManager(@Named("gradle_file") gradleFile: File): GradleManager {
        return GradleManager(gradleFile)
    }
}