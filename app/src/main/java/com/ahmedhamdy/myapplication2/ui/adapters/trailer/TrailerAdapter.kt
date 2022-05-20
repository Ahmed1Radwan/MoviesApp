package com.ahmedhamdy.myapplication2.ui.adapters.trailer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.ahmedhamdy.myapplication2.databinding.ItemTrailerBinding
import com.ahmedhamdy.myapplication2.model.entities.Trailer
import com.ahmedhamdy.myapplication2.ui.adapters.comparators.TrailerComparator
import com.ahmedhamdy.myapplication2.ui.adapters.interfaces.TrailerItemClickListener
import com.ahmedhamdy.myapplication2.ui.adapters.viewholders.TrailerViewHolder

private const val TAG = "TrailerAdapter"
class TrailerAdapter(diffCallback: TrailerComparator, onTrailerClicked: TrailerItemClickListener):PagingDataAdapter<Trailer, TrailerViewHolder>(diffCallback) {

    private val trailerClicked: TrailerItemClickListener = onTrailerClicked
    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val currentTrailer = getItem(position)
        if(currentTrailer != null){
            holder.bind(currentTrailer)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        return TrailerViewHolder(
            ItemTrailerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            trailerClicked
        )
    }
}