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

    /*fun getFilmByID(id: Int): Film {
        return mFilms.value?.find { it.id == id }?: throw IllegalStateException("Film not found")
    }*/

    /*fun onLikeChanged(id: Int){
        var changingFilm = getFilmByID(id)
        val position = mFilms.value?.indexOf(getFilmByID(id))
        changingFilm = changingFilm.copy(like = !changingFilm.like)
        position?.let {
            mFilms.value?.set(it, changingFilm)
        }
        mFilms.value = mFilms.value
    }*/

    fun onFilmChanged(comment: String, like: Boolean){
        /*val id = selectedFilm.value?.id*/
        val changingFilm = selectedFilm.value?.copy()
        changingFilm?.comment = comment
        changingFilm?.like = like
        if (changingFilm != null) {
            filmsRepository.updateFilmInDB(changingFilm)
        }
        filmsRepository.filmsSource.invalidate()

        /*var position: Int? = null
        position = mFilms.value?.indexOf(selectedFilm.value)

        position?.let {
            if (changingFilm != null) {
                mFilms.value?.set(it, changingFilm)
                selected = position
            }
        }
        mFilms.value = mFilms.value*/
    }


}