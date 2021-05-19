package com.bitso.challenge.network

import com.bitso.challenge.network.models.ApiResponse
import com.bitso.challenge.network.models.MovieDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("ticker")
    suspend fun getAllTickers(
    ): ApiResponse
}