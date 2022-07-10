package ru.otus.filmmonster.UI

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.otus.filmmonster.R
import ru.otus.filmmonster.lib.CheckableImageView
import ru.otus.filmmonster.repository.FilmModel

class FilmItemAdapter(
    private val onFilmDetailsClick: (id: Int) -> Unit,
    private val onLikeClick: (id: Int, view: CheckableImageView) -> Unit,
    private val highlightedFilmID: MutableLiveData<Int>
):PagingDataAdapter<FilmModel, FilmItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = FilmItemViewHolder(inflater.inflate(R.layout.film_unit, parent, false))
        highlightedFilmID.observe(viewHolder.itemView.context as LifecycleOwner) {
            checkHighlighted(viewHolder)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: FilmItemViewHolder, position: Int) {
        when (holder) {
            is FilmItemViewHolder -> {
                getItem(position)?.let { holder.bind(it, onFilmDetailsClick, onLikeClick) }
                checkHighlighted(holder)
            }
        }
    }

    private fun checkHighlighted(viewHolder: FilmItemViewHolder) {
        if (viewHolder.filmID == highlightedFilmID.value) {
            viewHolder.itemView.findViewById<TextView>(R.id.film_name).setTextColor(Color.RED)}
        else viewHolder.itemView.findViewById<TextView>(R.id.film_name).setTextColor(Color.BLACK)
    }

class DiffCallback : DiffUtil.ItemCallback<FilmModel>() {

    override fun areItemsTheSame(oldItem: FilmModel, newItem: FilmModel): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: FilmModel, newItem: FilmModel): Boolean {
        return (newItem.id == oldItem.id &&
                newItem.like == oldItem.like &&
                newItem.comment == oldItem.comment)
    }
}
}