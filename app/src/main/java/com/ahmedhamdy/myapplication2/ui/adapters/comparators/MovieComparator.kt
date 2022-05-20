package com.ahmedhamdy.myapplication2.ui.adapters.comparators

import androidx.recyclerview.widget.DiffUtil
import com.ahmedhamdy.myapplication2.model.entities.Movie

class MovieComparator : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}