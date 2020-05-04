package com.theapache64.gpm.di.modules

import com.theapache64.gpm.commands.subcommands.InstallViewModel
import com.theapache64.gpm.core.gm.GradleManager
import com.theapache64.gpm.data.repos.GpmRepo
import com.theapache64.gpm.data.repos.MavenRepo
import dagger.Module
import dagger.Provides

@Module(includes = [GradleModule::class])
class ViewModelModule {

    @Provides
    fun provideInstallViewModel(
        gpmRepo: GpmRepo,
        mavenRepo: MavenRepo,
        gradleManager: GradleManager
    ): InstallViewModel {
        return InstallViewModel(gpmRepo, mavenRepo, gradleManager)
    }
}