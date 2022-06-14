package ru.otus.filmmonster.repository

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class FilmModel (
    @SerializedName(value="id", alternate = ["kinopoiskId", "filmId"])
    @Expose
    val id: Int,
    @SerializedName("nameRu")
    @Expose
    val name: String,
    @SerializedName("posterUrl")
    @Expose
    val poster: String,
    @Expose
    var description: String = "",
    @Expose
    var like: Boolean = false,
    @Expose
    var comment: String = "",
    var isHighlighted: Boolean = false
)
