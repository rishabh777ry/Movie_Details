package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val repository = MovieRepository()

    // LiveData for search results
    private val _searchResults = MutableLiveData<List<Movie>?>()
    val searchResults: MutableLiveData<List<Movie>?> get() = _searchResults

    // LiveData for selected movie details
    private val _movieDetails = MutableLiveData<MovieDetailResponse>()
    val movieDetails: LiveData<MovieDetailResponse> get() = _movieDetails

    // Fetch search results
    fun fetchSearchResults(query: String) {
        viewModelScope.launch {
            val response = repository.searchMovies(query)
            if (response?.Search != null) {
                _searchResults.postValue(response.Search)
            } else {
                _searchResults.postValue(emptyList())
            }
        }
    }

    // Fetch movie details
    fun fetchMovieDetails(imdbID: String) {
        viewModelScope.launch {
            val details = repository.getMovieDetails(imdbID)
            if (details != null) {
                _movieDetails.postValue(details)
            }
        }
    }
}
