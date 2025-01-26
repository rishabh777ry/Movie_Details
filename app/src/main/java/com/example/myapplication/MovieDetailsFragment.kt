package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.CircularProgressIndicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailFragment : Fragment() {

    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var movieTitle: TextView
    private lateinit var movieYear: TextView
    private lateinit var movieRated: TextView
    private lateinit var movieReleased: TextView
    private lateinit var movieRuntime: TextView
    private lateinit var movieGenre: TextView
    private lateinit var movieDirector: TextView
    private lateinit var movieWriter: TextView
    private lateinit var movieActors: TextView
    private lateinit var moviePlot: TextView
    private lateinit var movieLanguage: TextView
    private lateinit var moviePoster: ImageView

    private val apiService = RetrofitInstance.api

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_movie_details, container, false)
        progressIndicator = rootView.findViewById<CircularProgressIndicator>(R.id.circularProgressIndicator)

        // Initialize UI elements
        movieTitle = rootView.findViewById(R.id.movieTitle)
        movieYear = rootView.findViewById(R.id.movieYear)
        movieRated = rootView.findViewById(R.id.movieRated)
        movieReleased = rootView.findViewById(R.id.movieReleased)
        movieRuntime = rootView.findViewById(R.id.movieRuntime)
        movieGenre = rootView.findViewById(R.id.movieGenre)
        movieDirector = rootView.findViewById(R.id.movieDirector)
        movieWriter = rootView.findViewById(R.id.movieWriter)
        movieActors = rootView.findViewById(R.id.movieActors)
        moviePlot = rootView.findViewById(R.id.moviePlot)
        movieLanguage = rootView.findViewById(R.id.movieLanguage)
        moviePoster = rootView.findViewById(R.id.moviePoster)

        // Fetch movie details from arguments and API
        val imdbId = arguments?.getString("IMDB_ID") ?: return rootView
        fetchMovieDetails(imdbId)

        return rootView
    }

    /**
     * Fetch movie details from API based on imdbId.
     * @param imdbId The imdb ID of the selected movie
     */
    private fun fetchMovieDetails(imdbId: String) {
        val apiKey = "bd127174"  // Replace with your actual API key
        progressIndicator.visibility = View.VISIBLE
        // Make the API call to fetch movie details
        val call = apiService.getMovieDetails(imdbId, apiKey)

        // Enqueue the API call
        call.enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>) {
                progressIndicator.visibility = View.GONE
                if (response.isSuccessful) {
                    val movie = response.body()

                    // Check if data is available
                    if (movie != null) {
                        displayMovieDetails(movie)
                    } else {
                        Toast.makeText(context, "Failed to load movie details", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Error fetching movie details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                progressIndicator.visibility = View.GONE
                Toast.makeText(context, "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * Display the movie details in the UI.
     * @param movie The movie details fetched from API
     */
    private fun displayMovieDetails(movie: MovieDetailResponse) {
        movieTitle.text = movie.Title
        movieYear.text = "Year: ${movie.Year}"
        movieRated.text = "Rated: ${movie.Rated}"
        movieReleased.text = "Released: ${movie.Released}"
        movieRuntime.text = "Runtime: ${movie.Runtime}"
        movieGenre.text = "Genre: ${movie.Genre}"
        movieDirector.text = "Director: ${movie.Director}"
        movieWriter.text = "Writer: ${movie.Writer}"
        movieActors.text = "Actors: ${movie.Actors}"
        moviePlot.text = "Plot: ${movie.Plot}"
        movieLanguage.text = "Language: ${movie.Language}"

        Glide.with(this)
            .load(movie.Poster)
            .into(moviePoster)
    }

    companion object {
        // Create a new instance of the fragment, passing the imdbId as an argument
        @JvmStatic
        fun newInstance(imdbId: String) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("IMDB_ID", imdbId)
                }
            }
    }
}
