package ru.otus.filmmonster

import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.otus.filmmonster.lib.CheckableImageView

open class FilmsFragment : Fragment() {

    private val viewModel: FilmsViewModel by activityViewModels()
    lateinit var recyclerView: RecyclerView
    /*private var films = viewModel.films.value*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {
            films = it.getParcelableArrayList<Film>(EXTRA_FILMS)?: arrayListOf()
        }*/

        /*selected = films.indexOf(films.find { film -> film.isHighlighted })*/

        /*parentFragmentManager.setFragmentResultListener(DETAILS_RESULT, this
        ) { requestKey, result ->
            val film = result.getParcelable<Film>(EXTRA_FILM)
            film?.let{
                films[it.id] = film
                recyclerView.adapter?.notifyItemChanged(film.id)
            }
        }*/
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        /*recyclerView.adapter?.notifyDataSetChanged()*/
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycler()
        Toast.makeText(context, "onViewCreated", Toast.LENGTH_SHORT).show()
    }

    open fun initRecycler(){
        Toast.makeText(context, "initRecycler", Toast.LENGTH_SHORT).show()
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

        recyclerView.adapter =
            FilmItemAdapter(
                arrayListOf(),
                {id -> onFilmDetailsClick(id)},
                {id, likeView -> onLikeClick(id, likeView)}
            )

        viewModel.films.observe(viewLifecycleOwner, Observer<ArrayList<Film>>{
            (recyclerView.adapter as FilmItemAdapter).swapLists(it)
        })
    }



    open fun onFilmDetailsClick(id: Int) {
        viewModel.onFilmClick(id)
        /*selectFilm(id)*/
        (activity as MainActivity).onFilmDetailsClick(id)
    }

    open fun onLikeClick(id: Int, likeView: CheckableImageView){
        viewModel.onLikeChanged(id)
        likeView.toggle()
        view?.let {
            Snackbar.make(
                it,
                if (viewModel.getFilmByID(id).like) R.string.likeSnackbar
                else R.string.dislikeSnackbar, Snackbar.LENGTH_SHORT
            )
                .setAction(R.string.cancel) {
                    viewModel.onLikeChanged(id)
                    likeView.toggle()
                }
                .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                .show()
        }

    }

/*    open fun getFilmByID(id: Int): Film {
        return films.find { it.id == id }?: throw IllegalStateException("No film data provided")
    }*/

/*    open fun selectFilm(selected: Int){
        recyclerView.adapter?.notifyItemChanged(selected)
        recyclerView.adapter?.notifyItemChanged(prevSelected)
        //Log.d("_OTUS_", "selected: $selected, prevselected: $prevSelected")
    }*/

/*    open fun unSelectFilms(){
        for (film in films) {
            film.isHighlighted = false
        }
    }*/

    companion object {
        const val EXTRA_FILM = "film"
        const val EXTRA_FILMS = "films"
        const val DETAILS_RESULT = "detailsResult"

        @JvmStatic
        fun newInstance() =
            FilmsFragment()/*.apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(EXTRA_FILMS, ArrayList<Parcelable>(films))
                }
            }*/


    }
}

