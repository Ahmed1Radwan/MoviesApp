package com.ahmedhamdy.myapplication2.ui.adapters.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmedhamdy.myapplication2.R

// for display loading state
class MoviesLoadStateAdapter(private val retryCallback: View.OnClickListener) :
    LoadStateAdapter<MoviesLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(loadStateViewHolder: LoadStateViewHolder, loadState: LoadState) {
        loadStateViewHolder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(parent, retryCallback)
    }

    class LoadStateViewHolder internal constructor(
        parent: ViewGroup,
        retryCallback: View.OnClickListener
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_networkstate, parent, false)
    ) {
        private val progressBar: ProgressBar? = parent.findViewById(R.id.progressBar)
        private val errorMsg: TextView? = parent.findViewById(R.id.errorMsg)
        private val retry: Button? = parent.findViewById(R.id.retryButton)
        fun bind(loadState: LoadState?) {

            if (progressBar != null) {
                progressBar.visibility = if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
            }
            if (retry != null) {
                retry.visibility = if (loadState is Error) View.VISIBLE else View.GONE
            }
            if (errorMsg != null) {
                errorMsg.visibility = if (loadState is Error) View.VISIBLE else View.GONE
            }
        }

        init {
            retry?.setOnClickListener(retryCallback)
        }
    }
}