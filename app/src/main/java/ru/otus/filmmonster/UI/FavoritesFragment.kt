package ru.otus.filmmonster.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar

import ru.otus.filmmonster.R
import ru.otus.filmmonster.lib.CheckableImageView
import ru.otus.filmmonster.repository.FilmModel


/**
 * A simple [Fragment] subclass.
 * Use the [FavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritesFragment : FilmsFragment() {

    var favoriteFilms: MutableList<FilmModel> = mutableListOf()
     var films: MutableList<FilmModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

       /* selected = favoriteFilms.indexOf(favoriteFilms.find { film -> film.isHighlighted }?: -1)
        initRecycler(favoriteFilms)*/
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