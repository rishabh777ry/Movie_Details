package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDbApiService {
    @GET("/")
    fun searchMovies(
        @Query("s") searchTerm: String,
        @Query("apikey") apiKey: String
    ): Call<SearchResponse>

    @GET("/")
    fun getMovieDetails(
        @Query("i") imdbId: String,
        @Query("apikey") apiKey: String
    ): Call<MovieDetailResponse>
}
