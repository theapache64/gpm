package com.theapache64.gpm.commands.subcommands.docs

import com.theapache64.gpm.core.TransactionManager
import com.theapache64.gpm.di.modules.NetworkModule
import com.theapache64.gpm.di.modules.TransactionModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TransactionModule::class, NetworkModule::class])
interface DocsComponent {
    fun transactionManager(): TransactionManager
    fun inject(docs: Docs)

}