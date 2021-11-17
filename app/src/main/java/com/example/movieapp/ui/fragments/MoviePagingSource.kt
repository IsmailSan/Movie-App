package com.example.movieapp.ui.fragments

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.api.MovieApi
import com.example.movieapp.data.Movie
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MoviePaging(
    private val movieApi: MovieApi,
    private val query : String?
    ) : PagingSource<Int, Movie>() {

    override suspend fun load(params : LoadParams<Int>): LoadResult<Int,Movie> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response =  if(query!= null) movieApi.searchMovies(query,position) else movieApi.getNowPlayingMovies(position)
            val movies = response.results

            LoadResult.Page(
                data = movies,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )

        } catch (e : IOException) {
            LoadResult.Error(e)
        } catch (e : HttpException) {
            LoadResult.Error(e)
        }
    }
}