package ru.otus.filmmonster.UI

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.otus.filmmonster.Film
import ru.otus.filmmonster.R
import ru.otus.filmmonster.lib.CheckableImageView

class FilmItemAdapter (
    private var films: ArrayList<Film>,
    private val onFilmDetailsClick: (id: Int) -> Unit,
    private val onLikeClick: (id: Int, view: CheckableImageView) -> Unit
    ):PagingDataAdapter<Film, FilmItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FilmItemViewHolder(inflater.inflate(R.layout.film_unit, parent, false))
    }

    override fun onBindViewHolder(holder: FilmItemViewHolder, position: Int) {
        when (holder) {
            is FilmItemViewHolder -> {
                getItem(position)?.let { holder.bind(it, onFilmDetailsClick, onLikeClick) }
            }
        }
    }

    /*override fun getItemCount(): Int = films.size*/

    /*fun swapLists(newFilms: ArrayList<Film>){
        val res = DiffUtil.calculateDiff(DiffCallback(films, newFilms))
        films.clear()
        films.addAll(newFilms)
        res.dispatchUpdatesTo(this)}
    *//*fun updateFilmsList(newFilmsList: ArrayList<Film>) {
        films.clear()
        films.addAll(newFilmsList)
        notifyDataSetChanged()
    }*//*

    }*/

class DiffCallback : DiffUtil.ItemCallback<Film>() {


    /* private val oldList: ArrayList<Film> = oldFilms
    private val newList: ArrayList<Film> = newFilms

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }*/

    /*override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        *//*val oldFilm: Film = oldList[oldItemPosition]
        val newFilm: Film = newList[newItemPosition]*//*
        return oldFilm.id == newFilm.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        *//*val oldFilm: Film = oldList[oldItemPosition]
        val newFilm: Film = newList[newItemPosition]*//*
        return (oldFilm.id == newFilm.id &&
                oldFilm.like == newFilm.like &&
                oldFilm.comment == newFilm.comment &&
                oldFilm.isHighlighted == newFilm.isHighlighted)
    }*/

    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
        return (newItem.id == oldItem.id &&
                newItem.like == oldItem.like &&
                newItem.comment == oldItem.comment &&
                newItem.isHighlighted == oldItem.isHighlighted)
    }
}
}