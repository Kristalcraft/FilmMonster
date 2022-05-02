package ru.otus.filmmonster

import android.os.Bundle
import android.os.Parcelable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PreferencesActivity : MainActivity() {

    val recyclerPrefView by lazy{findViewById<RecyclerView>(R.id.recycler_preferences)}
    var favoriteFilms: MutableList<Film> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        val filmsExtra = intent?.getParcelableArrayListExtra<Film>(EXTRA_FILMS) ?: throw IllegalStateException("No film data provided")
        films = filmsExtra
        for (film in films){
            if (film.like){
                favoriteFilms.add(film)
            }
        }

        initPrefRecycler(favoriteFilms)
    }

    fun initPrefRecycler(films:MutableList<Film>){
        val layoutManager = LinearLayoutManager(this)
        recyclerPrefView.layoutManager = layoutManager
        recyclerPrefView.adapter = FilmItemAdapter(films) { position -> onFilmDetailsClick(position)}
    }

    override fun onBackPressed() {
        finish()
    }
}