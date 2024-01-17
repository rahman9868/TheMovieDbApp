package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetail(
    val id: Int,
    val overview: String,
    val genres: List<Genre>,
    val posterPath: String?,
    val title: String,
    val voteAverage: Double,
    val isFavorite: Boolean
) : Parcelable