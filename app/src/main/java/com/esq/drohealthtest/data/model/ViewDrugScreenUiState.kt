package com.esq.drohealthtest.data.model


/**
 * ViewDrugScreen state added.
 */
sealed class ViewDrugScreenUiState{
    data class Success(val successMessageHeader: String, val successMessageSubtitle: String) :
        ViewDrugScreenUiState()
    data class Error(val errorMessageHeader: String, val errorMessageSubtitle: String) :
        ViewDrugScreenUiState()
    object Empty : ViewDrugScreenUiState()
}
