package com.ahmedhamdy.myapplication2

import android.app.Application
import com.ahmedhamdy.myapplication2.data.local.database.MoviesDatabase

lateinit var db: MoviesDatabase

class App: Application() {
    companion object{
        lateinit var INSTANCE: App
    }
    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
        db = MoviesDatabase.getDatabase(this)
        INSTANCE = this
    }
}