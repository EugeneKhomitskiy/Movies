package com.example.movies.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies.BuildConfig
import com.example.movies.api.ApiService
import com.example.movies.dto.Movie
import com.example.movies.error.ApiError
import java.lang.Exception
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val apiService: ApiService
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val page = params.key ?: 0
            val response = apiService.getMovies(apiKey = BuildConfig.API_KEY, offset = page)

            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = checkNotNull(response.body()?.results)
            val prevKey = if (page == 0) null else page
            val nextKey = if (body.size < 20) null else page + 20
            LoadResult.Page(
                data = body,
                prevKey = prevKey,
                nextKey = nextKey,
            )
            return LoadResult.Page(
                data = body,
                prevKey = prevKey,
                nextKey = nextKey,
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}