package ru.otus.filmmonster.repository

import android.content.Context
import androidx.room.Room

private const val DATABASE_NAME = "FILMS_DB"
object DBinstance {
    private var instance: FilmDB? = null

    fun getDBinstance(context: Context): FilmDB? {
        if (instance == null) {
                instance = Room.databaseBuilder(context, FilmDB::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build()


        }
        return instance

    }

    fun destroyDBinstance(){
        instance?.close()
        instance = null
    }
}