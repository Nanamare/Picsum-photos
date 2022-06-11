package com.nanamare.starter.util

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class PagingListAdapter<T : Any, V : RecyclerView.ViewHolder>(
    itemDiffCallback: ItemDiffCallback<T>
) : PagingDataAdapter<T, V>(itemDiffCallback)

class ItemDiffCallback<T : Any>(
    val onItemsTheSame: (T, T) -> Boolean,
    val onContentsTheSame: (T, T) -> Boolean
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = onItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T) = onContentsTheSame(oldItem, newItem)
}