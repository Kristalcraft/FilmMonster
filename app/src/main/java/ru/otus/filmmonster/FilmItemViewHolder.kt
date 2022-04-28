package ru.otus.filmmonster

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

    fun bind(film: Film, onFilmDetailsClick: (position: Int) -> Unit){
        filmTitle.setText(film.name)
        filmPoster.setImageResource(film.poster)
        detailsButton.setOnClickListener ({ _ ->
            onFilmDetailsClick(adapterPosition)
        })
    }
}