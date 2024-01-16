package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    val id: Int,
    val genres: List<GenreResponse>,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double
)