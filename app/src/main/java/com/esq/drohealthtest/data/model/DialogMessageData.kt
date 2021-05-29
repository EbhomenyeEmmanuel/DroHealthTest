package com.esq.drohealthtest.data.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * Class that stores dialog data
 */

@Parcelize
@SuppressLint("ParcelCreator")
data class DialogMessageData(val messageHeader: String, val messageSubtitle: String, val bagItem: BagItem?) :
    Parcelable