package com.ahmedhamdy.myapplication2.ui.adapters.viewpager


import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ahmedhamdy.myapplication2.model.entities.Movie
import com.ahmedhamdy.myapplication2.ui.adapters.interfaces.TrailerItemClickListener
import com.ahmedhamdy.myapplication2.ui.fragments.OverviewFragment
import com.ahmedhamdy.myapplication2.ui.fragments.ReviewFragment
import com.ahmedhamdy.myapplication2.ui.fragments.TrailerFragment




class MovieDetailPagerAdapter(fragment: Fragment, private val movie: Movie, private val listener: TrailerItemClickListener)
    : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OverviewFragment.newInstance(movie.overview, movie.releaseDate, movie.voteCount.toString(), movie.voteAverage.toString(), movie.originalLanguage)
            1 -> ReviewFragment.newInstance(movie.id)
            2 -> TrailerFragment.newInstance(movie.id, listener)
            else -> return null!!
        }
    }
    override fun getItemCount(): Int {
        return FRAGMENTS_NUM
    }

    companion object{
        private const val FRAGMENTS_NUM = 3
    }
}
