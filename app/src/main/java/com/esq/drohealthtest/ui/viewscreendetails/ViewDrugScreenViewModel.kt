package com.esq.drohealthtest.ui.viewscreendetails

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.esq.drohealthtest.data.interfaces.StoreRepository
import com.esq.drohealthtest.data.model.BagItem
import com.esq.drohealthtest.data.model.DialogMessageData
import com.esq.drohealthtest.data.model.StoreItem
import com.esq.drohealthtest.data.model.ViewDrugScreenUiState
import com.esq.drohealthtest.utils.Constants
import com.esq.drohealthtest.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _currentViewDrugScreenState =
        MutableStateFlow<ViewDrugScreenUiState>(ViewDrugScreenUiState.Empty)
    val currentViewDrugScreenState: StateFlow<ViewDrugScreenUiState> = _currentViewDrugScreenState

    val TAG = this::class.java.simpleName
    private val _numberOfItemsInBag = MediatorLiveData<Int>()
    val numberOfItemsInBag: LiveData<Int>
        get() = _numberOfItemsInBag

    var currentDrugShown: StoreItem = savedStateHandle.get<StoreItem>(Constants.VIEW_DRUG_ARGS)!!
    private var _noOfPacksChosen = MutableLiveData<Int>()
    val noOfPacksChosen: LiveData<Int>
        get() = _noOfPacksChosen

    val totalPriceForDrug: LiveData<Int>
        get() = Transformations.map(_noOfPacksChosen) {
            if (it != null) {
                //Total price for drug should change when no of packs change i.e value of LiveData changes
                currentDrugShown.medicinePrice * it
            } else {
                0
            }
        }

    private val _navigateToPopUpDialog = MutableLiveData<Event<DialogMessageData>>()

    val navigateToPopUpDialog: LiveData<Event<DialogMessageData>>
        get() = _navigateToPopUpDialog

    //Navigate to Popup Dialog when clicked
    fun onNavigateToPopUpDialog(dialogMessageData: DialogMessageData) {
        _navigateToPopUpDialog.value = Event(dialogMessageData)
    }

    private fun displayInitialData() {

        //Get value of LiveData from repo and use straightaway
        _numberOfItemsInBag.addSource(repository.getNumberOfItemsInBag()) {
            _numberOfItemsInBag.value = it
        }
        _noOfPacksChosen.postValue(Constants.INITIAL_NUMBER_OF_PACK_CHOSEN)

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

    fun onAddToBagClicked(view: View) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d(TAG, "onAddToBagClicked: Trying...")
                with(currentDrugShown) {
                    val bag = BagItem(
                        id = id,
                        drugIcon = medicineIcon,
                        drugName = mainName,
                        drugType = medicineTypeName,
                        drugPrice = medicinePrice.toString(),
                        drugQuantity = noOfPacksChosen.value!!
                    )
                    repository.saveItemToBag(
                        bag
                    )
                    _currentViewDrugScreenState.value = ViewDrugScreenUiState.Success(
                        DialogMessageData(
                            "Successful",
                            "${currentDrugShown.mainName} has been added to your bag", bag
                        )
                    )
                }
            } catch (e: Exception) {
                Log.d(TAG, "onAddToBagClicked: Error...")
                _currentViewDrugScreenState.value = ViewDrugScreenUiState.Error(
                    DialogMessageData(
                        "Error",
                        e.message.toString(),
                        null
                    )
                )
            }
        }
    }

}