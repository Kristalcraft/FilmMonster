package ru.otus.filmmonster.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmsApi {

    /*@GET ("films")
    fun getFilms(): Call<ArrayList<FilmModel>>*/

    @GET ("top")
    fun getTop250Films(@Query("type") type: String,@Query("page") page: Int): Call<Top250filmsResponse>

    @GET ("{filmID}")
    fun getFilm(@Path("filmID") filmID: Int): Call<FilmModel>
}