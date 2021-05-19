package com.bitso.sample.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Agustin Madina on 26/04/2021.
 */
@JsonClass(generateAdapter = true)
data class ApiResponse(
    @Json(name = "results")
    val movies: List<Movie>
)