package com.ahmedhamdy.myapplication2.ui.adapters.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.ahmedhamdy.myapplication2.model.entities.Movie
import com.ahmedhamdy.myapplication2.ui.adapters.comparators.MovieComparator
import com.ahmedhamdy.myapplication2.databinding.ItemMovieBinding
import com.ahmedhamdy.myapplication2.ui.adapters.viewholders.MovieViewHolder


// for movies from remote database
class MoviesAdapter(diffCallback: MovieComparator, var context: Context) :
    PagingDataAdapter<Movie, MovieViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent.context
        )
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentMovie = getItem(position)
        if (currentMovie != null) {
            holder.bind(currentMovie)
        }
    }
}