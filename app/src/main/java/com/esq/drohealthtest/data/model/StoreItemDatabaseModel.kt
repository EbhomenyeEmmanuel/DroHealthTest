package com.esq.drohealthtest.data.model

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@SuppressLint("ParcelCreator")
/**
 * Data class that captures user information for store items saved in local database
 */
@Entity(tableName = "stored_item")
data class StoreItemDatabaseModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val medicineIcon: Int,
    val mainName: String,
    val otherName: String,
    val medicineTypeName: String, val medicinePrice: Int
) :
    Parcelable