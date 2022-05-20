package com.ahmedhamdy.myapplication2.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmedhamdy.myapplication2.R
import com.ahmedhamdy.myapplication2.model.entities.Movie
import com.ahmedhamdy.myapplication2.ui.adapters.movie.MoviesAdapter
import com.ahmedhamdy.myapplication2.ui.adapters.movie.MoviesLoadStateAdapter
import com.ahmedhamdy.myapplication2.utils.ItemOffsetDecoration
import com.ahmedhamdy.myapplication2.ui.adapters.comparators.MovieComparator
import com.ahmedhamdy.myapplication2.data.viewmodels.PopularViewModel
import com.ahmedhamdy.myapplication2.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_popular.*

class PopularFragment : BaseFragment() {

    private lateinit var popularViewModel: PopularViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        popularViewModel = ViewModelProvider(this).get(PopularViewModel::class.java)
        popularViewModel.getPopularMoviesPaging()
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularProgressBar.visibility = View.GONE
        val popularMoviesAdapter =  MoviesAdapter(MovieComparator(), mContext)
        popularViewModel.getPopularPagingDataFlow()?.subscribe { moviePagingData: PagingData<Movie> ->
            popularMoviesAdapter?.submitData(
                lifecycle, moviePagingData
            )
        }.also { if(it != null) compositeDisposable.add(it) }
        popularMovieRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = popularMoviesAdapter?.withLoadStateFooter(
                MoviesLoadStateAdapter { popularMoviesAdapter.retry() }
            )
            addItemDecoration(ItemOffsetDecoration(
                requireContext(),
                R.dimen.item_offset
            ))
        }
    }
}
