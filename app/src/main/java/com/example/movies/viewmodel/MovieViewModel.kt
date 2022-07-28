package com.example.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.movies.dto.Movie
import com.example.movies.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    movieRepository: MovieRepository
): ViewModel() {

    val data: Flow<PagingData<Movie>> = movieRepository.data
}