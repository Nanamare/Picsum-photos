package com.nanamare.starter.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nanamare.domain.photo.model.Photo
import com.nanamare.starter.R
import com.nanamare.starter.databinding.ItemPhotoBinding
import com.nanamare.starter.util.ItemDiffCallback
import com.nanamare.starter.util.PagingListAdapter

class PhotoAdapter(private val itemWidth: Int, private val block: (photo: Photo) -> Unit) :
    PagingListAdapter<Photo, ItemPhotoViewHolder>(ItemDiffCallback({ old, new -> old.id == new.id }) { old, new -> old == new }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        with(binding.iv.layoutParams) {
            width = itemWidth
            height = itemWidth
        }
        return ItemPhotoViewHolder(binding).apply {
            itemView.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    getItem(bindingAdapterPosition)?.let(block)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ItemPhotoViewHolder, position: Int) {
        holder.binding.photo = getItem(position)
    }

    override fun getItemViewType(position: Int): Int = if (position == itemCount) {
        VIEW_TYPE_IMAGE
    } else {
        VIEW_TYPE_UNKNOWN
    }

    companion object {
        const val VIEW_TYPE_IMAGE = R.layout.item_photo
        const val VIEW_TYPE_UNKNOWN = -1
        private const val SPAN_COUNT = 3
    }

}

class ItemPhotoViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)