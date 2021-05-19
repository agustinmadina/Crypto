package com.bitso.challenge.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse(
    @Json(name = "success")
    val movies: Boolean,
    @Json(name = "payload")
    val tickers: List<Ticker>
)