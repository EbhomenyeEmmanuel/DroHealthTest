package com.esq.drohealthtest.ui.viewscreendetails

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.esq.drohealthtest.data.interfaces.StoreRepository
import com.esq.drohealthtest.data.model.StoreItem
import com.esq.drohealthtest.utils.Constants
import com.esq.drohealthtest.utils.switchMapThenComputeIntValueType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewDrugScreenViewModel @Inject constructor(
    private val repository: StoreRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    init {
        viewModelScope.launch {
            delay(50)
            withContext(Dispatchers.Main) {
                displayInitialData()
            }
        }
    }

    val TAG = this::class.java.simpleName
    private val _numberOfItemsInBag = MediatorLiveData<Int>()
    val numberOfItemsInBag: LiveData<Int>
        get() = _numberOfItemsInBag

    var currentDrugShown: StoreItem = savedStateHandle.get<StoreItem>(Constants.VIEW_DRUG_ARGS)!!
    private var _noOfPacksChosen = MutableLiveData<Int>()
    val noOfPacksChosen: LiveData<Int>
        get() = _noOfPacksChosen

    private val _totalPriceForDrug = MediatorLiveData<Int>()
    val totalPriceForDrug: LiveData<Int> get() = _totalPriceForDrug

    private fun displayInitialData() {

        //Get value of LiveData from repo and use straightaway
        _numberOfItemsInBag.addSource(repository.getNumberOfItemsInBag()) {
            _numberOfItemsInBag.value = it
        }
        _noOfPacksChosen.postValue(Constants.INITIAL_NUMBER_OF_PACK_CHOSEN)

        //Total price for drug should change when no of packs change i.e value of LiveData changes
        _totalPriceForDrug.switchMapThenComputeIntValueType(_noOfPacksChosen) {
            if (it != null) {
                currentDrugShown.medicinePrice * it
            } else {
                0
            }
        }
    }

    /**
     * Decrements value of packs chosen
     */
    fun onRemovePackClicked(view: View) {
        Log.d(TAG, "onRemovePackClicked: ")
        if (_noOfPacksChosen.value == Constants.INITIAL_NUMBER_OF_PACK_CHOSEN) {
            return
        } else {
            _noOfPacksChosen.value = _noOfPacksChosen.value?.dec()
        }
    }

    /**
     * Increments value of packs chosen
     */
    fun onAddPackClicked(view: View) {
        Log.d(TAG, "onAddPackClicked: ")
        //TODO("Set maximum amount of pack")
        _noOfPacksChosen.value = _noOfPacksChosen.value?.inc()
    }
}