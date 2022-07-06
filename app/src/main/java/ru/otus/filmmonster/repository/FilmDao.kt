package ru.otus.filmmonster.repository

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FilmDao {

    @Query("SELECT * FROM FilmModel ORDER by positionID LIMIT :limit OFFSET :offset")
    fun getFilms(limit: Int, offset: Int): List<FilmModel>?

    @Query("SELECT * FROM FilmModel ORDER by positionID  LIMIT :limit OFFSET :offset")
    fun getFilmsLiveData(limit: Int, offset: Int): LiveData<List<FilmModel>?>

    @Insert
    fun insert(entity: FilmModel?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entities: ArrayList<FilmModel>?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: FilmModel?)

    @Query("SELECT * FROM FilmModel WHERE id = :filmID")
    fun getFilm(filmID: String): FilmModel?

    @Query("UPDATE FilmModel SET description = :description WHERE id = :filmID")
    fun updateFilmDescription(filmID: String, description: String)
}