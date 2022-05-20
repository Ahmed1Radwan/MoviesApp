package com.ahmedhamdy.myapplication2.ui.adapters.comparators

import androidx.recyclerview.widget.DiffUtil
import com.ahmedhamdy.myapplication2.model.entities.Trailer

class TrailerComparator : DiffUtil.ItemCallback<Trailer>() {
    override fun areItemsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
        return oldItem == newItem
    }
}