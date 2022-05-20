package com.ahmedhamdy.myapplication2.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmedhamdy.myapplication2.R
import com.ahmedhamdy.myapplication2.utils.Constants.MOVIE_LANGUAGE
import com.ahmedhamdy.myapplication2.utils.Constants.MOVIE_OVERVIEW
import com.ahmedhamdy.myapplication2.utils.Constants.MOVIE_RELEASE
import com.ahmedhamdy.myapplication2.utils.Constants.MOVIE_VOTEAVERG
import com.ahmedhamdy.myapplication2.utils.Constants.MOVIE_VOTECOUNT
import kotlinx.android.synthetic.main.fragment_overview.*

private const val TAG = "OverviewFragment"
class OverviewFragment : Fragment() {
    private var overview: String = ""
    private var releaseDate: String = ""
    private var voteCount: String = ""
    private var voteAverage: String = ""
    private var originalLanguage: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            overview = it.getString(MOVIE_OVERVIEW).toString()
            releaseDate = it.getString(MOVIE_RELEASE).toString()
            voteCount = it.getString(MOVIE_VOTECOUNT).toString()
            voteAverage = it.getString(MOVIE_VOTEAVERG).toString()
            originalLanguage = it.getString(MOVIE_LANGUAGE).toString()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        overViewTextView.text = overview
        textReleaseDate.text = releaseDate
        labelVote.text = voteCount
        textVote.text = voteAverage
        textLanguage.text = originalLanguage
    }

    companion object {
        @JvmStatic
        fun newInstance(overView: String?, releaseDate: String?, vCount: String?, vAverage: String?, language: String?) =
            OverviewFragment().apply {
                arguments = Bundle().apply {
                    putString(MOVIE_OVERVIEW, overView)
                    putString(MOVIE_RELEASE, releaseDate)
                    putString(MOVIE_VOTECOUNT, vCount)
                    putString(MOVIE_VOTEAVERG, vAverage)
                    putString(MOVIE_LANGUAGE, language)
                }
            }
    }

}