package com.example.core.data.source

import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.repository.IMovieRepository
import com.example.core.utils.AppExecutors
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieRepository (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getAllMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovie()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()

    override fun getDetailMovie(id: Int): Flow<Resource<MovieDetail>>  =
        flow {
            emit(Resource.Loading(null))

            try {
                remoteDataSource.getDetailMovie(id).collect { response ->
                    when (response) {
                        is ApiResponse.Success -> {

                            val isFavorite = localDataSource.isFavoriteMovie(id).first()
                            val movieDetail = DataMapper.mapDetailResponseToEntity(response.data,isFavorite)
                            emit(Resource.Success(movieDetail))
                        }
                        is ApiResponse.Error -> {
                            emit(Resource.Error(response.errorMessage, null))
                        }
                        ApiResponse.Empty -> {
                        }
                    }
                }
            } catch (e: Exception) {
                emit(Resource.Error("An error occurred", null))
            }
        }
    override fun getMovieRecommendations(id: Int): Flow<Resource<List<Movie>>>  =
        flow {
            emit(Resource.Loading(null))

            try {
                remoteDataSource.getMovieRecommendations(id).collect { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            val movieEntities = DataMapper.mapResponsesToEntities(response.data)
                            val movies = DataMapper.mapEntitiesToDomain(movieEntities)
                            emit(Resource.Success(movies))
                        }
                        is ApiResponse.Error -> {
                            emit(Resource.Error(response.errorMessage, null))
                        }
                        ApiResponse.Empty -> {
                        }
                    }
                }
            } catch (e: Exception) {
                emit(Resource.Error("An error occurred", null))
            }
        }
    override fun searchMovies(query: String): Flow<Resource<List<Movie>>> =
        flow {
            emit(Resource.Loading(null))

            try {
                remoteDataSource.searchMovies(query).collect { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            val movieEntities = DataMapper.mapResponsesToEntities(response.data)
                            val movies = DataMapper.mapEntitiesToDomain(movieEntities)
                            emit(Resource.Success(movies))
                        }
                        is ApiResponse.Error -> {
                            emit(Resource.Error(response.errorMessage, null))
                        }
                        ApiResponse.Empty -> {
                        }
                    }
                }
            } catch (e: Exception) {
                emit(Resource.Error("An error occurred", null))
            }
        }

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMovie(movieId: Int, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteMovie(movieId, state)
        }
    }
}

