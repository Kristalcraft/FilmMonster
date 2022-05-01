package ru.otus.filmmonster

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
    val id: Int,
    val name: Int,
    val poster: Int,
    val description: Int,
    var like: Boolean = false,
    var comment: String = "",
    var isHighlighted: Boolean = false
    ) : Parcelable