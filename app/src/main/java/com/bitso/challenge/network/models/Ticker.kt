package com.bitso.challenge.network.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Ticker(

    @Json(name = "high")
    val high: String?,

    @Json(name = "last")
    val last: String,

    @Json(name = "created_at")
    val createdAt: String?,

    @Json(name = "book")
    val book: String?,

    @Json(name = "volume")
    val volume: String?,

    @Json(name = "vwap")
    val vwap: String?,

    @Json(name = "ask")
    val ask: String?,

    @Json(name = "bid")
    val bid: String?,

    @Json(name = "change_24")
    val change24: String?

) :Parcelable
