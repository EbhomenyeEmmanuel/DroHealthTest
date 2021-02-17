package com.esq.drohealthtest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class that captures user information for bag items saved in local database
 */
@Entity(tableName = "items_in_bag")
data class BagItemDatabaseModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val drugIcon: Int,
    val drugName: String,
    val drugType: String,
    val drugPrice: String,
    var drugQuantity: Int
)