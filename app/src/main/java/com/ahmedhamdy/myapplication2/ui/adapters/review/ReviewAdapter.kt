package com.ahmedhamdy.myapplication2.ui.adapters.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.ahmedhamdy.myapplication2.databinding.ItemReviewBinding
import com.ahmedhamdy.myapplication2.model.entities.Review
import com.ahmedhamdy.myapplication2.ui.adapters.comparators.ReviewComparator
import com.ahmedhamdy.myapplication2.ui.adapters.viewholders.ReviewViewHolder


class ReviewAdapter(diffUtil: ReviewComparator): PagingDataAdapter<Review, ReviewViewHolder>(diffUtil){

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val currentReview = getItem(position)
        if(currentReview != null){
            holder.bind(currentReview)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}