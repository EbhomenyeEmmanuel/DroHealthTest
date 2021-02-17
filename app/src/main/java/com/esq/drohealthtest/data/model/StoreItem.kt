package com.esq.drohealthtest.data.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@SuppressLint("ParcelCreator")
data class StoreItem(val id: Int, val medicineIcon: Int, val mainName: String, val otherName: String, val medicineTypeName: String, val medicinePrice: Int) :
    Parcelable