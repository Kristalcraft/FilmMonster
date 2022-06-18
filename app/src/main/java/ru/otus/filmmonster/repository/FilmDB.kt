package ru.otus.filmmonster.repository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        FilmModel::class
    ],
    version = 1
)
abstract class FilmDB: RoomDatabase() {

    abstract fun getFilmDao(): FilmDao
}