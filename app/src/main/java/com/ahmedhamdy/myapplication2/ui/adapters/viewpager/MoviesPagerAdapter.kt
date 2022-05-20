package com.ahmedhamdy.myapplication2.ui.adapters.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ahmedhamdy.myapplication2.ui.fragments.FavoriteFragment
import com.ahmedhamdy.myapplication2.ui.fragments.PopularFragment
import com.ahmedhamdy.myapplication2.ui.fragments.TopRatedFragment


class MoviesPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PopularFragment()
            1 -> TopRatedFragment()
            2 -> FavoriteFragment()
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