package ru.otus.filmmonster

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FilmItemAdapter (
    private val films: MutableList<Film>,
    private val onFilmDetailsClick: (position: Int) -> Unit,
    private val onLikeClick: (position: Int) -> Unit
    ):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        return FilmItemViewHolder(inflater.inflate(R.layout.film_unit, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is FilmItemViewHolder -> {
                holder.bind(films[position],onFilmDetailsClick, onLikeClick)
            }
        }
    }

    override fun getItemCount(): Int = films.size



//    override fun getItemViewType(position: Int): int{
//        return ITEM_VIEW_TYPE
//    }
}