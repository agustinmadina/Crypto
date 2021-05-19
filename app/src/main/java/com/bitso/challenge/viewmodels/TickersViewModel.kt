package com.bitso.challenge.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitso.challenge.network.models.Ticker
import com.bitso.challenge.repos.MainRepository
import kotlinx.coroutines.launch
import timber.log.Timber

private const val ERROR_GET_TICKERS = "Error while retrieving tickers."

class TickersViewModel(
    private val mainRepo: MainRepository
) : ViewModel() {

    private val _tickersState = MutableLiveData<TickersState>(TickersState.Loading)
    val tickersState: LiveData<TickersState> = _tickersState

    private val _tickers = MutableLiveData<List<Ticker>>()
    val tickers: LiveData<List<Ticker>> = _tickers

//    private val _movieDetail = MutableLiveData<MovieDetail>()
//    val movieDetail: LiveData<MovieDetail> = _movieDetail

    fun getAllTickers() {
        _tickersState.value = TickersState.Loading
        viewModelScope.launch {
            try {
                _tickers.value = mainRepo.getAllTickers()
                _tickersState.value = TickersState.Success
            } catch (e: Throwable) {
                _tickersState.value = TickersState.Error(e)
                Timber.e(e, ERROR_GET_TICKERS)
            }
        }
    }

//    fun getMovieDetail(movieId: Int) {
//        _moviesState.value = MovieState.Loading
//        viewModelScope.launch {
//            try {
//                _movieDetail.value = mainRepo.getMovieDetails(movieId)
//                _moviesState.value = MovieState.Success
//            } catch (e: Throwable) {
//                _moviesState.value = MovieState.Error(e)
//                Timber.e(e, ERROR_GET_MOVIES)
//            }
//        }
//    }
//
//    fun searchMovie(keyword: String) {
//        _moviesState.value = MovieState.Loading
//        viewModelScope.launch {
//            try {
//                _movies.value = mainRepo.searchMovie(keyword)
//                _moviesState.value = MovieState.Success
//            } catch (e: Throwable) {
//                _moviesState.value = MovieState.Error(e)
//                Timber.e(e, ERROR_GET_MOVIES)
//            }
//        }
//    }

//    fun loadCategory(position: Int) {
//        _tickersState.value = TickersState.Loading
//        viewModelScope.launch {
//            try {
//                when (position) {
//                    0 ->  _tickers.value = mainRepo.getAllTickers()
//                    1 ->  _tickers.value = mainRepo.getPopularMovies()
//                    2 ->  _tickers.value = mainRepo.getUpcomingMovies()
//                }
//                _tickersState.value = TickersState.Success
//            } catch (e: Throwable) {
//                _tickersState.value = TickersState.Error(e)
//                Timber.e(e, ERROR_GET_MOVIES)
//            }
//        }
//    }
}

sealed class TickersState {
    object Loading : TickersState()
    object Success : TickersState()
    data class Error(val exception: Throwable) : TickersState()
}