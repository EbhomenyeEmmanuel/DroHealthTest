package com.esq.drohealthtest.data.interfaces

import androidx.lifecycle.LiveData
import com.esq.drohealthtest.data.model.StoreItem
import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    suspend fun getHomeListResultStream(): Flow<List<StoreItem>>
    suspend fun getSearchResultStream(): Flow<List<StoreItem>>
    suspend fun getNumberOfItemsInStore(): LiveData<Int>
    suspend fun getNumberOfItemsInBag(): LiveData<Int>
}