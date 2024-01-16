package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.ListMovieResponse
import com.example.core.data.source.remote.response.MovieDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getList(): ListMovieResponse
    @GET("movie/{id}")
    suspend fun getDetail(@Path("id") id: Int): MovieDetailResponse?
    @GET("movie/{id}/recommendations")
    suspend fun getMovieRecommendations(@Path("id") id: Int): ListMovieResponse
    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String): ListMovieResponse

}