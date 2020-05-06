package com.theapache64.gpm.di.modules

import com.squareup.moshi.Moshi
import com.theapache64.gpm.core.TransactionManager
import dagger.Module
import dagger.Provides

@Module(includes = [MoshiModule::class])
class TransactionModule(private val isFromTest: Boolean) {
    @Provides
    fun provideTransactionManager(moshi: Moshi): TransactionManager {
        return TransactionManager(isFromTest, moshi)
    }
}