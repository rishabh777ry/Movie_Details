package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator

class MovieListFragment : Fragment() {

    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var recyclerView: RecyclerView
    private var movies = listOf<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie_list, container, false)

        // Initialize CircularProgressIndicator
        progressIndicator = rootView.findViewById(R.id.circularProgressIndicator)

        // Initialize RecyclerView
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Get the movies passed from MovieSearch fragment
        movies = arguments?.getParcelableArrayList<Movie>("MOVIE_LIST") ?: listOf()

        // Show the progress indicator while loading data
        progressIndicator.visibility = View.VISIBLE

        // If no movies are passed, show a message
        if (movies.isEmpty()) {
            Toast.makeText(context, "No movies to display", Toast.LENGTH_SHORT).show()
        }

        // Set the adapter with the movie list
        movieListAdapter = MovieListAdapter(movies) { imdbId ->
            // Handle movie click here (navigate to MovieDetailFragment)
            val fragment = MovieDetailFragment.newInstance(imdbId)
            parentFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit()
        }
        recyclerView.adapter = movieListAdapter

        // Hide the progress indicator when data is loaded
        progressIndicator.visibility = View.GONE

        return rootView
    }

    companion object {
        // Create a new instance of the fragment, passing the movie list
        @JvmStatic
        fun newInstance(movies: List<Movie>) = MovieListFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList("MOVIE_LIST", ArrayList(movies))
            }
        }
    }
}
