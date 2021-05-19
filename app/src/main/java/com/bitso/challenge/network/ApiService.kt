package com.bitso.challenge.network

import com.bitso.challenge.network.models.ApiResponse
import retrofit2.http.GET

interface ApiService {

    @GET("ticker")
    suspend fun getAllTickers(
    ): ApiResponse
}