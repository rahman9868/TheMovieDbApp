package com.example.movieapp.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.data.source.Resource
import com.example.core.domain.model.Movie
import com.example.core.domain.usecase.MovieUseCase

class SearchViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun searchMovies(query: String): LiveData<Resource<List<Movie>>> {
        return movieUseCase.searchMovies(query).asLiveData()
    }
}