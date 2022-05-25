package ru.otus.filmmonster

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.os.Bundle as AndroidOsBundle
import com.google.android.material.navigation.NavigationBarView

open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: AndroidOsBundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkSavedState(savedInstanceState)

        openFilms(savedInstanceState)

        val navigation: BottomNavigationView= findViewById(R.id.bottomNavigation)

        navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.films -> openFilms(savedInstanceState)
                R.id.favorites -> openPreferences()
            }
            true
        }
    }

    fun openFilms(savedInstanceState: android.os.Bundle?){
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FilmsFragment.newInstance(films))
                .commit()
        }
        else {
            supportFragmentManager.findFragmentById(R.id.fragment_container)?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FavoritesFragment.newInstance(films))
            .commit()
    }

    fun onFilmDetailsClick(film: Film) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, DetailsFragment.newInstance(film), DETAILS)
            .addToBackStack(DETAILS)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0){
            supportFragmentManager.popBackStack( DETAILS, 1)
        } else {
            onCreateDialog()
        }
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

    open fun onLikeClick(id: Int){
        getFilmByID(id).like = !getFilmByID(id).like
        //recyclerView.adapter?.notifyItemChanged(position)
    }

    open fun getFilmByID(id: Int): Film {
        return films.find { it.id == id }?: throw IllegalStateException("No film data provided")
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

    companion object {
        const val EXTRA_FILM = "film"
        const val EXTRA_FILMS = "films"
        const val DETAILS = "fragment_details"
    }
}

