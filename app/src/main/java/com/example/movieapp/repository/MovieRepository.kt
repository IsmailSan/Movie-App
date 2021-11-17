package com.example.movieapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.movieapp.api.MovieApi
import com.example.movieapp.ui.fragments.MoviePaging
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val movieApi : MovieApi) {
    fun getNowPlayingMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 18,
                maxSize = 100,
                enablePlaceholders = false
            ),
                    pagingSourceFactory = {MoviePaging(movieApi, null)}
        ).liveData

    fun getSearchMovies(query : String) =
        Pager(
            config = PagingConfig(
                pageSize = 18,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {MoviePaging(movieApi, query)}
        ).liveData
}