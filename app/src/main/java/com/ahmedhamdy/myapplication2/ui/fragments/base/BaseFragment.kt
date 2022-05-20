package com.ahmedhamdy.myapplication2.ui.fragments.base

import android.content.Context
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment: Fragment() {
    protected var compositeDisposable = CompositeDisposable()
    protected lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}