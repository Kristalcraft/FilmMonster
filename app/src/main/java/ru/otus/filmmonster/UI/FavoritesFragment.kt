package ru.otus.filmmonster.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

import ru.otus.filmmonster.R
import ru.otus.filmmonster.lib.CheckableImageView
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
    /*fun getFilmByID(id: Int): Film {
        return favoriteFilms.find { it.id == id }?: throw IllegalStateException("No film data provided")
    }*/

    /*override fun onFilmDetailsClick(id: Int) {
        prevSelected = selected
        selected = favoriteFilms.indexOf(getFilmByID(id))
        selectFilm(selected)
        (activity as MainActivity).onFilmDetailsClick(getFilmByID(id))
    }*/

    /*override fun onLikeClick(id: Int, likeView: CheckableImageView){
        val position = favoriteFilms.indexOf(getFilmByID(id))
        var film = getFilmByID(id)
        getFilmByID(id).like = !getFilmByID(id).like
        likeView.toggle()
        view?.let {
            Snackbar.make(
                it,
                if (getFilmByID(id).like) R.string.likeSnackbar
                else R.string.dislikeSnackbar, Snackbar.LENGTH_LONG
            )
                .setAction(R.string.cancel) {
                    favoriteFilms.add(position, film)
                    getFilmByID(id).like = true
                    likeView.toggle()
                    recyclerView.adapter?.notifyItemInserted(position)
                }
                .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                .setAnchorView(likeView)
                .show()
        }

        favoriteFilms.remove(getFilmByID(id))
        recyclerView.adapter?.notifyItemRemoved(position)
    }*/

     /*fun selectFilm(selected: Int){
        unSelectFilms()
        favoriteFilms[selected].isHighlighted = true
        recyclerView.adapter?.notifyItemChanged(selected)
        recyclerView.adapter?.notifyItemChanged(prevSelected)
        Log.d("_OTUS_", "selected: $selected, prevselected: $prevSelected")
    }*/


    /*override fun unSelectFilms(){
        for (film in favoriteFilms) {
            film.isHighlighted = false
        }
    }*/

    /*companion object {

        @JvmStatic
        fun newInstance(films: MutableList<Film>) =
            FavoritesFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(EXTRA_FILMS, ArrayList<Parcelable>(films))
                }
            }
    }*/
}