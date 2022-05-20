package com.ahmedhamdy.myapplication2.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.ahmedhamdy.myapplication2.R
import com.ahmedhamdy.myapplication2.databinding.ActivityMainBinding
import com.ahmedhamdy.myapplication2.model.entities.Movie
import com.ahmedhamdy.myapplication2.ui.adapters.interfaces.MovieClickListener
import com.ahmedhamdy.myapplication2.ui.fragments.DetailFragment
import com.ahmedhamdy.myapplication2.utils.Constants
import com.ahmedhamdy.myapplication2.utils.Constants.MOVIEObject_EXTRA
import com.ahmedhamdy.myapplication2.utils.Constants.SHAREDPREFNAME

/*CodeReview when opening any movie other than the first one (details screen is opened by default)
   I get this crash " java.lang.IllegalStateException: Fragment already added: YouTubePlayerSupportFragmentX{365df05} "
   please check it, I was testing on pixel c table in landscape mode */

/*
* I solved the problem was that I make fragment manager add fragment layout not replace it
*
* considering the layout duplicate I replace it with the dimens file for tablet in land mode
*
* considering the package name also solved
* */
private const val TAG = "MainActivity"
class MainActivity : FragmentActivity(), MovieClickListener {

    private lateinit var binding : ActivityMainBinding
    private var mIsDualPane = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentMovieDetailsView = findViewById<View>(R.id.moviesDetailsFragmentPane)
        mIsDualPane = fragmentMovieDetailsView?.visibility == View.VISIBLE
        // check if in tablet land mode so display first movie of popular
        if(mIsDualPane){
            Constants.firstMovieToShow.observe(this, Observer {
                passDataToDetailFragment(it)
            })
        }
    }
    // to update movie details if it's in tablet mode just transfer data to movieDetail Fragment else start the activity movieDetail
    override fun transferMovie(movie: Movie) {
        if(mIsDualPane){
            passDataToDetailFragment(movie)
        }else{
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIEObject_EXTRA, movie)
            startActivity(intent)
        }
    }

    private fun passDataToDetailFragment(movie: Movie){
        val sharedPref = getSharedPreferences(SHAREDPREFNAME, FragmentActivity.MODE_PRIVATE)
        val fragmentMovieDetails = supportFragmentManager.findFragmentById(R.id.moviesDetailsFragmentPane) as DetailFragment?
        fragmentMovieDetails?.passingDataFromDetailActivity(movie, sharedPref, supportFragmentManager)
    }


}