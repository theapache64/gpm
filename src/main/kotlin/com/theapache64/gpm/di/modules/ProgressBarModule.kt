package com.theapache64.gpm.di.modules

import com.theapache64.gpm.di.InstallProgress
import dagger.Module
import dagger.Provides
import me.tongfei.progressbar.ProgressBar

@Module
class ProgressBarModule {

    @InstallProgress
    @Provides
    fun provideInstallProgressBar(): ProgressBar {
        return ProgressBar("Install", 100)
    }
}