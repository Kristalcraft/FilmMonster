package ru.otus.filmmonster.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmsApi {

    @GET ("films")
    fun getFilms(): Call<ArrayList<FilmModel>>

    @GET ("top")
    suspend fun getTop250Films(@Query("type") type: String,@Query("page") page: Int): Top250filmsResponse
}