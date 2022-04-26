package ru.otus.filmmonster

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.otus.filmmonster.R

class FilmItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
    private val filmTitle: TextView = itemView.findViewById(R.id.film_name)
}