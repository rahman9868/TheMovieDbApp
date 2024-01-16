package com.example.core.domain.usecase

import com.example.core.data.source.Resource
import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow


class MovieInteractor(private val movieRepository: IMovieRepository): MovieUseCase {

    override fun getAllMovie() = movieRepository.getAllMovie()
    override fun getDetailMovie(id: Int) = movieRepository.getDetailMovie(id)
    override fun getMovieRecommendations(id: Int) = movieRepository.getMovieRecommendations(id)
    override fun searchMovies(query: String) = movieRepository.searchMovies(query)

    override fun getFavoriteMovie() = movieRepository.getFavoriteMovie()

    override fun setFavoriteMovie(movieId: Int, state: Boolean) = movieRepository.setFavoriteMovie(movieId, state)
}