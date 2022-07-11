package ru.otus.filmmonster.UI

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import ru.otus.filmmonster.FilmsViewModel
import ru.otus.filmmonster.MainActivity
import ru.otus.filmmonster.R

class DetailsFragment : Fragment() {

    private val viewModel: FilmsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedFilm.observe(viewLifecycleOwner) {
            it.apply {
                val poster = view.findViewById<ImageView>(R.id.det_poster)

                Glide.with(poster.context)
                    .load(this.poster)
                    .placeholder(R.drawable.ic_baseline_image_filler)
                    .error(com.google.android.material.R.drawable.mtrl_ic_error)
                    .centerCrop()
                    .into(poster)

                view.findViewById<TextView>(R.id.det_film_name).text = name
                view.findViewById<TextView>(R.id.det_description).text = description
                view.findViewById<EditText>(R.id.det_comment).setText(comment)
                view.findViewById<CheckBox>(R.id.det_like).isChecked = like ?: false
                view.findViewById<Button>(R.id.det_invite).setOnClickListener() {
                    val intentShare = Intent(Intent.ACTION_SEND)
                    intentShare.putExtra(Intent.EXTRA_TEXT, "Советую посмотреть фильм $name")
                    intentShare.type = "text/plain"
                    startActivity(Intent.createChooser(intentShare, "Поделиться"))
                }
            } ?: throw IllegalStateException("No film data provided")
            val toolbar: Toolbar = view.findViewById(R.id.toolbar)
            toolbar.setNavigationOnClickListener { view -> (activity as MainActivity).onBackPressed() }
        }
    }

    override fun onPause() {
        viewModel.onFilmChanged(
            view?.findViewById<EditText>(R.id.det_comment)?.text.toString(),
            view?.findViewById<CheckBox>(R.id.det_like)!!.isChecked
        )
        super.onPause()
    }
}