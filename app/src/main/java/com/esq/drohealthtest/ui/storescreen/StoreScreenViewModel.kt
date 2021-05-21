package com.esq.drohealthtest.ui.storescreen

import androidx.lifecycle.*
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

@InternalCoroutinesApi
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
            delay(100)
            withContext(Dispatchers.Main){
                displayInitialData()
            }
            getStoreItems()
        }
    }

    private val _numberOfItemsInStore = MediatorLiveData<Int>()
    val numberOfItemsInStore: LiveData<Int>
        get() = _numberOfItemsInStore

    private val _navigateToViewDrugScreen = MutableLiveData<Event<StoreItem>>()

    val navigateToViewDrugScreen: LiveData<Event<StoreItem>>
        get() = _navigateToViewDrugScreen

    private fun displayInitialData() {
        _numberOfItemsInStore.addSource(repository.getNumberOfItemsInStore()) {
            _numberOfItemsInStore.value = it
        }
    }

    private suspend fun getStoreItems() {
        withContext(Dispatchers.IO){
            _currentStoreScreenState.value = StoreScreenUiState.Loading
            delay(50)
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