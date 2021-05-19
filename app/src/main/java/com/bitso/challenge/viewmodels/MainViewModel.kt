package com.bitso.challenge.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitso.challenge.network.models.Movie
import com.bitso.challenge.network.models.MovieDetail
import com.bitso.challenge.repos.MainRepository
import kotlinx.coroutines.launch
import timber.log.Timber

private const val ERROR_GET_MOVIES = "Error while retrieving movies."

class MainViewModel(
    private val mainRepo: MainRepository
) : ViewModel() {

    private val _moviesState = MutableLiveData<MovieState>(MovieState.Loading)
    val moviesState: LiveData<MovieState> = _moviesState

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _movieDetail = MutableLiveData<MovieDetail>()
    val movieDetail: LiveData<MovieDetail> = _movieDetail

    fun getTopRatedMovies() {
        _moviesState.value = MovieState.Loading
        viewModelScope.launch {
            try {
                _movies.value = mainRepo.getTopRatedMovies()
                _moviesState.value = MovieState.Success
            } catch (e: Throwable) {
                _moviesState.value = MovieState.Error(e)
                Timber.e(e, ERROR_GET_MOVIES)
            }
        }
    }

    fun getMovieDetail(movieId: Int) {
        _moviesState.value = MovieState.Loading
        viewModelScope.launch {
            try {
                _movieDetail.value = mainRepo.getMovieDetails(movieId)
                _moviesState.value = MovieState.Success
            } catch (e: Throwable) {
                _moviesState.value = MovieState.Error(e)
                Timber.e(e, ERROR_GET_MOVIES)
            }
        }
    }

    fun searchMovie(keyword: String) {
        _moviesState.value = MovieState.Loading
        viewModelScope.launch {
            try {
                _movies.value = mainRepo.searchMovie(keyword)
                _moviesState.value = MovieState.Success
            } catch (e: Throwable) {
                _moviesState.value = MovieState.Error(e)
                Timber.e(e, ERROR_GET_MOVIES)
            }
        }
    }

    fun loadCategory(position: Int) {
        _moviesState.value = MovieState.Loading
        viewModelScope.launch {
            try {
                when (position) {
                    0 ->  _movies.value = mainRepo.getTopRatedMovies()
                    1 ->  _movies.value = mainRepo.getPopularMovies()
                    2 ->  _movies.value = mainRepo.getUpcomingMovies()
                }
                _moviesState.value = MovieState.Success
            } catch (e: Throwable) {
                _moviesState.value = MovieState.Error(e)
                Timber.e(e, ERROR_GET_MOVIES)
            }
        }
    }
}

sealed class MovieState {
    object Loading : MovieState()
    object Success : MovieState()
    data class Error(val exception: Throwable) : MovieState()
}