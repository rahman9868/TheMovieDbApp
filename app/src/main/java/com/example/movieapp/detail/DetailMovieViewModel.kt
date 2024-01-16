package com.example.movieapp.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.data.source.Resource
import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.usecase.MovieUseCase

class DetailMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun getDetailMovie(movieId: Int): LiveData<Resource<MovieDetail>> {
        return movieUseCase.getDetailMovie(movieId).asLiveData()
    }
    fun getMovieRecommendations(movieId: Int): LiveData<Resource<List<Movie>>> {
        return movieUseCase.getMovieRecommendations(movieId).asLiveData()
    }
    fun setFavoriteMovie(movieId: Int, newStatus:Boolean) =
        movieUseCase.setFavoriteMovie(movieId, newStatus)
}

