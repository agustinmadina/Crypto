package com.bitso.challenge.network

import com.bitso.challenge.network.models.ApiResponse
import com.bitso.challenge.network.models.ChartEntry
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("v3/ticker")
    suspend fun getAllTickers(
    ): ApiResponse

    @GET("trade/chartJSON/{book}/{time}")
    suspend fun getTickerChartInfo(
        @Path("book") book: String,
        @Path("time") time: String
    ): List<ChartEntry>
}