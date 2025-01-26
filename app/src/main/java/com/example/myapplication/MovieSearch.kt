package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieSearch : Fragment() {

    private lateinit var editTextView: EditText
    private lateinit var submitButton: Button

    // Reference to the API service
    private val apiService = RetrofitInstance.api

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_movie_search, container, false)

        // Initialize UI elements
        editTextView = rootView.findViewById(R.id.editTextView)
        submitButton = rootView.findViewById(R.id.submitButton)

        // Set onClick listener for the "Search" button
        submitButton.setOnClickListener {
            val movieName = editTextView.text.toString()

            // Check if user has entered something in the EditText
            if (movieName.isNotEmpty()) {
                Log.d(">>//", " Movie ka naam daal diya h $movieName")
                searchMovies(movieName)  // Perform movie search
            } else {
                Toast.makeText(context, "Please enter a movie name", Toast.LENGTH_SHORT).show()
            }
        }

        return rootView
    }

    /**
     * Function to call the API and search movies based on the entered movie name.
     * @param movieName The name of the movie to search for
     */
    private fun searchMovies(movieName: String) {
        val apiKey = "bd127174"  // Replace with your actual API key
        Log.d(">>//", "Making request to fetch movies with query: $movieName")
        // Make the API call to search movies
        val call = apiService.searchMovies(movieName, apiKey)

        // Enqueue the API call
        call.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful) {
                    Log.d(">>//", "Response success: ${response.body()}")
                    val movies = response.body()?.Search
                    if (movies != null && movies.isNotEmpty()) {
                        Log.d(">>//", "Movies found: ${movies.size}")  // Check the number of movies
                        showMoviesList(movies)
                    } else {
                        Toast.makeText(context, "No movies found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d(">>//", "Error in response: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Error fetching movies", Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Toast.makeText(context, "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.d(">>//","Request failed: ${t.message}")
            }
        })
    }

    /**
     * Function to display the list of movies (you can implement RecyclerView or another UI element)
     * @param movies List of movies from the search response
     */
    private fun showMoviesList(movies: List<Movie>) {
        val transaction = parentFragmentManager.beginTransaction()
        val movieListFragment = MovieListFragment.newInstance(movies)  // Pass the movies list
        transaction.replace(R.id.frameLayout, movieListFragment)  // Update with your FragmentContainer ID
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
