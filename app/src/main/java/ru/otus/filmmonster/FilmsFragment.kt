package ru.otus.filmmonster

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView


/**
 * A simple [Fragment] subclass.
 * Use the [FilmsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
open class FilmsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    open lateinit var films: MutableList<Film>
    var selected: Int = -1
    var prevSelected: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            films = it.getParcelableArrayList<Film>(EXTRA_FILMS)?: arrayListOf()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        recyclerView.adapter?.notifyDataSetChanged()
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.film_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycler(films)
    }

    open fun initRecycler(films:MutableList<Film>){
        recyclerView = view?.findViewById<RecyclerView>(R.id.recycler) as RecyclerView
        val horizontalItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider_drawable)
            ?.let { horizontalItemDecoration.setDrawable(it) }
        recyclerView.addItemDecoration(horizontalItemDecoration)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val verticalItemDecoration =
                DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL)
            ContextCompat.getDrawable(requireContext(), R.drawable.divider_drawable)
                ?.let { verticalItemDecoration.setDrawable(it) }
            recyclerView.addItemDecoration(verticalItemDecoration)
        }
        recyclerView.adapter = FilmItemAdapter(
            films,
            {id -> onFilmDetailsClick(id)},
            {id -> onLikeClick(id)}
        )
    }

    fun onFilmDetailsClick(id: Int) {
        prevSelected = selected
        selected = id
        selectFilm(id)
        /*Log.d("_OTUS_","onFilmDetailsClick $id")
        openDetails(id)*/

        (activity as MainActivity).onFilmDetailsClick(getFilmByID(id))
    }

    open fun onLikeClick(id: Int){
        getFilmByID(id).like = !getFilmByID(id).like
        //recyclerView.adapter?.notifyItemChanged(position)
    }

    open fun getFilmByID(id: Int): Film {
        return films.find { it.id == id }?: throw IllegalStateException("No film data provided")
    }

    fun selectFilm(selected: Int){
        unSelectFilms()
        getFilmByID(selected).isHighlighted = true
        recyclerView?.adapter?.notifyItemChanged(selected)
        recyclerView?.adapter?.notifyItemChanged(prevSelected)
    }

    fun unSelectFilms(){
        for (film in films) {
            film.isHighlighted = false
        }
    }

    companion object {
        const val EXTRA_FILM = "film"
        const val EXTRA_FILMS = "films"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FilmsFragment.
         */
        @JvmStatic
        fun newInstance(films: MutableList<Film>) =
            FilmsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(EXTRA_FILMS, ArrayList<Parcelable>(films))
                }
            }


    }
}

