package ru.otus.filmmonster.UI

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import kotlinx.coroutines.launch
import ru.otus.filmmonster.MainActivity.Companion.FAVORITE_FILMS
import ru.otus.filmmonster.repository.FilmModel


/**
 * A simple [Fragment] subclass.
 * Use the [FavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritesFragment : FilmsFragment() {

    override fun observePagedFilms(filmAdapter: FilmItemAdapter) {
        viewModel.pagedFavoriteFilms.observe(viewLifecycleOwner, Observer<PagingData<FilmModel>> {
            lifecycleScope.launch { filmAdapter.submitData(it) }
        })
    }

    override fun onResume(){
        super.onResume()
        viewModel.fragmentName = FAVORITE_FILMS
    }
}