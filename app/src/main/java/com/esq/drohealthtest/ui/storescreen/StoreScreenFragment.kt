package com.esq.drohealthtest.ui.storescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.esq.drohealthtest.R
import com.esq.drohealthtest.data.model.StoreItem
import com.esq.drohealthtest.databinding.StoreScreenFragmentBinding
import com.esq.drohealthtest.utils.EventObserver
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class StoreScreenFragment : Fragment() {

    companion object {
        fun newInstance() = StoreScreenFragment()
    }

    private val _viewModel: StoreScreenViewModel by viewModels()
    private lateinit var adapter: StoreScreenRvAdapter

    private lateinit var bind: StoreScreenFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DataBindingUtil.inflate(inflater, R.layout.store_screen_fragment, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.viewModel = _viewModel
        bind.lifecycleOwner = this

        _viewModel.navigateToViewArticle.observe(viewLifecycleOwner, EventObserver { article ->
            //if (findNavController().currentDestination?.id == R.id.storeScreenFragment)
            //findNavController().navigate(Home.actionHomeFragmentToViewProductFragment(article))
        })

        initAdapter()
        initStoreList()
        //initBottomSheet()
    }

    private fun initBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(bind.bottomSheet).apply {
            peekHeight = 56
        }
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        bind.appCompatImageView4.visibility = View.VISIBLE
                        bind.textView5.visibility = View.VISIBLE
                        bind.appCompatImageView04.visibility = View.GONE
                        bind.textView05.visibility = View.GONE
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        bind.appCompatImageView4.visibility = View.GONE
                        bind.textView5.visibility = View.GONE
                        bind.appCompatImageView04.visibility = View.VISIBLE
                        bind.textView05.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //TODO("Not yet implemented")
            }

        })
    }

    private fun initAdapter() {
        adapter = StoreScreenRvAdapter(requireContext()) { item: StoreItem ->
            _viewModel.onViewHomeArticleClicked(item)
        }
        bind.recyclerViewStoreList.layoutManager = GridLayoutManager(activity, 2)
        bind.recyclerViewStoreList.adapter = adapter
    }

    private fun initStoreList() {
        lifecycleScope.launchWhenResumed {
            _viewModel.storeItems.collectLatest {
                adapter.submitList(it)
            }
        }
    }
}