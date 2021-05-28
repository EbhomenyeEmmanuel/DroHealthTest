package com.esq.drohealthtest.data.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class that captures user information for bag items saved in local database
 */

@Parcelize
@SuppressLint("ParcelCreator")
data class BagItem(
    val id: Int,
    val drugIcon: Int,
    val drugName: String,
    val drugType: String,
    val drugPrice: String,
    var drugQuantity: Int
) : Parcelable