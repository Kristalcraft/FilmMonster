package ru.otus.filmmonster.repository

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.Executors

private const val DATABASE_NAME = "FILMS_DB"
object DBinstance {
    private var instance: FilmDB? = null

    fun getDBinstance(context: Context): FilmDB? {
        if (instance == null) {
                instance = Room.databaseBuilder(context, FilmDB::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .setQueryCallback(RoomDatabase.QueryCallback { sqlQuery, bindArgs ->
                println("SQL Query: $sqlQuery SQL Args: $bindArgs")
            }, Executors.newSingleThreadExecutor())
                    .build()


        }
        return instance

    }

    fun destroyDBinstance(){
        instance?.close()
        instance = null
    }
}