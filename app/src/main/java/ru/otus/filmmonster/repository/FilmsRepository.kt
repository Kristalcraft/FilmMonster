package ru.otus.filmmonster.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.otus.filmmonster.App
import ru.otus.filmmonster.Film

class FilmsRepository(
) {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    fun getPagedFilms(): LiveData<PagingData<Film>>{
        val loader: FilmsPageLoader = { pageIndex, pageSize ->
            getFilms(pageIndex, pageSize)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                /*initialLoadSize = 2*/
            ),
            pagingSourceFactory = { FilmsPagingSource(loader, PAGE_SIZE) }
        ).liveData
    }

    private suspend fun getFilms(pageIndex: Int, pageSize: Int): ArrayList<Film>
    = withContext(ioDispatcher) {
        val list = arrayListOf<Film>()

       val response =  App.instance.api.getTop250Films("TOP_250_BEST_FILMS", pageIndex)

        response.films.forEach { model ->
            list.add(
                Film(
                    model.id,
                    model.name,
                    model.poster,
                    /*model.description*/
                    /*model.like,
                    model.comment*/
                )
            )
        }

        return@withContext list
            }

    companion object {
        const val PAGE_SIZE = 1
    }
}



