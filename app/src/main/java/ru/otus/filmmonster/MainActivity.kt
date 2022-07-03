package ru.otus.filmmonster

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.os.Bundle as AndroidOsBundle
import ru.otus.filmmonster.UI.DetailsFragment
import ru.otus.filmmonster.UI.FavoritesFragment
import ru.otus.filmmonster.UI.FilmsFragment
import ru.otus.filmmonster.repository.FilmsRepository

open class MainActivity : AppCompatActivity() {

    /*private val viewModel: FilmsViewModel by viewModels()*/
    private lateinit var viewModel: FilmsViewModel

    override fun onCreate(savedInstanceState: AndroidOsBundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, FilmsViewModelFactory(FilmsRepository()))[FilmsViewModel::class.java]
        /*checkSavedState(savedInstanceState)*/
        openFilms(savedInstanceState)

        /*openFilms(savedInstanceState)*/

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
                .replace(R.id.fragment_container, FilmsFragment.newInstance())
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

    /*fun checkSavedState(savedInstanceState: android.os.Bundle?){
        if (savedInstanceState == null) {
            viewModel.films.observe(this, Observer<ArrayList<Film>>{ repos -> this.films = repos; openFilms(savedInstanceState)})
            selected = -1
            prevSelected = -1
        } else {
            selected = savedInstanceState.getInt("select")
            prevSelected = savedInstanceState.getInt("prevSelect")
            viewModel.films.observe(this, Observer<ArrayList<Film>>{ repos -> this.films = repos; openFilms(savedInstanceState)})
        }
    }*/

    private fun openPreferences() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FavoritesFragment())
            .commit()
    }

    fun onFilmDetailsClick(id: Int) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, DetailsFragment(), DETAILS)
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
    }




    var selected: Int = -1
    var prevSelected: Int = -1


    companion object {
        const val EXTRA_FILM = "film"
        const val EXTRA_FILMS = "films"
        const val DETAILS = "fragment_details"
    }
}

class FilmsViewModelFactory(val filmsRepository: FilmsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FilmsViewModel(filmsRepository) as T
    }
}

