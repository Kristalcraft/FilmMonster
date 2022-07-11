package ru.otus.filmmonster.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.otus.filmmonster.App

class FilmsRepository(
) {

    lateinit var filmsSource: FilmsPagingSource
    val repoError = MutableLiveData<String>()

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getFilms(pageIndex: Int, pageSize: Int): ArrayList<FilmModel>
            = withContext(ioDispatcher) {
        /*Log.d("__OTUS__", "getFilms $pageIndex")*/
        val films = getFilmsFromDB(pageIndex, pageSize)
        /*Log.d("__OTUS__", "${films.size}")*/
        if (films.size < 20) {
            getFilmsFromAPI(pageIndex, pageSize)
            return@withContext getFilmsFromDB(pageIndex, pageSize)
        }
        else {Log.d("__OTUS__", "${films.size} return films")
            return@withContext films}
    }

    private suspend fun getFilmsFromAPI(pageIndex: Int, pageSize: Int)
            = withContext(ioDispatcher) {
        val list = arrayListOf<FilmModel>()

        try {
            val response = App.instance.api.getTop250Films("TOP_250_BEST_FILMS", pageIndex)
            /*Log.d("__OTUS__", "response from server $pageIndex")*/
            response.films.forEachIndexed { i, model ->
                list.add(
                    FilmModel(
                        (pageIndex - 1)*pageSize + i,
                        model.id,
                        model.name,
                        model.poster,
                    )
                )
            }

            DBinstance.getDBinstance(App.instance.applicationContext)?.getFilmDao()
                ?.insert(list)
        } catch (e: Throwable){
            repoError.postValue("Failed to get films from server: ${e.message}")
        }
    }

    private suspend fun getFilmsFromDB(pageIndex: Int, pageSize: Int): ArrayList<FilmModel>
            = withContext(ioDispatcher){
        val offset = (pageIndex - 1) * pageSize
        val DBlist = arrayListOf<FilmModel>()
        DBinstance.getDBinstance(App.instance.applicationContext)?.getFilmDao()
            ?.getFilms(pageSize, offset)?.forEach { model ->
                DBlist.add(FilmModel(
                    model.positionID,
                    model.id,
                    model.name,
                    model.poster,
                    model.description?:"",
                    model.like?: false,
                    model.comment?:""
                ))
                /*Log.d("__OTUS__", "Подгружен из БД: ${model.name} ")*/
            }
        return@withContext DBlist
    }

    suspend fun getFavoriteFilms(pageIndex: Int, pageSize: Int): ArrayList<FilmModel>
            = withContext(ioDispatcher){
        val offset = (pageIndex - 1) * pageSize
        val DBlist = arrayListOf<FilmModel>()
        DBinstance.getDBinstance(App.instance.applicationContext)?.getFilmDao()
            ?.getFavoriteFilms(pageSize, offset)?.forEach { model ->
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
                    repoError.postValue("Failed to get film from server: ${t.message}")
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

    companion object {
        const val PAGE_SIZE = 20
    }
}



