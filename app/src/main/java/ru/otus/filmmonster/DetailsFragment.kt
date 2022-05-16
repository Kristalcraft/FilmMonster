package ru.otus.filmmonster

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class DetailsFragment : Fragment() {

    private var film: Film? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            film = it.getParcelable<Film>(EXTRA_FILM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filmLocal = film?: throw IllegalStateException("No film data provided")
            //Log.d("_OTUS_","intent OK")
            view.findViewById<ImageView>(R.id.det_poster).setImageResource(filmLocal.poster)
            view.findViewById<TextView>(R.id.det_film_name).setText(filmLocal.name)
            view.findViewById<TextView>(R.id.det_description).setText(filmLocal.description)
            view.findViewById<EditText>(R.id.det_comment).setText(filmLocal.comment)
            view.findViewById<CheckBox>(R.id.det_like).isChecked = filmLocal.like

            view.findViewById<Button>(R.id.det_invite).setOnClickListener(){
                val intentShare = Intent(Intent.ACTION_SEND)
                intentShare.putExtra(Intent.EXTRA_TEXT, "Советую посмотреть фильм ${getString(filmLocal.name)}")
                intentShare.type = "text/plain"
                startActivity(Intent.createChooser(intentShare, "Поделиться"))
            }
            film = filmLocal

    }

    companion object {

        const val EXTRA_FILM = "film"
        @JvmStatic
        fun newInstance(film: Film) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_FILM, film)
                }
            }
    }
}