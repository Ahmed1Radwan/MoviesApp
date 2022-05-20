package com.ahmedhamdy.myapplication2.ui.adapters.interfaces

import com.ahmedhamdy.myapplication2.model.entities.Trailer

// when trailer clicked show its youtube video
interface TrailerItemClickListener {
    fun onTrailerCliced(trailer: Trailer)
}