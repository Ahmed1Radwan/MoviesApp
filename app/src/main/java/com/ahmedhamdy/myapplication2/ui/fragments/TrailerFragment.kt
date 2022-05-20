package com.ahmedhamdy.myapplication2.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedhamdy.myapplication2.R
import com.ahmedhamdy.myapplication2.model.entities.Trailer
import com.ahmedhamdy.myapplication2.ui.adapters.comparators.TrailerComparator
import com.ahmedhamdy.myapplication2.data.viewmodels.TrailerViewModel
import com.ahmedhamdy.myapplication2.ui.adapters.trailer.TrailerAdapter
import com.ahmedhamdy.myapplication2.ui.adapters.interfaces.TrailerItemClickListener
import com.ahmedhamdy.myapplication2.ui.fragments.base.BaseFragment
import com.ahmedhamdy.myapplication2.utils.Constants.MOVIEID_EXTRA
import kotlinx.android.synthetic.main.fragment_trailer.*


class TrailerFragment : BaseFragment() {


    private var s: String? = null
    private var movieId: Long = -1
    private lateinit var itemListener: TrailerItemClickListener
    private lateinit var trailerViewModel: TrailerViewModel

    private fun getText(hamo: Int?): Int{
        return 0
    }

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
        trailerViewModel = ViewModelProvider(this).get(TrailerViewModel::class.java)
        trailerViewModel.getMovieTrailersPaging(movieId)
        return inflater.inflate(R.layout.fragment_trailer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val trailerAdapter = TrailerAdapter(TrailerComparator(), itemListener)
        trailerViewModel.getTrailersPagingDataFlowable()?.subscribe { trailerPagingDate: PagingData<Trailer> ->
            trailerAdapter.submitData(lifecycle, trailerPagingDate)
        }.also { if (it != null) compositeDisposable.add(it) }
        trailerRecyclerView.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = trailerAdapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(movieId: Long, listener: TrailerItemClickListener) =
            TrailerFragment().apply {
                itemListener = listener
                arguments = Bundle().apply {
                    putLong(MOVIEID_EXTRA, movieId)
                }
            }
    }

}
