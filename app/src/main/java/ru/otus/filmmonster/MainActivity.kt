package ru.otus.filmmonster

import android.content.Intent
import android.graphics.Color
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle as AndroidOsBundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

open class MainActivity : AppCompatActivity() {

    open val recyclerView by lazy{findViewById<RecyclerView>(R.id.recycler)}

    override fun onCreate(savedInstanceState: AndroidOsBundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<FloatingActionButton>(R.id.preferencesButton).setOnClickListener { view ->
            openPreferences()
        }

        checkSavedState(savedInstanceState)
        initRecycler(films)
    }

    fun checkSavedState(savedInstanceState: android.os.Bundle?){
        if (savedInstanceState == null) {
            films = createFilms()
            selected = -1
            prevSelected = -1
        } else {
            selected = savedInstanceState.getInt("select")
            prevSelected = savedInstanceState.getInt("prevSelect")
            films = savedInstanceState.getParcelableArrayList(EXTRA_FILMS)!!
        }
    }

    private fun openPreferences() {
        val intentPreferences = Intent(this, PreferencesActivity::class.java)
        intentPreferences.putParcelableArrayListExtra(EXTRA_FILMS,ArrayList<Parcelable>(films))
        //Log.d("_OTUS_","putExtra")
        startActivity(intentPreferences, AndroidOsBundle())
        //getFilm.launch(intentDetails)
    }

    open fun initRecycler(films:MutableList<Film>){
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = FilmItemAdapter(
            films,
            {position -> onFilmDetailsClick(position)},
            {position -> onLikeClick(position)}
        )
    }

    override fun onBackPressed() {
        onCreateDialog()
    }

    private fun onCreateDialog() {
        return AlertDialog.Builder(this)
            .setTitle(R.string.exit)
            .setMessage(getString(R.string.exit_question))
            .setNegativeButton(R.string.no) { dialog, which -> }
            .setPositiveButton(R.string.yes) { dialog, which -> finish()}
            .create()
            .show()
    }

    override fun onSaveInstanceState(outState: AndroidOsBundle){
        super.onSaveInstanceState(outState)
        outState.putInt("select", selected)
        outState.putInt("prevSelect", prevSelected)
        outState.putParcelableArrayList(EXTRA_FILMS, ArrayList<Parcelable>(films))
    }

    fun onFilmDetailsClick(position: Int) {
        prevSelected = selected
        selected = position
        selectFilm(position)
        Log.d("_OTUS_","onFilmDetailsClick $position")
        openDetails(position)
    }

    fun onLikeClick(position: Int){
        films[position].like = !films[position].like
        //recyclerView.adapter?.notifyItemChanged(position)
    }

    fun openDetails(id: Int){
        val intentDetails = Intent(this, DetailsActivity::class.java)
        intentDetails.putExtra(EXTRA_FILM,films[id])
        //Log.d("_OTUS_","putExtra")
        //startActivity(intentDetails, Bundle())
        getFilm.launch(intentDetails)
    }

    var selected: Int = -1
    var prevSelected: Int = -1
    lateinit var films: MutableList<Film>

    private fun createFilms(): MutableList<Film> {
         films = mutableListOf(
          Film(
            0,
            R.string.film1,
            R.drawable.norway,
            R.string.description1,
        ),
         Film(
            1,
            R.string.film2,
            R.drawable.the_wire,
            R.string.description2,
        ),
        Film(
            2,
            R.string.film3,
            R.drawable.true_detective,
            R.string.description3,
        ),
         Film(
             3,
             R.string.film1,
             R.drawable.norway,
             R.string.description1,
         ),
         Film(
             4,
             R.string.film2,
             R.drawable.the_wire,
             R.string.description2,
         ),
         Film(
             5,
             R.string.film3,
             R.drawable.true_detective,
             R.string.description3,
         ),
         Film(
             6,
             R.string.film1,
             R.drawable.norway,
             R.string.description1,
         ),
         Film(
             7,
             R.string.film2,
             R.drawable.the_wire,
             R.string.description2,
         ),
         Film(
             8,
             R.string.film3,
             R.drawable.true_detective,
             R.string.description3,
         ),
         Film(
             9,
             R.string.film1,
             R.drawable.norway,
             R.string.description1,
         ),
         Film(
             10,
             R.string.film2,
             R.drawable.the_wire,
             R.string.description2,
         ),
         Film(
             11,
             R.string.film3,
             R.drawable.true_detective,
             R.string.description3,
         ),
         Film(
             12,
             R.string.film1,
             R.drawable.norway,
             R.string.description1,
         ),
         Film(
             13,
             R.string.film2,
             R.drawable.the_wire,
             R.string.description2,
         ),
         Film(
             14,
             R.string.film3,
             R.drawable.true_detective,
             R.string.description3,
         ),
         Film(
             15,
             R.string.film1,
             R.drawable.norway,
             R.string.description1,
         ),
         Film(
             16,
             R.string.film2,
             R.drawable.the_wire,
             R.string.description2,
         ),
         Film(
             17,
             R.string.film3,
             R.drawable.true_detective,
             R.string.description3,
         ),
         Film(
             18,
             R.string.film1,
             R.drawable.norway,
             R.string.description1,
         ),
         Film(
             19,
             R.string.film2,
             R.drawable.the_wire,
             R.string.description2,
         ),
         Film(
             20,
             R.string.film3,
             R.drawable.true_detective,
             R.string.description3,
         ),
         Film(
             21,
             R.string.film1,
             R.drawable.norway,
             R.string.description1,
         ),
         Film(
             22,
             R.string.film2,
             R.drawable.the_wire,
             R.string.description2,
         ),
         Film(
             23,
             R.string.film3,
             R.drawable.true_detective,
             R.string.description3,
         ),

        )
        //val films = mutableListOf<Film>(film1, film2, film3)
        return films
    }


    fun selectFilm(selected: Int){
        unSelectFilms()
        films[selected].isHighlighted = true
        recyclerView.adapter?.notifyItemChanged(selected)
        recyclerView.adapter?.notifyItemChanged(prevSelected)
    }

    fun unSelectFilms(){
        for (film in films) {
            film.isHighlighted = false
        }
    }

    val getFilm = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
            val data = result.data
            if (result.resultCode == RESULT_OK && data != null){
                val film: Film? = data.getParcelableExtra(DetailsActivity.EXTRA_FILM)
                film?.let{
                    val id = it.id
                    films[id] = it
                    Log.d("_OTUS_", it.comment)
                    Log.d("_OTUS_","${it.like}")
                    Log.d("_OTUS_", "film $id")
                }
            }
    }

    companion object {
        const val EXTRA_FILM = "film"
        const val EXTRA_FILMS = "films"
    }
}

