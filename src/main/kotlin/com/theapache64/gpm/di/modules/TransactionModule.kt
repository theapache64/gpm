package com.theapache64.gpm.di.modules

import com.squareup.moshi.Moshi
import com.theapache64.gpm.core.TransactionManager
import com.theapache64.gpm.di.GpmJsonFile
import dagger.Module
import dagger.Provides
import java.io.File

@Module(includes = [MoshiModule::class])
class TransactionModule(private val isFromTest: Boolean) {

    @Provides
    @GpmJsonFile
    fun provideGpmJsonFile(): File {
        return if (isFromTest) {
            File("src/test/resources/temp.gpm.json")
        } else {
            File("gpm.json")
        }
    }

    @Provides
    fun provideTransactionManager(@GpmJsonFile gpmJsonFile: File, moshi: Moshi): TransactionManager {
        return TransactionManager(gpmJsonFile, moshi)
    }
}