package ru.otus.filmmonster

import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import ru.otus.filmmonster.repository.FilmModel
import ru.otus.filmmonster.repository.FilmsPageLoader
import ru.otus.filmmonster.repository.FilmsPagingSource
import ru.otus.filmmonster.repository.FilmsRepository

class FilmsViewModel(
    private val filmsRepository: FilmsRepository
) : ViewModel() {

    /*private*/ var mFilms = MutableLiveData<ArrayList<FilmModel>>()
    private val mError = filmsRepository.repoError
    private val mSelectedFilm = MutableLiveData<FilmModel>()
    var highlightedFilmID = MutableLiveData<Int>()


    val pagedFilms: LiveData<PagingData<FilmModel>>
        get() {
            val loader: FilmsPageLoader = { pageIndex, pageSize ->
                filmsRepository.getFilms(pageIndex, pageSize)
            }
             return Pager(
                config = PagingConfig(
                    pageSize = FilmsRepository.PAGE_SIZE,
                    enablePlaceholders = false,
                    /*initialLoadSize = 2*/
                ),
                pagingSourceFactory = {
                    filmsRepository.filmsSource = FilmsPagingSource(loader, 20)
                    filmsRepository.filmsSource
                }
            ).liveData
                 .cachedIn(viewModelScope)
        }

    init {
        highlightedFilmID.value = -1

    }
    val selectedFilm: LiveData<FilmModel> = mSelectedFilm
    val error: LiveData<String> = mError



    fun onFilmClick(id: Int){
        highlightedFilmID.value = id
        filmsRepository.getFilm(id) {it: FilmModel? -> mSelectedFilm.value = it }
    }

    fun onLikeChanged(id: Int){
        val changingFilm = filmsRepository.getFilmFromDB(id)
        if (changingFilm?.like != null) {
            changingFilm.like = !changingFilm.like!!
            filmsRepository.updateFilmInDB(changingFilm)
        }
    }

    fun onFilmChanged(comment: String, like: Boolean){
        val changingFilm = selectedFilm.value?.copy()
        changingFilm?.comment = comment
        changingFilm?.like = like
        if (changingFilm != null) {
            filmsRepository.updateFilmInDB(changingFilm)
        }
        filmsRepository.filmsSource.invalidate()
    }


}