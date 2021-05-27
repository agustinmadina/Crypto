package com.bitso.challenge.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitso.challenge.network.models.ChartEntry
import com.bitso.challenge.network.models.Ticker
import com.bitso.challenge.repos.MainRepository
import kotlinx.coroutines.delay
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

    private val _tickerChartInfo = MutableLiveData<List<ChartEntry>>()
    val tickerChartInfo: LiveData<List<ChartEntry>> = _tickerChartInfo

    fun getAllTickersEvery30Secs() {
        _tickersState.value = TickersState.Loading
        viewModelScope.launch {
            while (true) {
                getAllTickers()
                delay(30000)
            }
        }
    }

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

    fun getTickerChartInfo(book: String, time: String) {
        _tickersState.value = TickersState.Loading
        viewModelScope.launch {
            try {
                _tickerChartInfo.value = mainRepo.getTickerChartInfo(book, time)
                _tickersState.value = TickersState.Success
            } catch (e: Throwable) {
                _tickersState.value = TickersState.Error(e)
                Timber.e(e, ERROR_GET_TICKERS)
            }
        }
    }
}

sealed class TickersState {
    object Loading : TickersState()
    object Success : TickersState()
    data class Error(val exception: Throwable) : TickersState()
}