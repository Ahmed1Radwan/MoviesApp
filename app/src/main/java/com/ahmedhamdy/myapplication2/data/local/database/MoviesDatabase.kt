package com.ahmedhamdy.myapplication2.data.local.database

import android.content.Context
import androidx.room.*
import com.ahmedhamdy.myapplication2.data.local.api.Dao
import com.ahmedhamdy.myapplication2.model.convertors.TypeConvertors
import com.ahmedhamdy.myapplication2.model.entities.Movie
import com.ahmedhamdy.myapplication2.model.entities.Review
import com.ahmedhamdy.myapplication2.model.entities.Trailer

@Database(
    entities = [Movie::class, Review::class, Trailer::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConvertors::class)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun movieDao(): Dao

    companion object{

        @Volatile
        private var databaseInstance: MoviesDatabase? = null

        fun getDatabase(context: Context): MoviesDatabase {

            return databaseInstance ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDatabase::class.java,
                    "Movie_database"
                ).build()
                databaseInstance = instance
                return instance
            }

        }

    }
}