package com.ahmedhamdy.myapplication2.utils

import android.content.SharedPreferences

object SharedPrefManager {

    // if movie is favorite save its id to sharedpref
    fun addToSharedPref(
        sharedPref: SharedPreferences,
        sharedPrefEditor: SharedPreferences.Editor,
        movieId: Long
    ) {
        var set = sharedPref.getStringSet(Constants.SHAREDPREFKEY, null)
        if(set == null)
            set = mutableSetOf<String>()
        else
            sharedPrefEditor.clear()
        set.add(movieId.toString())
        sharedPrefEditor.putStringSet(Constants.SHAREDPREFKEY, set)
        sharedPrefEditor.apply()
    }
    // if movie removed from favorite remove it from sharedpref
    fun removeFromSharedPref(
        sharedPref: SharedPreferences,
        sharedPrefEditor: SharedPreferences.Editor,
        movieId: Long
    ) {
        val set = sharedPref.getStringSet(Constants.SHAREDPREFKEY, null)
        if (set != null && set.contains(movieId.toString())) {
            set.remove(movieId.toString())
            sharedPrefEditor.clear()
            sharedPrefEditor.putStringSet(Constants.SHAREDPREFKEY,set)
            sharedPrefEditor.apply()
        }

    }

}