package com.example.movieapp.api


import com.example.movieapp.data.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "35f53d0ba85920579c16eb46540c34ed"
    }

    @GET("movie/now_playing?api_key=$API_KEY")
    suspend fun getNowPlayingMovies(
        @Query("page") position : Int
    ) : MovieResponse

    @GET("search/movie?api_key=$API_KEY")
    suspend fun  searchMovies(
        @Query("query") query : String,
        @Query("page") page : Int
    ):MovieResponse
}