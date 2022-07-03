package ru.otus.filmmonster.UI

import android.graphics.Color
import android.util.Log
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

    fun bind(film: FilmModel, onFilmDetailsClick: (position: Int) -> Unit, onLikeClick: (position: Int, likeView: CheckableImageView) -> Unit){
        filmTitle.text = film.name
        /*filmPoster.setImageResource(film.poster)*/
        likeButton.isChecked = film.like?: false
        /*checkHighlight(film)*/
        Log.d("_OTUS_","film  $adapterPosition  bound")
        detailsButton.setOnClickListener { _ ->
            onFilmDetailsClick(film.id)
        }

        likeButton.setOnClickListener {
                _, -> onLikeClick(film.id, likeButton);
        }

        Glide.with(filmPoster.context)
            .load(film.poster)
            .placeholder(R.drawable.ic_baseline_image_filler)
            .error(com.google.android.material.R.drawable.mtrl_ic_error)
            .centerCrop()
            .into(filmPoster)
    }


    /*fun checkHighlight(film: FilmModel){
        if (!film.isHighlighted) {
            filmTitle.setTextColor(Color.BLACK)
        }else{
            filmTitle.setTextColor(Color.RED)
        }
    }*/
}