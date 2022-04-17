package ru.otus.filmmonster

data class Film(
    val id: Int,
    val name: Int,
    val poster: Int,
    val description: Int,
    var like: Boolean = false,
    var comment: String = ""
    )
