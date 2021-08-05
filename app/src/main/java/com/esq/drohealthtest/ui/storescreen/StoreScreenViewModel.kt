package com.esq.drohealthtest.ui.storescreen

import android.util.Log
import androidx.lifecycle.*
import com.esq.drohealthtest.data.interfaces.StoreRepository
import com.esq.drohealthtest.data.model.StoreItem
import com.esq.drohealthtest.data.model.StoreScreenUiState
import com.esq.drohealthtest.utils.Event
import com.esq.drohealthtest.utils.toFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@FlowPreview
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
            _currentStoreScreenState.value =
                StoreScreenUiState.Success(repository.getHomeListResultStream())
        }
    }

    //Navigate to Drug Screen when clicked
    fun onViewDrugClicked(item: StoreItem) {
        _navigateToViewDrugScreen.value = Event(item)
    }

    //Search from db
    fun searchResults(stateFlow: StateFlow<String>) {
        //TODO("Fix empty query from db when results has been search ")
        viewModelScope.launch {
           stateFlow.debounce(300)
                .filter { query ->
                    return@filter !query.isEmpty()
                }.distinctUntilChanged()
                .flatMapLatest { query ->
                    repository.getSearchResultStream(query).map {
                        //searchDatabaseForQuery(it, query)
                        Log.d(TAG, "List from db is $it")
                        it
                    }.catch {
                        _currentStoreScreenState.value =
                            StoreScreenUiState.Error("")
                    }
                }.flowOn(Dispatchers.Default)
                .collect {
                    Log.d(TAG, "List collected is $it")
                    _currentStoreScreenState.value =
                        StoreScreenUiState.Success(it.toFlow())
                }
        }
    }

    private fun searchDatabaseForQuery(
        list: List<StoreItem>,
        query: String
    ): List<StoreItem> {
        val searchResultList = mutableListOf<StoreItem>()
        list.forEach { item ->
            if (item.mainName == query || item.medicineTypeName == query || item.otherName == query) {
                Log.d(TAG, "$item found")
                searchResultList.add(item)
            }else{
                Log.d(TAG, "$item not found")
            }
        }
        return searchResultList.toList()
    }

}