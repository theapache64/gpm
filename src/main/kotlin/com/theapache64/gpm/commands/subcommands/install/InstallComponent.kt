package com.theapache64.gpm.commands.subcommands.install

import com.squareup.moshi.Moshi
import com.theapache64.gpm.commands.Gpm
import com.theapache64.gpm.core.TransactionManager
import com.theapache64.gpm.data.remote.gpm.GpmApiInterface
import com.theapache64.gpm.data.remote.maven.MavenApiInterface
import com.theapache64.gpm.data.repos.MavenRepo
import com.theapache64.gpm.di.GpmJsonFile
import com.theapache64.gpm.di.GradleFile
import com.theapache64.gpm.di.modules.*
import dagger.Component
import java.io.File
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        NetworkModule::class,
        MoshiModule::class,
        GradleModule::class,
        CommandModule::class
    ]
)
interface InstallComponent {
    /*
     * For testing
     */
    fun getGpm(): Gpm
    fun gpmApiInterface(): GpmApiInterface
    fun mavenApiInterface(): MavenApiInterface

    @GradleFile
    fun gradleFile(): File

    @GpmJsonFile
    fun gpmJsonFile(): File

    fun mavenRepo(): MavenRepo
    fun gpmFileManager(): TransactionManager

    fun inject(install: Install)
    fun moshi(): Moshi
}