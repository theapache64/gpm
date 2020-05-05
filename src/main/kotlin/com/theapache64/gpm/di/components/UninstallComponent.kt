package com.theapache64.gpm.di.components

import com.theapache64.gpm.commands.subcommands.uninstall.Uninstall
import com.theapache64.gpm.di.modules.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ViewModelModule::class]
)
interface UninstallComponent {
    fun inject(uninstall: Uninstall)
}