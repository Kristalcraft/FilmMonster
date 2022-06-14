package ru.otus.filmmonster.repository

import com.google.gson.annotations.Expose

data class Top250filmsResponse(
    @Expose
    var pagesCount: Int,
    @Expose
    var films: ArrayList<FilmModel>
)

