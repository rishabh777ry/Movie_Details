package com.example.myapplication

class MovieRepository {
    private val api = RetrofitInstance.api

    suspend fun searchMovies(query: String): SearchResponse? {
        return try {
            val response = api.searchMovies(query, "bd127174").execute()
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getMovieDetails(imdbID: String): MovieDetailResponse? {
        return try {
            val response = api.getMovieDetails(imdbID, "bd127174").execute()
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }
}
