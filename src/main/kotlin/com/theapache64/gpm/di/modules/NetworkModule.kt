package com.theapache64.gpm.di.modules

import com.squareup.moshi.Moshi
import com.theapache64.gpm.data.remote.gpm.GpmApiInterface
import com.theapache64.gpm.data.remote.maven.MavenApiInterface
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module(includes = [MoshiModule::class])
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
    }

    @Singleton
    @Provides
    fun provideGpmApiInterface(retrofitBuilder: Retrofit.Builder, moshi: Moshi): GpmApiInterface {
        return retrofitBuilder
            .baseUrl("https://raw.githubusercontent.com/theapache64/gpm/dev/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GpmApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideMavenApiInterface(retrofitBuilder: Retrofit.Builder): MavenApiInterface {
        return retrofitBuilder
            .baseUrl("https://mvnrepository.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(MavenApiInterface::class.java)
    }
}