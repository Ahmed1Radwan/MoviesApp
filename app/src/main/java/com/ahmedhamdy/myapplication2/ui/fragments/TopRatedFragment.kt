package com.ahmedhamdy.myapplication2.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmedhamdy.myapplication2.R
import com.ahmedhamdy.myapplication2.model.entities.Movie
import com.ahmedhamdy.myapplication2.ui.adapters.movie.MoviesAdapter
import com.ahmedhamdy.myapplication2.ui.adapters.movie.MoviesLoadStateAdapter
import com.ahmedhamdy.myapplication2.utils.ItemOffsetDecoration
import com.ahmedhamdy.myapplication2.ui.adapters.comparators.MovieComparator
import com.ahmedhamdy.myapplication2.data.viewmodels.TopRatedViewModel
import com.ahmedhamdy.myapplication2.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_top_rated.*


class TopRatedFragment : BaseFragment() {

    private lateinit var topRatedViewModel: TopRatedViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        topRatedViewModel = ViewModelProvider(this).get(TopRatedViewModel::class.java)
        topRatedViewModel.getTopRatedMoviesPaging()
        return inflater.inflate(R.layout.fragment_top_rated, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topRatedProgressBar.visibility = View.GONE
        val topRatedMoviesAdapter = MoviesAdapter(MovieComparator(), mContext)
        topRatedViewModel.getTopRatedPagingDaFlow()?.subscribe { moviePagingData: PagingData<Movie> ->
            topRatedMoviesAdapter?.submitData(
                lifecycle, moviePagingData
            )
        }.also { if (it != null) compositeDisposable.add(it) }
        topRatedMovieRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = topRatedMoviesAdapter?.withLoadStateFooter(
                MoviesLoadStateAdapter { topRatedMoviesAdapter.retry() }
            )
            addItemDecoration(ItemOffsetDecoration(
                requireContext(), R.dimen.item_offset
            ))
        }
    }

}
