package com.theapache64.gpm.di.modules

import com.squareup.moshi.Moshi
import com.theapache64.gpm.core.TransactionManager
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named

@Module(includes = [MoshiModule::class])
class TransactionModule(private val isFromTest: Boolean) {

    @Provides
    @Named("gpm_json_file")
    fun provideGpmJsonFile(): File {
        return if (isFromTest) {
            File("src/test/resources/temp.gpm.json")
        } else {
            File("gpm.json")
        }
    }

    @Provides
    fun provideTransactionManager(@Named("gpm_json_file") gpmJsonFile: File, moshi: Moshi): TransactionManager {
        return TransactionManager(gpmJsonFile, moshi)
    }
}