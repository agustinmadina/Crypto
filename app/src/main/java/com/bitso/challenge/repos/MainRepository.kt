package com.bitso.challenge.repos

import com.bitso.challenge.coroutines.DispatcherProvider
import com.bitso.challenge.network.ApiService
import com.bitso.challenge.network.models.Ticker
import kotlinx.coroutines.withContext

class MainRepository(
    private val api: ApiService,
    private val dispatcher: DispatcherProvider
) {

    suspend fun getAllTickers(): List<Ticker> =
        withContext(dispatcher.io()) {
            api.getAllTickers().tickers
        }
}