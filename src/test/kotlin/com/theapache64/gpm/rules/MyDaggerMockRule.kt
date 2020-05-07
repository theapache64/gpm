package com.theapache64.gpm.rules

import com.theapache64.gpm.commands.subcommands.install.DaggerInstallComponent
import com.theapache64.gpm.commands.subcommands.install.InstallComponent

import com.theapache64.gpm.di.modules.GradleModule
import com.theapache64.gpm.di.modules.NetworkModule
import com.theapache64.gpm.di.modules.TransactionModule
import it.cosenonjaviste.daggermock.DaggerMockRule

class MyDaggerMockRule : DaggerMockRule<InstallComponent>(
    InstallComponent::class.java,
    NetworkModule(),
    GradleModule(isFromTest = true)
) {
    init {
        customizeBuilder<DaggerInstallComponent.Builder> {
            it.transactionModule(TransactionModule(true))
        }
    }
}