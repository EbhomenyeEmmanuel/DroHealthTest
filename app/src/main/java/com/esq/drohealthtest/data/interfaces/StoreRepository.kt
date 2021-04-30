package com.esq.drohealthtest.data.interfaces

import androidx.lifecycle.LiveData
import com.esq.drohealthtest.data.model.StoreItem
import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    fun getHomeListResultStream(): Flow<List<StoreItem>>
    fun getSearchResultStream(): Flow<List<StoreItem>>
    fun getNumberOfItemsInStore(): LiveData<Int>
    fun getNumberOfItemsInBag(): LiveData<Int>
}