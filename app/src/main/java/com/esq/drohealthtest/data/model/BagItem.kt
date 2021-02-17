package com.esq.drohealthtest.data.model

/**
 * Data class that captures user information for bag items saved in local database
 */
data class BagItem(
    val id: Int,
    val drugIcon: Int,
    val drugName: String,
    val drugType: String,
    val drugPrice: String,
    var drugQuantity: Int
)