package com.esq.drohealthtest.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.esq.drohealthtest.data.model.BagItemDatabaseModel
import com.esq.drohealthtest.data.model.StoreItemDatabaseModel
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreItemsDao {

    @Query("SELECT * FROM stored_item WHERE id = :itemId")
    fun getStoredItem(itemId: String): Flow<StoreItemDatabaseModel>

    @Query("SELECT * FROM stored_item")
    fun getAllStoredItems(): Flow<List<StoreItemDatabaseModel>>

    @Query("SELECT COUNT(*) FROM items_in_bag")
    fun getNumberOfItemsInStore(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStoredItem(storeItemDatabaseModel: StoreItemDatabaseModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllStoredItems(storeItemDatabaseModel: List<StoreItemDatabaseModel>)

    @Query("DELETE FROM stored_item")
    suspend fun deleteAll()

}

@Dao
interface BagItemsDao {

    @Query("SELECT * FROM items_in_bag")
    fun getAllItemsInBag(): Flow<List<BagItemDatabaseModel>>

    @Query("SELECT COUNT(*) FROM items_in_bag")
    fun getNumberOfItemsInBag(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBagItem(bagItemDatabaseModel: BagItemDatabaseModel)

    @Query("DELETE FROM items_in_bag")
    suspend fun deleteAll()

}