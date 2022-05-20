package com.ahmedhamdy.myapplication2.ui.adapters.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.ahmedhamdy.myapplication2.databinding.ItemReviewBinding
import com.ahmedhamdy.myapplication2.model.entities.Review

class ReviewViewHolder(itemReviewBinding: ItemReviewBinding) : RecyclerView.ViewHolder(itemReviewBinding.root) {
    private val itemReviewBinding: ItemReviewBinding = itemReviewBinding
    fun bind(review: Review) {
        itemReviewBinding.review = review
        itemReviewBinding.executePendingBindings()
    }
}