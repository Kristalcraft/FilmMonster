package ru.otus.filmmonster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FilmItemAdapter (
    private val films: MutableList<Film>,
    private val onDetailClickListener: View.OnClickListener
    ):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        return FilmItemViewHolder(inflater.inflate(R.layout.film_unit, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is FilmItemViewHolder -> {
                holder.bind(films[position],onDetailClickListener )
            }
        }
    }

    override fun getItemCount(): Int = films.size

//    override fun getItemViewType(position: Int): int{
//        return ITEM_VIEW_TYPE
//    }
}