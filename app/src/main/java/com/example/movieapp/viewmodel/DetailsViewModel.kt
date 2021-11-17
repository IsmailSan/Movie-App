package com.example.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.movieapp.data.Movie
import com.example.movieapp.db.FavoriteMovie
import com.example.movieapp.repository.FavoriteMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: FavoriteMovieRepository) : ViewModel() {

        fun addToFavorite(movie : Movie) {
            CoroutineScope(Dispatchers.IO).launch {
                repository.addToFavorite(
                    FavoriteMovie(
                        movie.id,
                        movie.original_title,
                        movie.overview,
                        movie.poster_path
                    )
                )
            }
        }

    suspend fun checkMovie(id: String) = repository.checkMovie(id)

    fun removeFromFavorite(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.removeFromMovie(id)
        }
    }
}