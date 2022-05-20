package com.ahmedhamdy.myapplication2.ui.fragments
//CodeReview package name is wrong

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmedhamdy.myapplication2.R
import com.ahmedhamdy.myapplication2.ui.adapters.viewpager.MoviesPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*

class MoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentPagerAdapter = MoviesPagerAdapter(this)
        viewPager.adapter = fragmentPagerAdapter
        TabLayoutMediator(tabLayout, viewPager){
                tab,
                position -> tab.text = when (position) {
            0 -> {
                resources.getString(R.string.popularFragment)
            }
            1 -> {
                resources.getString(R.string.topRatedFragment)
            }
            else -> {
                resources.getString(R.string.favoriteFragment)
            }
        }
        }.attach()
    }

}