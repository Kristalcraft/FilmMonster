package ru.otus.filmmonster.UI

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.otus.filmmonster.R
import ru.otus.filmmonster.lib.CheckableImageView
import ru.otus.filmmonster.repository.FilmModel

class FilmItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
    private val filmTitle: TextView = itemView.findViewById(R.id.film_name)
    private val filmPoster: ImageView = itemView.findViewById(R.id.film_poster)
    private val detailsButton: Button = itemView.findViewById(R.id.button_details)
    private val likeButton: CheckableImageView = itemView.findViewById(R.id.like_button)
    var filmID: Int = 0

    fun bind(film: FilmModel, onFilmDetailsClick: (position: Int) -> Unit, onLikeClick: (position: Int, likeView: CheckableImageView) -> Unit){
        filmID = film.id
        filmTitle.text = film.name
        likeButton.isChecked = film.like?: false
        /*Log.d("__OTUS__","film  ${film.positionID}  bound")*/
        detailsButton.setOnClickListener { _ ->
            onFilmDetailsClick(film.id)
        }

        likeButton.setOnClickListener {
                _, -> onLikeClick(film.id, likeButton);
        }

        Glide.with(filmPoster.context)
            .load(film.poster)
            .timeout(10000)
            .placeholder(R.drawable.ic_baseline_image_filler)
            .error(com.google.android.material.R.drawable.mtrl_ic_error)
            .centerCrop()
            .into(filmPoster)
    }
}