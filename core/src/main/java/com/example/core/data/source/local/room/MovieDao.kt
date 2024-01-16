package com.example.core.data.source.local.room

import androidx.room.*
import com.example.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getAllMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie where isFavorite = 1")
    fun getFavoriteMovie(): Flow<List<MovieEntity>>

    @Query("SELECT isFavorite FROM movie WHERE id = :movieId")
    fun isFavoriteMovie(movieId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: List<MovieEntity>)

    @Query("UPDATE movie SET isFavorite = :isFavorite WHERE id = :movieId")
    fun updateFavoriteMovie(movieId: Int, isFavorite: Boolean)
}
