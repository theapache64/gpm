package com.theapache64.gpm.di.components

import com.theapache64.gpm.commands.subcommands.Install
import com.theapache64.gpm.di.modules.GradleModule
import com.theapache64.gpm.di.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [GradleModule::class, NetworkModule::class])
interface InstallComponent {
    fun inject(install: Install)
}