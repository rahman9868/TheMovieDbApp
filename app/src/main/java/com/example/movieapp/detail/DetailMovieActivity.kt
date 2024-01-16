package com.example.movieapp.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.core.data.source.Resource
import com.example.core.domain.model.Genre
import com.example.movieapp.R
import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail
import com.example.core.ui.MovieAdapter
import com.example.core.ui.MoviePosterAdapter
import com.example.movieapp.databinding.ActivityDetailMovieBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }

    private val detailMovieViewModel: DetailMovieViewModel by viewModel()
    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var posterAdapter: MoviePosterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val movie = intent.getParcelableExtra<Movie>(EXTRA_DATA)
        initAdapter()
        movie?.let {
            supportActionBar?.title = movie.title
            getDetailMovie(movie.id)
            getMovieRecommendations(movie.id)
        }
    }

    private fun initAdapter() {

        posterAdapter = MoviePosterAdapter()
        posterAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailMovieActivity::class.java)
            intent.putExtra(EXTRA_DATA, selectedData)
            startActivity(intent)
        }
    }

    private fun getDetailMovie(id: Int) {
        detailMovieViewModel.getDetailMovie(id).observe(this) { detailMovie ->
            if (detailMovie != null) {
                when (detailMovie) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        showDetailMovie(detailMovie.data)
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text =
                            detailMovie.message ?: getString(R.string.something_wrong)
                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun getMovieRecommendations(id: Int) {
        detailMovieViewModel.getMovieRecommendations(id).observe(this) { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        showMovieRecommendations(movies.data)
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text =
                            movies.message ?: getString(R.string.something_wrong)
                    }
                    else -> {

                    }
                }
            }
        }

        with(binding.content.rvMovie) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = posterAdapter
        }
    }

    private fun showMovieRecommendations(data: List<Movie>?) {
            data?.let {
                posterAdapter.setData(it)
            }
    }

    private fun showDetailMovie(detailMovie: MovieDetail?) {
        Log.v("ADX", "detailMovie $detailMovie")
        detailMovie?.let {
            supportActionBar?.title = detailMovie.title
            binding.content.tvDetailDescription.text = detailMovie.overview
            if(detailMovie.posterPath != null ) {
                Glide.with(this@DetailMovieActivity)
                    .load("$BASE_IMAGE_URL${detailMovie.posterPath}")
                    .into(binding.ivDetailImage)
            }

            var statusFavorite = detailMovie.isFavorite
            setStatusFavorite(statusFavorite)
            binding.fab.setOnClickListener {
                statusFavorite = !statusFavorite
                detailMovieViewModel.setFavoriteMovie(detailMovie.id, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
            binding.content.tvGenre.text = showGenres(detailMovie.genres)
            val roundedRating = Math.round(detailMovie.voteAverage * 2) / 2.0f
            binding.content.crbRating.rating = roundedRating
            binding.content.tvRating.text = roundedRating.toString()
        }
    }

    fun showGenres(genres: List<Genre>): String {
        val result = StringBuilder()
        for (genre in genres) {
            result.append("${genre.name}, ")
        }

        if (result.isEmpty()) {
            return result.toString()
        }

        return result.substring(0, result.length - 2)
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite_white))
        }
    }
}
