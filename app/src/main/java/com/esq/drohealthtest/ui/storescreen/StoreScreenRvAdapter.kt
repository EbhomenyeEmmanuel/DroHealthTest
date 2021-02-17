package com.esq.drohealthtest.ui.storescreen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.esq.drohealthtest.R
import com.esq.drohealthtest.data.model.StoreItem
import com.esq.drohealthtest.databinding.ContentItemStoreScreenBinding

class StoreScreenRvAdapter(
    private val context: Context, private val click: (StoreItem) -> Unit
) :
    ListAdapter<StoreItem, StoreScreenRvAdapter.StoreScreenViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<StoreItem>() {
            override fun areItemsTheSame(oldItem: StoreItem, newItem: StoreItem): Boolean {
                return oldItem.medicineIcon == newItem.medicineIcon
            }

            override fun areContentsTheSame(oldItem: StoreItem, newItem: StoreItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class StoreScreenViewHolder(private val binding: ContentItemStoreScreenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(storeItem: StoreItem) {
            binding.storeItem = storeItem
            binding.executePendingBindings()
            itemView.setOnClickListener { click(storeItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreScreenViewHolder =
        StoreScreenViewHolder(
            DataBindingUtil.inflate<ContentItemStoreScreenBinding>(
                LayoutInflater.from(context),
                R.layout.content_item_store_screen,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: StoreScreenViewHolder, position: Int) {
        val product = getItem(position)
        product.let { holder.bind(it) }
    }

}