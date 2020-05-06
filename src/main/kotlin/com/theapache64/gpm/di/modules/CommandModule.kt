package com.theapache64.gpm.di.modules

import com.theapache64.gpm.commands.Gpm
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CommandModule {

    @Singleton
    @Provides
    fun provideGpm(): Gpm {
        return Gpm()
    }
}