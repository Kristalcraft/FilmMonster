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

    private lateinit var viewModel: FilmsViewModel

    override fun onCreate(savedInstanceState: AndroidOsBundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, FilmsViewModelFactory(FilmsRepository()))[FilmsViewModel::class.java]

        val navigation: BottomNavigationView= findViewById(R.id.bottomNavigation)
        navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.films -> openFilms(savedInstanceState)
                R.id.favorites -> openFavorites(savedInstanceState)
            }
            true
        }
        when (viewModel.fragmentName) {
            FILMS -> openFilms(savedInstanceState)
            FAVORITE_FILMS -> openFavorites(savedInstanceState)
            DETAILS -> viewModel.selectedFilm.value?.id?.let { onFilmDetailsClick(it) }
        }
    }

    fun openFilms(savedInstanceState: android.os.Bundle?){
        viewModel.fragmentName = FILMS
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FilmsFragment(), FILMS)
                .commit()
        }
        else {
            val filmsFragment = supportFragmentManager.findFragmentByTag(FILMS)?:  FilmsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, filmsFragment)
                .commit()

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun openFavorites(savedInstanceState: android.os.Bundle?) {
        viewModel.fragmentName = FAVORITE_FILMS
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FavoritesFragment(), FAVORITE_FILMS)
                .commit()
        }
        else {
            val favoritesFragment = supportFragmentManager.findFragmentByTag(FAVORITE_FILMS)?: FavoritesFragment()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, favoritesFragment)
                    .commit()
        }
    }

    fun onFilmDetailsClick(id: Int) {
        viewModel.fragmentName = DETAILS
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

    companion object {
        const val FAVORITE_FILMS = "favorite_films"
        const val FILMS = "fragment_films"
        const val DETAILS = "fragment_details"
    }
}

class FilmsViewModelFactory(val filmsRepository: FilmsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FilmsViewModel(filmsRepository) as T
    }
}

