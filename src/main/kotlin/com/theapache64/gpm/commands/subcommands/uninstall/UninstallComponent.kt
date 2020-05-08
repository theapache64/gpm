package com.theapache64.gpm.commands.subcommands.uninstall

import com.theapache64.gpm.core.TransactionManager
import com.theapache64.gpm.core.gm.GradleManager
import com.theapache64.gpm.di.GpmJsonFile
import com.theapache64.gpm.di.GradleFile
import com.theapache64.gpm.di.modules.GradleModule
import com.theapache64.gpm.di.modules.TransactionModule
import dagger.Component
import java.io.File
import javax.inject.Singleton

@Singleton
@Component(
    modules = [GradleModule::class, TransactionModule::class]
)
interface UninstallComponent {
    fun inject(uninstall: Uninstall)

    @GradleFile
    fun gradleFile(): File

    @GpmJsonFile
    fun gpmJsonFile(): File
    fun transactionManager(): TransactionManager
    fun gradleManager(): GradleManager

}