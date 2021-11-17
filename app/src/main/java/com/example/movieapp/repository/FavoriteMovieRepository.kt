package com.example.movieapp.repository

import com.example.movieapp.db.FavoriteMovie
import com.example.movieapp.db.FavoriteMovieDao
import javax.inject.Inject
import javax.inject.Singleton


class FavoriteMovieRepository @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
) {

    suspend fun addToFavorite(favoriteMovie: FavoriteMovie) =
        favoriteMovieDao.addToFavorite(favoriteMovie)

    fun getFavoriteMovies() = favoriteMovieDao.getFavoriteMovie()

    suspend fun checkMovie(id: String) = favoriteMovieDao.checkMovie(id)

    suspend fun removeFromMovie(id: String) {
        favoriteMovieDao.removeFromFavorite(id)
    }

}