package com.ahmedhamdy.myapplication2.ui.adapters.viewholders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.ahmedhamdy.myapplication2.databinding.ItemMovieBinding
import com.ahmedhamdy.myapplication2.model.entities.Movie
import com.ahmedhamdy.myapplication2.ui.adapters.interfaces.MovieClickListener

class MovieViewHolder(movieItemBinding: ItemMovieBinding, private val context: Context) :
    RecyclerView.ViewHolder(movieItemBinding.root) {

    private val movieItemBinding: ItemMovieBinding = movieItemBinding
    private var movie: Movie? = null
    fun bind(movie: Movie) {
        this.movie = movie
        movieItemBinding.movie = movie
    }
    init {
        itemView.setOnClickListener {
            val communicator = context as MovieClickListener
            movie?.let { it1 -> communicator.transferMovie(it1) }
        }

    }
}