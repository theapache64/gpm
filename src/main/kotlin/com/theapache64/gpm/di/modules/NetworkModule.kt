package com.theapache64.gpm.di.modules

import com.theapache64.gpm.core.registries.gpm.GpmApiInterface
import com.theapache64.gpm.core.registries.maven.MavenApiInterface
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideGpmApiInterface(retrofit: Retrofit): GpmApiInterface {
        return retrofit.create(GpmApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideMavenApiInterface(retrofit: Retrofit): MavenApiInterface {
        return retrofit.create(MavenApiInterface::class.java)
    }
}