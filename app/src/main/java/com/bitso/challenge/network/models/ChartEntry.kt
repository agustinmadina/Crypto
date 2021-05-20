package com.bitso.challenge.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat

@JsonClass(generateAdapter = true)
class ChartEntry(
    @Json(name = "date")
    val date: String,

    @Json(name = "dated")
    val dated: String,

    @Json(name = "value")
    val value: String,

    @Json(name = "volume")
    val volume: String,

    @Json(name = "open")
    val open: String,

    @Json(name = "low")
    val low: String,

    @Json(name = "high")
    val high: String,

    @Json(name = "close")
    val close: String,

    @Json(name = "vwap")
    val vwap: String,
) {
    val timestamp: Long
        get() = SimpleDateFormat("yyyy-MM-dd").parse(date).time
}