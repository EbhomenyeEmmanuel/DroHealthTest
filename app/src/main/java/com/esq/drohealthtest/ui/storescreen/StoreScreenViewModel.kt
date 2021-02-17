package com.esq.drohealthtest.ui.storescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esq.drohealthtest.data.interfaces.StoreRepository
import com.esq.drohealthtest.data.model.StoreItem
import com.esq.drohealthtest.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class StoreScreenViewModel @Inject constructor(private val repository: StoreRepository) : ViewModel() {

    val storeItems: Flow<List<StoreItem>> = repository.getHomeListResultStream()

    val numberOfItemsInStore: LiveData<Int>
        get() = repository.getNumberOfItemsInStore()

    val numberOfItemsInBag: LiveData<Int>
        get() = repository.getNumberOfItemsInBag()

    private val _navigateToViewArticle = MutableLiveData<Event<StoreItem>>()

    val navigateToViewArticle: LiveData<Event<StoreItem>>
        get() = _navigateToViewArticle

    fun onViewHomeArticleClicked(item: StoreItem) {
        _navigateToViewArticle.value = Event(item)
    }

}