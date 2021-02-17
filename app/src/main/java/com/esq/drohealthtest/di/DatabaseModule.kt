package com.esq.drohealthtest.di

import android.content.Context
import com.esq.drohealthtest.data.database.BagItemsDao
import com.esq.drohealthtest.data.database.DroHealthStoreDatabase
import com.esq.drohealthtest.data.database.StoreItemsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): DroHealthStoreDatabase {
        return DroHealthStoreDatabase.getDatabase(appContext, CoroutineScope(SupervisorJob() + Dispatchers.IO))
    }

    @Provides
    @Singleton
    fun provideStoreItemsDao(database: DroHealthStoreDatabase): StoreItemsDao {
        return database.storeDao()
    }

    @Provides
    @Singleton
    fun provideBagItemsDao(database: DroHealthStoreDatabase): BagItemsDao {
        return database.bagItemsDao()
    }

}


