package com.example.movies.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movies.api.ApiService
import com.example.movies.dto.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: ApiService
) {
    val data: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 1, enablePlaceholders = false),
        pagingSourceFactory = { MoviePagingSource(apiService) },
    ).flow
}