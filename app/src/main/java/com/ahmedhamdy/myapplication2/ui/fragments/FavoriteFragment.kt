package com.ahmedhamdy.myapplication2.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmedhamdy.myapplication2.R
import com.ahmedhamdy.myapplication2.data.viewmodels.FavoriteViewModel
import com.ahmedhamdy.myapplication2.ui.adapters.comparators.MovieComparator
import com.ahmedhamdy.myapplication2.ui.adapters.movie.MoviesAdapter
import com.ahmedhamdy.myapplication2.ui.fragments.base.BaseFragment
import com.ahmedhamdy.myapplication2.utils.ItemOffsetDecoration
import kotlinx.android.synthetic.main.fragment_favorite.*


private const val TAG = "FavoriteFragment"
class FavoriteFragment : BaseFragment(){
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favoriteViewModel.getFavoriteMovies()
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favoriteMoviesAdapter = MoviesAdapter(MovieComparator(), mContext)
       favoriteViewModel.getFavoriteFlowable()?.subscribe{
            if(it != null){
                favoriteMoviesAdapter?.submitData(lifecycle, PagingData.from(it))
            }
        }.also { if (it != null) compositeDisposable.add(it) }
        favoriteMovieRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = favoriteMoviesAdapter
            addItemDecoration(ItemOffsetDecoration(
                requireContext(),
                R.dimen.item_offset
            ))
        }
    }
}