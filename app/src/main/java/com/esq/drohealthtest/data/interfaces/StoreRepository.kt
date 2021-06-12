package com.esq.drohealthtest.data.interfaces

import androidx.lifecycle.LiveData
import com.esq.drohealthtest.data.model.BagItem
import com.esq.drohealthtest.data.model.StoreItem
import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    suspend fun getHomeListResultStream(): Flow<List<StoreItem>>
    fun getSearchResultStream(searchQuery: String): Flow<List<StoreItem>>
    fun getNumberOfItemsInStore(): LiveData<Int>
    fun getNumberOfItemsInBag(): LiveData<Int>
    suspend fun saveItemToBag( bagItem: BagItem )
}