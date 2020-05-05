package com.theapache64.gpm.di.components

import com.theapache64.gpm.commands.subcommands.install.Install
import com.theapache64.gpm.core.TransactionManager
import com.theapache64.gpm.data.remote.gpm.GpmApiInterface
import com.theapache64.gpm.data.remote.maven.MavenApiInterface
import com.theapache64.gpm.data.repos.MavenRepo
import com.theapache64.gpm.di.modules.GradleModule
import com.theapache64.gpm.di.modules.NetworkModule
import com.theapache64.gpm.di.modules.ViewModelModule
import dagger.Component
import java.io.File
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, NetworkModule::class, GradleModule::class])
interface InstallComponent {
    /*
     * For testing
     */
    fun gpmApiInterface(): GpmApiInterface
    fun mavenApiInterface(): MavenApiInterface
    fun gradleFile(): File
    fun mavenRepo(): MavenRepo
    fun gpmFileManager(): TransactionManager

    fun inject(install: Install)
}