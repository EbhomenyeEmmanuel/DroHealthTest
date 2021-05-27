package com.esq.drohealthtest.di

import com.esq.drohealthtest.data.database.BagItemsDao
import com.esq.drohealthtest.data.database.StoreItemsDao
import com.esq.drohealthtest.data.interfaces.StoreRepository
import com.esq.drohealthtest.data.model.BagItemModelMapper
import com.esq.drohealthtest.data.model.StoreItemModelMapper
import com.esq.drohealthtest.ui.storescreen.StoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideHomeRepository(storeItemsDao: StoreItemsDao, bagItemsDao: BagItemsDao, storeItemModelMapper: StoreItemModelMapper, bagItemModelMapper: BagItemModelMapper): StoreRepository =
        StoreRepositoryImpl(storeItemsDao, bagItemsDao, storeItemModelMapper, bagItemModelMapper)
}

