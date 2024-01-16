package com.example.core.utils

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.remote.response.GenreResponse
import com.example.core.data.source.remote.response.MovieDetailResponse
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.domain.model.Genre
import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail


object DataMapper {
    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                overview = it.overview,
                title = it.title,
                voteAverage = it.voteAverage,
                posterPath = it.posterPath,
                isFavorite = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                voteAverage = it.voteAverage,
                isFavorite = it.isFavorite,
            )
        }

    fun mapDomainToEntity(input: Movie) = MovieEntity(
        id = input.id,
        title = input.title,
        overview = input.overview,
        posterPath = input.posterPath,
        voteAverage = input.voteAverage,
        isFavorite = input.isFavorite
    )

    fun mapDetailResponseToEntity(data: MovieDetailResponse, isFavorite: Boolean) = MovieDetail (
        id = data.id,
        title = data.title,
        genres = mapGenreResponseToEntity(data.genres),
        overview = data.overview,
        posterPath = data.posterPath,
        voteAverage = data.voteAverage,
        isFavorite = isFavorite
    )

    private fun mapGenreResponseToEntity(data: List<GenreResponse>) = data.map { Genre(it.id,it.name) }
}