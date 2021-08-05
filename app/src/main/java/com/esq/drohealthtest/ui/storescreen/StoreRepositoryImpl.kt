package com.esq.drohealthtest.ui.storescreen

import android.util.Log
import androidx.lifecycle.LiveData
import com.esq.drohealthtest.data.database.BagItemsDao
import com.esq.drohealthtest.data.database.StoreItemsDao
import com.esq.drohealthtest.data.interfaces.StoreRepository
import com.esq.drohealthtest.data.model.BagItem
import com.esq.drohealthtest.data.model.BagItemModelMapper
import com.esq.drohealthtest.data.model.StoreItemModelMapper
import com.esq.drohealthtest.data.model.StoreItem
import com.esq.drohealthtest.utils.toFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val storeItemsDao: StoreItemsDao,
    private val bagItemsDao: BagItemsDao,
    private val mapperStoreItem: StoreItemModelMapper,
    private val bagItemModelMapper: BagItemModelMapper
) : StoreRepository {

    override suspend fun getHomeListResultStream(): Flow<List<StoreItem>> {
        return storeItemsDao.getAllStoredItems().map {
            Log.d(this::class.java.simpleName, "Results is $it")
            mapperStoreItem.mapFromEntityList(it)
        }
    }

    override fun getSearchResultStream(searchQuery: String): Flow<List<StoreItem>> {
        return storeItemsDao.findItemWithName(searchQuery)
            .map { mapperStoreItem.mapFromEntityList(it) }
    }

    override fun getNumberOfItemsInStore(): LiveData<Int> {
        return storeItemsDao.getNumberOfItemsInStore()
    }

    override fun getNumberOfItemsInBag(): LiveData<Int> {
        return bagItemsDao.getNumberOfItemsInBag()
    }

    override suspend fun saveItemToBag(bagItem: BagItem) {
        bagItemsDao.saveBagItem(bagItemModelMapper.mapToEntity(bagItem))
    }
}