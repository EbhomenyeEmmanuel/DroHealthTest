package com.esq.drohealthtest.data.model

import kotlinx.coroutines.flow.Flow

/**
 * StoreScreen state added.
 */
sealed class StoreScreenUiState{
    data class Success(val data: Flow<List<StoreItem>>) :
        StoreScreenUiState()
    data class Error(val message: String) :
        StoreScreenUiState()
    object Loading : StoreScreenUiState()
    object Empty : StoreScreenUiState()
}
