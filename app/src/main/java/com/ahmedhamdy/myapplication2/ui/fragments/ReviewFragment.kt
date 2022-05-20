package com.ahmedhamdy.myapplication2.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedhamdy.myapplication2.R
import com.ahmedhamdy.myapplication2.model.entities.Review
import com.ahmedhamdy.myapplication2.ui.adapters.comparators.ReviewComparator
import com.ahmedhamdy.myapplication2.data.viewmodels.ReviewViewModel
import com.ahmedhamdy.myapplication2.ui.adapters.review.ReviewAdapter
import com.ahmedhamdy.myapplication2.ui.fragments.base.BaseFragment
import com.ahmedhamdy.myapplication2.utils.Constants.MOVIEID_EXTRA
import kotlinx.android.synthetic.main.fragment_review.*

class ReviewFragment : BaseFragment() {
    private  var movieId: Long = -1
    private lateinit var reviewViewModel: ReviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getLong(MOVIEID_EXTRA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reviewViewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)
        reviewViewModel.getMovieReviewsPaging(movieId)
        return inflater.inflate(R.layout.fragment_review, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val reviewAdapter = ReviewAdapter(ReviewComparator())
       reviewViewModel.getReviewPagingDataFlow()?.subscribe { reviewPagingData: PagingData<Review> ->
            reviewAdapter.submitData(lifecycle, reviewPagingData)
        }.also { if (it != null) compositeDisposable.add(it) }
        reviewRecyclerView.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = reviewAdapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(movieId: Long) =
            ReviewFragment().apply {
                arguments = Bundle().apply {
                    putLong(MOVIEID_EXTRA, movieId)
                }
            }
    }
}
