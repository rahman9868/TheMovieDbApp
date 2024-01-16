package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val id: Int,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double
)

