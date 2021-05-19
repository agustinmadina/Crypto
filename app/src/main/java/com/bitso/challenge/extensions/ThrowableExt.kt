package com.bitso.challenge.extensions

import android.content.Context
import com.bitso.challenge.R
import retrofit2.HttpException

fun Throwable.getIOErrorMessage(context: Context) = when (this) {
    is HttpException -> context.getString(R.string.error)
    else -> context.getString(R.string.error)
}