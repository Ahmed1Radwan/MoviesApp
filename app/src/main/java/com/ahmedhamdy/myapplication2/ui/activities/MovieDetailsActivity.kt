package com.ahmedhamdy.myapplication2.ui.activities

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.ahmedhamdy.myapplication2.R
import com.ahmedhamdy.myapplication2.databinding.ActivityMovieDetailsBinding
import com.ahmedhamdy.myapplication2.model.entities.Movie
import com.ahmedhamdy.myapplication2.ui.fragments.DetailFragment
import com.ahmedhamdy.myapplication2.utils.Constants.MOVIEObject_EXTRA
import com.ahmedhamdy.myapplication2.utils.Constants.SHAREDPREFNAME


private const val TAG = "MovieDetailsActivity"

class MovieDetailsActivity : FragmentActivity(){

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent.getSerializableExtra(MOVIEObject_EXTRA) as Movie
        val fragmentMovieDetails = supportFragmentManager.findFragmentById(R.id.moviesDetailsFragmentPane) as DetailFragment?
        fragmentMovieDetails?.passingDataFromDetailActivity(movie, getSharedPreferences(SHAREDPREFNAME, FragmentActivity.MODE_PRIVATE),supportFragmentManager)

    }
}