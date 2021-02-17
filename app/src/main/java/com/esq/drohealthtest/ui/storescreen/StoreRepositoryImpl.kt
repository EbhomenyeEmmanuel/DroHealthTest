package com.esq.drohealthtest.ui.storescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.esq.drohealthtest.data.database.BagItemsDao
import com.esq.drohealthtest.data.database.StoreItemsDao
import com.esq.drohealthtest.data.interfaces.StoreRepository
import com.esq.drohealthtest.data.model.StoreItemModelMapper
import com.esq.drohealthtest.data.model.StoreItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(private val storeItemsDao: StoreItemsDao,
                                              private val bagItemsDao: BagItemsDao,
                                              private val mapperStoreItem: StoreItemModelMapper): StoreRepository {

    override fun getHomeListResultStream(): Flow<List<StoreItem>> {
        return storeItemsDao.getAllStoredItems().map { mapperStoreItem.mapFromEntityList(it) }
    }

    override fun getNumberOfItemsInStore(): LiveData<Int> {
        return storeItemsDao.getNumberOfItemsInStore().asLiveData()
    }

    override fun getNumberOfItemsInBag(): LiveData<Int> {
        return bagItemsDao.getNumberOfItemsInBag().asLiveData()
    }
}