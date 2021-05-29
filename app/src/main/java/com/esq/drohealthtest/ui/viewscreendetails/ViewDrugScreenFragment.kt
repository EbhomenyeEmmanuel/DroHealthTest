package com.esq.drohealthtest.ui.viewscreendetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.esq.drohealthtest.R
import com.esq.drohealthtest.data.model.ViewDrugScreenUiState
import com.esq.drohealthtest.databinding.FragmentViewDrugScreenBinding
import com.esq.drohealthtest.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * A simple [Fragment] subclass.
 * Use the [ViewDrugScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class ViewDrugScreenFragment : Fragment() {

    private val _viewModel: ViewDrugScreenViewModel by viewModels()
    private lateinit var bind: FragmentViewDrugScreenBinding

    private val args: ViewDrugScreenFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bind =
            DataBindingUtil.inflate(inflater, R.layout.fragment_view_drug_screen, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.viewModel = _viewModel
        bind.lifecycleOwner = this

        initUiState()
        //Navigate to DrugScreen
        _viewModel.navigateToPopUpDialog.observe(viewLifecycleOwner, EventObserver { message ->
            if (findNavController().currentDestination?.id == R.id.viewDrugScreenFragment)
                findNavController().navigate(ViewDrugScreenFragmentDirections.actionViewDrugScreenFragmentToPopupInfoDialogFragment(message))
        })

        bind.imageButtonBack.setOnClickListener {
            findNavController().navigate(ViewDrugScreenFragmentDirections.actionViewDrugScreenFragmentToStoreScreenFragment())
        }
    }

    private fun initUiState() {
        lifecycleScope.launchWhenStarted {
            _viewModel.currentViewDrugScreenState.collect { state ->
                when (state) {
                    is ViewDrugScreenUiState.Success -> {
                        _viewModel.onNavigateToPopUpDialog(state.dialogMessageData)
                    }
                    is ViewDrugScreenUiState.Error -> {
                        _viewModel.onNavigateToPopUpDialog(state.dialogMessageData)
                    }
                    is ViewDrugScreenUiState.Empty -> {

                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = ViewDrugScreenFragment()
    }
}