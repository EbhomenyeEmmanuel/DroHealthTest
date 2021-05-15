package com.esq.drohealthtest.ui.storescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esq.drohealthtest.data.interfaces.StoreRepository
import com.esq.drohealthtest.data.model.StoreItem
import com.esq.drohealthtest.data.model.StoreScreenUiState
import com.esq.drohealthtest.utils.Event
import com.esq.drohealthtest.utils.toFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class StoreScreenViewModel @Inject constructor(
    private val repository: StoreRepository
) : ViewModel() {
    val TAG = this::class.java.simpleName

    private val _currentStoreScreenState =
        MutableStateFlow<StoreScreenUiState>(StoreScreenUiState.Empty)
    val currentStoreScreenState: StateFlow<StoreScreenUiState> = _currentStoreScreenState

    init {
        viewModelScope.launch {
            displayInitialData()
            getStoreItems()
        }
    }

    private val _numberOfItemsInStore = MutableLiveData<Int>()
    val numberOfItemsInStore: LiveData<Int>
        get() = _numberOfItemsInStore

    private val _navigateToViewDrugScreen = MutableLiveData<Event<StoreItem>>()

    val navigateToViewDrugScreen: LiveData<Event<StoreItem>>
        get() = _navigateToViewDrugScreen

    private fun displayInitialData() {
        //_numberOfItemsInStore.value = repository.getNumberOfItemsInStore().value
    }

    private suspend fun getStoreItems() {
        withContext(Dispatchers.IO){
            _currentStoreScreenState.value = StoreScreenUiState.Loading
            delay(1L)
            _currentStoreScreenState.value =
                StoreScreenUiState.Success(repository.getHomeListResultStream())
        }
    }

    //Navigate to Drug Screen when clicked
    fun onViewDrugClicked(item: StoreItem) {
        _navigateToViewDrugScreen.value = Event(item)
    }

    //Search from db
    fun searchResults(queryValue: String) {
        viewModelScope.launch {
            repository.getSearchResultStream().map {
                var searchResultList = mutableListOf<StoreItem>()
                for (item in it) {
                    if (item.mainName == queryValue || item.medicineTypeName == queryValue || item.otherName == queryValue) {
                        searchResultList.add(item)
                        _currentStoreScreenState.value =
                            StoreScreenUiState.Success(searchResultList.toFlow())
                    }
                }
            }
        }
    }

}