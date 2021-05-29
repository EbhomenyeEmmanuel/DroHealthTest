package com.esq.drohealthtest.data.model


/**
 * ViewDrugScreen UI state.
 */
sealed class ViewDrugScreenUiState{
    data class Success(val dialogMessageData: DialogMessageData) :
        ViewDrugScreenUiState()
    data class Error(val dialogMessageData: DialogMessageData) :
        ViewDrugScreenUiState()
    object Empty : ViewDrugScreenUiState()
}
