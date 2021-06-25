package com.emrys.chipselectionrecyclerview

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emrys.chipselectionrecyclerview.databinding.ItemCategoryBinding

private val diffCallback = object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}

class CategoryAdapter(): ListAdapter<String, CategoryAdapter.ViewHolder>(diffCallback) {

    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).let { ViewHolder(it) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        tracker?.let {
            holder.bind(getItem(position), it.isSelected(position.toLong()))
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class ViewHolder(
        private val binding: ItemCategoryBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: String, isSelected: Boolean = false) {
            binding.tvName.text = data
            binding.root.isActivated = isSelected

        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
                override fun inSelectionHotspot(e: MotionEvent): Boolean { return true }
            }
    }

}