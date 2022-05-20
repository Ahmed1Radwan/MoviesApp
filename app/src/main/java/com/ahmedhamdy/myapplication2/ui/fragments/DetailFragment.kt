package com.ahmedhamdy.myapplication2.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.ahmedhamdy.myapplication2.R
import com.ahmedhamdy.myapplication2.databinding.FragmentDetailBinding
import com.ahmedhamdy.myapplication2.model.entities.Movie
import com.ahmedhamdy.myapplication2.model.entities.Trailer
import com.ahmedhamdy.myapplication2.data.viewmodels.MovieDetailsViewModel
import com.ahmedhamdy.myapplication2.ui.adapters.interfaces.TrailerItemClickListener
import com.ahmedhamdy.myapplication2.ui.adapters.viewpager.MovieDetailPagerAdapter
import com.ahmedhamdy.myapplication2.ui.fragments.base.BaseFragment
import com.ahmedhamdy.myapplication2.utils.Constants.SHAREDPREFKEY
import com.ahmedhamdy.myapplication2.utils.Constants.YOUTUBE_APIKEY
import com.ahmedhamdy.myapplication2.utils.SharedPrefManager
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import kotlinx.android.synthetic.main.fragment_detail.*
import android.content.Intent
import android.net.Uri


private const val TAG = "DetailFragment"
class DetailFragment : BaseFragment(), TrailerItemClickListener, YouTubePlayer.OnInitializedListener{

    private lateinit var binding : FragmentDetailBinding
    private var favoritePressed: Boolean = false
    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefEditor: SharedPreferences.Editor

    private lateinit var youtubeFragmentManager: FragmentManager
    private val trailerPlayerViewFragment = YouTubePlayerSupportFragmentX.newInstance()
    private var youtubePlayer: YouTubePlayer? = null
    private lateinit var movieDetailViewModel: MovieDetailsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        binding = FragmentDetailBinding.bind(view)
        movieDetailViewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        return view
    }

    fun passingDataFromDetailActivity(movie: Movie, sharedPrefrence: SharedPreferences, youtube: FragmentManager){
        sharedPref = sharedPrefrence
        sharedPrefEditor = sharedPref.edit()
        Log.d(TAG, "passingDataFromDetailActivity: ${movie.id}")
        setupUi(youtube, movie)
        updateMovieDetails(movie)
    }
    private fun setupUi(youtube: FragmentManager, movie: Movie){
        favoritePressed = false
        favoriteButton.background = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_favorite_border_black_24dp) }
        if (!favoritePressed){
            val set = sharedPref.getStringSet(SHAREDPREFKEY, null)
            if(set != null && set.contains(movie.id.toString())){
                favoritePressed = true
            }

        }
        if(favoritePressed){
            favoriteButton.background = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_favorite_black_24dp) }
        }
        youtubeFragmentManager = youtube
        youtubeFragmentManager.beginTransaction().replace(R.id.youtubeView2,trailerPlayerViewFragment).commit()
        trailerPlayerViewFragment.initialize(YOUTUBE_APIKEY, this)


        val fragmentPagerAdapter = MovieDetailPagerAdapter(this, movie, this)
        movieDetailViewPager.adapter = fragmentPagerAdapter
        // setup the tabs for viewpager
        TabLayoutMediator(movieDetailTabLayout, movieDetailViewPager){
                tab,
                position -> tab.text = when (position) {
            0 -> {
                resources.getString(R.string.overViewFragment)
            }
            1 -> {
                resources.getString(R.string.ReviewsFragment)
            }
            else -> {
                resources.getString(R.string.trailersFragment)
            }
        }
        }.attach()

    }

    private fun updateMovieDetails(movie: Movie){

        movieDetailViewModel.getMovieDetails(movie.id)
        movieDetailViewModel.getMovieDetailsObservable()?.subscribe(
            {
                if(it != null) {
                    progressBar.visibility = View.GONE
                    imageMovieBackdrop.visibility = View.VISIBLE
                    movie.genres = it.genres
                    binding.movieDetails = movie
                }
            },
            {
                Toast.makeText(context, resources.getString(R.string.error_loading_movies), Toast.LENGTH_SHORT).show()
            }
        ).also { if (it != null) compositeDisposable.add(it) }

        // update favoriteMovies database when user click the favoriteButton
        favoriteButton.setOnClickListener {
            if(!favoritePressed){
                favoriteButton.background =
                    context?.let { it1 -> ContextCompat.getDrawable(it1, R.drawable.ic_favorite_black_24dp) }
                favoritePressed = true
                movie?.let { it1 -> SharedPrefManager.addToSharedPref(sharedPref, sharedPrefEditor, it1.id) }
                movieDetailViewModel.insertMovieToFavoriteDatabase(movie)
            }else{
                favoriteButton.background =
                    context?.let { it1 -> ContextCompat.getDrawable(it1, R.drawable.ic_favorite_border_black_24dp) }
                favoritePressed = false
                movie?.let { it1 -> SharedPrefManager.removeFromSharedPref(sharedPref, sharedPrefEditor, it1.id) }
                movie?.let { it1 -> movieDetailViewModel.deleteMovieFromFavoriteDatabase(it1.id) }
            }
        }
    }


    override fun onTrailerCliced(trailer: Trailer) {
        /*if (movieDetailViewModel.getNetworkState()){
            imageMovieBackdrop.visibility = View.GONE
            val cueVideo = youtubePlayer?.loadVideo(trailer.key)

        }else{
            Toast.makeText(mContext, "Please Check Your Network", Toast.LENGTH_SHORT).show()
        }*/
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=${trailer.key}")
            )
        )

    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        if(!p2){
            p1?.setFullscreen(false)
            p1?.setShowFullscreenButton(false)
            this.youtubePlayer = p1
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        Log.d(TAG, "onInitializationFailure: ")
    }

}
