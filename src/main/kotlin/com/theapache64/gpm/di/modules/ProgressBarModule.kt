package com.theapache64.gpm.di.modules

import com.theapache64.gpm.di.InstallProgress
import dagger.Module
import dagger.Provides
import me.tongfei.progressbar.ProgressBar
import me.tongfei.progressbar.ProgressBarBuilder

@Module
class ProgressBarModule {

    @InstallProgress
    @Provides
    fun provideInstallProgressBar(): ProgressBarBuilder {
        return ProgressBarBuilder()
            .setTaskName("🚛  Install")
            .setInitialMax(100)
            .setUpdateIntervalMillis(200)
    }
}