package ru.otus.filmmonster

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getText(R.string.favorites)

        val horizontalItemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        ContextCompat.getDrawable(this, R.drawable.divider_drawable)
            ?.let { horizontalItemDecoration.setDrawable(it) }
        recyclerPrefView.addItemDecoration(horizontalItemDecoration)

        val verticalItemDecoration = DividerItemDecoration(this, RecyclerView.HORIZONTAL)
        ContextCompat.getDrawable(this, R.drawable.divider_drawable)
            ?.let { verticalItemDecoration.setDrawable(it) }
        recyclerPrefView.addItemDecoration(verticalItemDecoration)
    }

    override fun onSaveInstanceState(outState: Bundle){
        super.onSaveInstanceState(outState)
        outState.putInt("select", selected)
        outState.putInt("prevSelect", prevSelected)
        outState.putParcelableArrayList(EXTRA_FILMS, ArrayList<Parcelable>(films))
    }

    override val getFilm = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        val data = result.data
        if (result.resultCode == RESULT_OK && data != null){
            val film: Film? = data.getParcelableExtra(DetailsActivity.EXTRA_FILM)
            film?.let{ film1 ->
                val id = film1.id
                films[id] = film1
                favoriteFilms[favoriteFilms.indexOf(getFilmByID(id))] = film1
                favoriteFilms.removeIf { it.id == id && !it.like }
                initPrefRecycler(favoriteFilms)
                Log.d("_OTUS_", film1.comment)
                Log.d("_OTUS_","${film1.like}")
                Log.d("_OTUS_", "film $id")
            }
        }
    }

    override fun onResume() {
        recyclerPrefView.adapter?.notifyDataSetChanged()
        super.onResume()
    }

    override fun getFilmByID(id: Int): Film{
        return favoriteFilms.find { it.id == id }?: throw IllegalStateException("No film data provided")
    }

    override fun onLikeClick(id: Int){
        getFilmByID(id).like = !getFilmByID(id).like
        val position = favoriteFilms.indexOf(getFilmByID(id))
        favoriteFilms.remove(getFilmByID(id))
        recyclerPrefView.adapter?.notifyItemRemoved(position)
    }



    fun initPrefRecycler(films:MutableList<Film>){
        /*recyclerPrefView.adapter = FilmItemAdapter(
            films,
            { id -> onFilmDetailsClick(id)},
            { id -> onLikeClick(id)}
        )*/
    }

    override fun onBackPressed() {
        val savedFilms = Intent()
        savedFilms.putParcelableArrayListExtra(EXTRA_FILMS, ArrayList<Parcelable>(films))
        setResult(RESULT_OK, savedFilms)
        finish()
    }

    companion object {
        const val EXTRA_FILM = "film"
        const val EXTRA_FILMS = "films"
    }
}