package ru.otus.filmmonster.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmsApi {

    @GET ("top")
    suspend fun getTop250Films(@Query("type") type: String,@Query("page") page: Int): Top250filmsResponse

    @GET ("{filmID}")
    fun getFilm(@Path("filmID") filmID: Int): Call<FilmModel>
}