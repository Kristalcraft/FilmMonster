package ru.otus.filmmonster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FilmsViewModel : ViewModel() {

    /*private*/ var mFilms = MutableLiveData<ArrayList<Film>>()
    private val mError = MutableLiveData<String>()
    private val mSelectedFilm =  MutableLiveData<Film>()

    init {
        mFilms.value = arrayListOf(
            Film(
                587,
                R.string.film1,
                R.drawable.norway,
                R.string.description1,
            ),
            Film(
                752,
                R.string.film2,
                R.drawable.the_wire,
                R.string.description2,
            ),
            Film(
                785,
                R.string.film3,
                R.drawable.true_detective,
                R.string.description3,
            ),
            Film(
                62,
                R.string.film1,
                R.drawable.norway,
                R.string.description1,
            ),
            Film(
                90,
                R.string.film2,
                R.drawable.the_wire,
                R.string.description2,
            ),
            Film(
                51,
                R.string.film3,
                R.drawable.true_detective,
                R.string.description3,
            ),
            Film(
                367,
                R.string.film1,
                R.drawable.norway,
                R.string.description1,
            ),
            Film(
                514,
                R.string.film2,
                R.drawable.the_wire,
                R.string.description2,
            ),
            Film(
                63,
                R.string.film3,
                R.drawable.true_detective,
                R.string.description3,
            ),
            Film(
                76,
                R.string.film1,
                R.drawable.norway,
                R.string.description1,
            ),
            Film(
                446,
                R.string.film2,
                R.drawable.the_wire,
                R.string.description2,
            ),
            Film(
                22145,
                R.string.film3,
                R.drawable.true_detective,
                R.string.description3,
            ),
            Film(
                5478,
                R.string.film1,
                R.drawable.norway,
                R.string.description1,
            ),
            Film(
                125,
                R.string.film2,
                R.drawable.the_wire,
                R.string.description2,
            ),
            Film(
                983,
                R.string.film3,
                R.drawable.true_detective,
                R.string.description3,
            ),
            Film(
                4,
                R.string.film1,
                R.drawable.norway,
                R.string.description1,
            ),
            Film(
                25,
                R.string.film2,
                R.drawable.the_wire,
                R.string.description2,
            ),
            Film(
                9328,
                R.string.film3,
                R.drawable.true_detective,
                R.string.description3,
            ),
            Film(
                674,
                R.string.film1,
                R.drawable.norway,
                R.string.description1,
            ),
            Film(
                584,
                R.string.film2,
                R.drawable.the_wire,
                R.string.description2,
            ),
            Film(
                5748,
                R.string.film3,
                R.drawable.true_detective,
                R.string.description3,
            ),
            Film(
                5979,
                R.string.film1,
                R.drawable.norway,
                R.string.description1,
            ),
            Film(
                87,
                R.string.film2,
                R.drawable.the_wire,
                R.string.description2,
            ),
            Film(
                153,
                R.string.film3,
                R.drawable.true_detective,
                R.string.description3,
            ),

            )
    }
    val selectedFilm: LiveData<Film> = mSelectedFilm
    val films: LiveData<ArrayList<Film>> = mFilms
    val error: LiveData<String> = mError
    var selected = -1
    var prevSelected: Int = -1

    fun onFilmClick(id: Int){
        prevSelected = selected
        if (prevSelected != -1) mFilms.value?.get(prevSelected)?.isHighlighted = false
        mSelectedFilm.value = getFilmByID(id)
        getFilmByID(id).isHighlighted = true
    }

    fun getFilmByID(id: Int): Film {
        return mFilms.value?.find { it.id == id }?: throw IllegalStateException("Film not found")
    }

    fun onLikeChanged(id: Int){
        var changingFilm = getFilmByID(id)
        val position = mFilms.value?.indexOf(getFilmByID(id))
        changingFilm = changingFilm.copy(like = !changingFilm.like)
        position?.let {
            mFilms.value?.set(it, changingFilm)
        }
        mFilms.value = mFilms.value
    }

    fun onFilmChanged(comment: String, like: Boolean){
        val id = selectedFilm.value?.id
        val changingFilm = selectedFilm.value?.copy()
        changingFilm?.comment = comment
        changingFilm?.like = like
        var position: Int? = null
        position = mFilms.value?.indexOf(selectedFilm.value)

        position?.let {
            if (changingFilm != null) {
                mFilms.value?.set(it, changingFilm)
                selected = position
            }
        }
        mFilms.value = mFilms.value
    }
}