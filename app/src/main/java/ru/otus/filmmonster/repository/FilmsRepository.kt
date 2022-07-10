package ru.otus.filmmonster.repository

import android.app.Activity
import android.content.SyncContext
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
import androidx.paging.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.otus.filmmonster.App
/*import ru.otus.filmmonster.Film*/
import ru.otus.filmmonster.FilmsViewModel
import ru.otus.filmmonster.MainActivity
import ru.otus.filmmonster.UI.FilmsFragment
import java.util.concurrent.Executors
import kotlin.coroutines.coroutineContext

class FilmsRepository(
) {

    lateinit var filmsSource: FilmsPagingSource
    val repoError = MutableLiveData<String>()

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getFilms(pageIndex: Int, pageSize: Int): ArrayList<FilmModel>
            = withContext(ioDispatcher) {
        val list = arrayListOf<FilmModel>()

        try {
            val response = App.instance.api.getTop250Films("TOP_250_BEST_FILMS", pageIndex)

        response.films.forEachIndexed { i, model ->
            list.add(
                FilmModel(
                    (pageIndex - 1)*20 + i,
                    model.id,
                    model.name,
                    model.poster,
                )
            )
        }

        DBinstance.getDBinstance(App.instance.applicationContext)?.getFilmDao()
            ?.insert(list)
         } catch (e: Throwable){
            repoError.postValue("Failed to get films from server")
         }
        val offset = (pageIndex - 1) * 20
        val DBlist = arrayListOf<FilmModel>()
            DBinstance.getDBinstance(App.instance.applicationContext)?.getFilmDao()
                ?.getFilms(20, offset)?.forEach { model ->
                    DBlist.add(FilmModel(
                        model.positionID,
                        model.id,
                        model.name,
                        model.poster,
                        model.description?:"",
                        model.like?: false,
                        model.comment?:""
                    ))
                    Log.d("__OTUS__", "Подгружен из БД: ${model.name} ")
                }

        return@withContext DBlist
    }

    fun getFilm(filmID: Int, onFilmRecievedListener: (FilmModel?) -> Unit){
        onFilmRecievedListener.invoke(
            getFilmFromDB(filmID)
        )
        App.instance.api.getFilm(filmID).enqueue(
            object: Callback<FilmModel> {
                override fun onResponse(call: Call<FilmModel>, response: Response<FilmModel>) {
                    response.body()?.let {
                        updateDescriptionIntoDB(it.id ,it.description)
                    }
                    onFilmRecievedListener.invoke(
                        getFilmFromDB(filmID)
                    )
                }
                override fun onFailure(call: Call<FilmModel>, t: Throwable) {
                }
            }
        )
    }

    fun updateDescriptionIntoDB(filmID: Int, description: String?){
        if (description != null) {
            DBinstance.getDBinstance(App.instance.applicationContext)?.getFilmDao()
                ?.updateFilmDescription(filmID.toString(), description)
        }
    }

    fun updateFilmInDB(filmModel: FilmModel){

                DBinstance.getDBinstance(App.instance.applicationContext)?.getFilmDao()
                    ?.update(filmModel)

    }

    fun getFilmFromDB(filmID: Int): FilmModel?{
        val filmModel = DBinstance.getDBinstance(App.instance.applicationContext)?.getFilmDao()
                    ?.getFilm(filmID.toString())

        return filmModel

    }

    /*fun filmModelToFilm(filmModel: FilmModel?): Film?{
        var film:Film? = null
        filmModel.let{
            film = Film(filmModel!!.id, filmModel.name,filmModel.poster,filmModel.description?:"",filmModel.like?: false,filmModel.comment?:"")
        }
        return film
    }*/

    /*fun filmToFilmModel(film: Film?): FilmModel?{
        var filmModel:FilmModel? = null
        film.let{
            filmModel = FilmModel(film!!.id, film.name,film.poster,film.description?:"",film.like?: false,film.comment?:"")
        }
        return filmModel
    }*/


    companion object {
        const val PAGE_SIZE = 1
    }
}



