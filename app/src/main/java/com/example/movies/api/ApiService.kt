package com.example.movies.api

import com.example.movies.dto.Movie
import com.example.movies.dto.Results
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("all.json")
    suspend fun getMovies(
        @Query("api-key") apiKey: String,
        @Query("offset") offset: Int
    ): Response<Results<List<Movie>>>
}