package com.ahmedhamdy.myapplication2.ui.adapters.interfaces

import com.ahmedhamdy.myapplication2.model.entities.Movie

// for communication between mainActivity and movies when clicked to show Detail activity or if in tablet just pass data to Detail Fragment
interface MovieClickListener {
    fun transferMovie(movie: Movie)
}