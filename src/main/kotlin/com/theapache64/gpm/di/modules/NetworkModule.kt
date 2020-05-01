package com.theapache64.gpm.di.modules

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.theapache64.gpm.data.remote.GpmApiInterface
import com.theapache64.gpm.data.remote.MavenApiInterface
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideKotlinJsonAdapterFactory(): KotlinJsonAdapterFactory {
        return KotlinJsonAdapterFactory()
    }

    @Singleton
    @Provides
    fun provideMoshi(kotlinJsonAdapter: KotlinJsonAdapterFactory): Moshi {
        return Moshi.Builder()
            .add(kotlinJsonAdapter)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Singleton
    @Provides
    fun provideGpmApiInterface(retrofitBuilder: Retrofit.Builder): GpmApiInterface {
        return retrofitBuilder
            .baseUrl("https://raw.githubusercontent.com/theapache64/gpm/")
            .build()
            .create(GpmApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideMavenApiInterface(retrofit: Retrofit): MavenApiInterface {
        return retrofit.create(MavenApiInterface::class.java)
    }
}