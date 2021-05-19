package com.bitso.challenge.repos

import com.bitso.challenge.coroutines.DispatcherProvider
import com.bitso.challenge.network.ApiService
import com.bitso.challenge.network.models.Movie
import com.bitso.challenge.network.models.MovieDetail
import kotlinx.coroutines.withContext

class MainRepository(
    private val api: ApiService,
    private val dispatcher: DispatcherProvider
) {

    suspend fun getTopRatedMovies(): List<Movie> =
        withContext(dispatcher.io()) {
            api.getTopRatedMovies().movies
        }

    suspend fun getPopularMovies(): List<Movie> =
        withContext(dispatcher.io()) {
            api.getPopularMovies().movies
        }

    suspend fun getUpcomingMovies(): List<Movie> =
        withContext(dispatcher.io()) {
            api.getUpcomingMovies().movies
        }

    suspend fun searchMovie(keyword: String): List<Movie> =
        withContext(dispatcher.io()) {
            api.searchMovie(keyword).movies
        }

    suspend fun getMovieDetails(movieId: Int): MovieDetail =
        withContext(dispatcher.io()) {
            api.getMovieDetail(movieId)
        }
}