package com.esq.drohealthtest.ui.viewscreendetails

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.esq.drohealthtest.data.interfaces.StoreRepository
import com.esq.drohealthtest.data.model.StoreItem
import com.esq.drohealthtest.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewDrugScreenViewModel @Inject constructor(
    private val repository: StoreRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            displayInitialData()
        }
    }
    val TAG = this::class.java.simpleName
    private val _numberOfItemsInBag = MutableLiveData<Int>()
    val numberOfItemsInBag: LiveData<Int>
        get() = _numberOfItemsInBag

    var currentDrugShown: StoreItem = savedStateHandle.get<StoreItem>(Constants.VIEW_DRUG_ARGS)!!
    private var _noOfPacksChosen = MutableLiveData<Int>()
    val noOfPacksChosen: LiveData<Int>
        get() = _noOfPacksChosen

    private val _totalPriceForDrug = MutableLiveData<Int>()
    val totalPriceForDrug: LiveData<Int> = noOfPacksChosen.map {
        currentDrugShown.medicinePrice * it
    }

    private suspend fun displayInitialData() {
        Log.d(
            TAG,
            "_numberOfItemsInBag is $_numberOfItemsInBag and bags in repository is ${repository.getNumberOfItemsInBag()} /n no of bags is ${repository.getNumberOfItemsInBag().value} "
        )
       _numberOfItemsInBag.postValue(repository.getNumberOfItemsInBag().value ?: 0)
        _noOfPacksChosen.postValue(Constants.INITIAL_NUMBER_OF_PACK_CHOSEN)
        /*
        totalPriceForDrug = noOfPacksChosen.map {
            currentDrugShown.medicinePrice * it
        }*/
    }

    fun onRemovePackClicked(view: View) {
        //Total price fo drug should change when no of packs change i.e value of LiveData - noOfPacksChosen
        Transformations.map(_noOfPacksChosen) {
            if (it == 1) {
                //return@map
            } else {
                //it -= 1
            }
        }
    }

    fun onAddPackClicked(view: View) {
        //_noOfPacksChosen =
        Transformations.map(_noOfPacksChosen) { it ->
            if (it == 1) {
                //return
            } else {
                //it -= 1
            }
        }
    }
}