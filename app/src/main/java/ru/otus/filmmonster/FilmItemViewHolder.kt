package ru.otus.filmmonster

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.otus.filmmonster.lib.CheckableImageView

class FilmItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
    private val filmTitle: TextView = itemView.findViewById(R.id.film_name)
    private val filmPoster: ImageView = itemView.findViewById(R.id.film_poster)
    private val detailsButton: Button = itemView.findViewById(R.id.button_details)
    private val likeButton: CheckableImageView = itemView.findViewById(R.id.like_button)

    fun bind(film: Film, onFilmDetailsClick: (position: Int) -> Unit, onLikeClick: (position: Int) -> Unit){
        filmTitle.setText(film.name)
        filmPoster.setImageResource(film.poster)
        likeButton.isChecked = film.like
        checkHighlight(film)
        Log.d("_OTUS_","adapterPosition $adapterPosition")
        Log.d("_OTUS_","adapterPosition ${filmTitle.id}")
        detailsButton.setOnClickListener { _ ->
            onFilmDetailsClick(adapterPosition)
        }

        likeButton.setOnClickListener {
                _ -> onLikeClick(adapterPosition);
            likeButton.toggle()
        }
    }


    fun checkHighlight(film: Film){
        if (!film.isHighlighted) {
            filmTitle.setTextColor(Color.BLACK)
        }else{
            filmTitle.setTextColor(Color.RED)
        }
    }
}