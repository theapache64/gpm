package com.theapache64.gpm.di.components

import com.theapache64.gpm.commands.subcommands.Install
import com.theapache64.gpm.di.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface InstallComponent {
    fun inject(install: Install)
}