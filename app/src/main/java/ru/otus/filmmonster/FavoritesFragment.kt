package ru.otus.filmmonster

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * A simple [Fragment] subclass.
 * Use the [FavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritesFragment : FilmsFragment() {

    var favoriteFilms: MutableList<Film> = mutableListOf()
    override var films: MutableList<Film> = mutableListOf()

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
        arguments?.let {
            films = it.getParcelableArrayList<Film>(EXTRA_FILMS)?: arrayListOf()
        }
        for (film in films){
            if (film.like){
                favoriteFilms.add(film)
            }
        }
        initRecycler(favoriteFilms)
    }

    override fun getFilmByID(id: Int): Film{
        return favoriteFilms.find { it.id == id }?: throw IllegalStateException("No film data provided")
    }

    override fun onLikeClick(id: Int){
        getFilmByID(id).like = !getFilmByID(id).like
        val position = favoriteFilms.indexOf(getFilmByID(id))
        favoriteFilms.remove(getFilmByID(id))
        recyclerView.adapter?.notifyItemRemoved(position)
    }

    companion object {

        @JvmStatic
        fun newInstance(films: MutableList<Film>) =
            FavoritesFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(EXTRA_FILMS, ArrayList<Parcelable>(films))
                }
            }
    }
}