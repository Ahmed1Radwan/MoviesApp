package com.ahmedhamdy.myapplication2.ui.adapters.viewholders

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ahmedhamdy.myapplication2.databinding.ItemTrailerBinding
import com.ahmedhamdy.myapplication2.model.entities.Trailer
import com.ahmedhamdy.myapplication2.ui.adapters.interfaces.TrailerItemClickListener

class TrailerViewHolder(itemTrailerBinding: ItemTrailerBinding, val onItemClicked: TrailerItemClickListener) : RecyclerView.ViewHolder(itemTrailerBinding.root), View.OnClickListener{
    private val itemTrailerBinding: ItemTrailerBinding = itemTrailerBinding
    private var trailer: Trailer?= null
    fun bind(trailer: Trailer) {
        itemTrailerBinding.trailer = trailer
        this.trailer = trailer
    }
    init {
        itemView.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        trailer?.let { onItemClicked.onTrailerCliced(it) }
    }

}