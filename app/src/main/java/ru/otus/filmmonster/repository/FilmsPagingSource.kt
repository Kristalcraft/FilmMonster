package ru.otus.filmmonster.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.lang.Exception

typealias FilmsPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<FilmModel>

class FilmsPagingSource (
    private val loader: FilmsPageLoader,
    private val pageSize: Int
        ) : PagingSource <Int, FilmModel>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmModel> {
        val pageIndex = params.key ?: 1

        return try {
            val films = loader.invoke(pageIndex, pageSize)
            Log.d("__OTUS__","prevKey:" + (if (pageIndex == 1) null else pageIndex - 1).toString()
                    + "nextKey:" + (if (films.size < pageSize) null else pageIndex + 1).toString())
            return LoadResult.Page(
                data = films,
                prevKey = if (pageIndex == 1) null else pageIndex - 1,
                nextKey = if (films.size < pageSize) null else pageIndex + 1,
                itemsBefore = pageIndex * pageSize
            )
        } catch (e: Exception){
            LoadResult.Error(
                throwable =  e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FilmModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        Log.d("__OTUS__", "RefreshKey:" + (page.prevKey?.plus(1) ?: page.nextKey?.minus(1).toString()))
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
}