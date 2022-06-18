package ru.otus.filmmonster.repository

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface FilmDao {

    @Insert
    fun insert(entity: FilmModel?)

    @Insert
    fun insert(entities: ArrayList<FilmModel>?)
}