package com.ahmedhamdy.myapplication2.ui.adapters.comparators

import androidx.recyclerview.widget.DiffUtil
import com.ahmedhamdy.myapplication2.model.entities.Review

class ReviewComparator : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}