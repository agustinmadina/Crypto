package com.bitso.sample.extensions

import android.content.Context
import com.bitso.sample.R
import retrofit2.HttpException

fun Throwable.getIOErrorMessage(context: Context) = when (this) {
    is HttpException -> context.getString(R.string.http_error)
    else -> context.getString(R.string.unknown_error)
}