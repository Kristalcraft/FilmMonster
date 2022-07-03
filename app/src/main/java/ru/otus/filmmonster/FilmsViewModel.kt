package ru.otus.filmmonster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import ru.otus.filmmonster.repository.FilmModel
import ru.otus.filmmonster.repository.FilmsRepository

class FilmsViewModel(
    private val filmsRepository: FilmsRepository
) : ViewModel() {

    /*private*/ var mFilms = MutableLiveData<ArrayList<FilmModel>>()
    private val mError = MutableLiveData<String>()
    private val mSelectedFilm =  MutableLiveData<FilmModel>()

    val pagedFilms: LiveData<PagingData<FilmModel>>

    init {

        pagedFilms = filmsRepository.getPagedFilms()
    }
    val selectedFilm: LiveData<FilmModel> = mSelectedFilm
    val error: LiveData<String> = mError
    var selected = -1
    var prevSelected: Int = -1

    fun onFilmClick(id: Int){
        prevSelected = selected
        filmsRepository.getFilm(id) {it: FilmModel? -> mSelectedFilm.value = it }
        /*getFilmByID(id).isHighlighted = true*/
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