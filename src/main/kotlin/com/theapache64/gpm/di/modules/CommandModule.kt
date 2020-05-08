package com.theapache64.gpm.di.modules

import com.theapache64.gpm.commands.gpm.Gpm
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CommandModule(private val isFromTest: Boolean) {

    @Singleton
    @Provides
    fun provideGpm(): Gpm {
        return Gpm(isFromTest)
    }
}