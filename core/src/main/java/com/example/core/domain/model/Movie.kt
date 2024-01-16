package com.example.core.domain.model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val overview: String,
    val posterPath: String?,
    val title: String,
    val voteAverage: Double,
    val isFavorite: Boolean
) : Parcelable