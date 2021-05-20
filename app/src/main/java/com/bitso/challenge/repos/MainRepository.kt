package com.bitso.challenge.repos

import com.bitso.challenge.coroutines.DispatcherProvider
import com.bitso.challenge.network.ApiService
import com.bitso.challenge.network.models.ChartEntry
import com.bitso.challenge.network.models.Ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class MainRepository(
    private val api: ApiService,
    private val dispatcher: DispatcherProvider
) {

    val poll = flow {
        while (true) {
            emit(getAllTickers())
            delay(30000)
        }
    }

    suspend fun getAllTickers(): List<Ticker> =
        withContext(dispatcher.io()) {
            api.getAllTickers().tickers
        }

    suspend fun getTickerChartInfo(book: String, time: String): List<ChartEntry> =
        withContext(dispatcher.io()) {
            api.getTickerChartInfo(book, time)
        }
}