package com.bitso.challenge.network

import android.content.Context
import com.bitso.challenge.BuildConfig
import com.bitso.challenge.coroutines.DispatcherProvider
import com.chuckerteam.chucker.api.ChuckerInterceptor
import kotlinx.coroutines.runBlocking
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class NetworkingManager(
    private val context: Context
) {
    private var noCachedOkHttpClient: OkHttpClient? = null

    companion object {
        const val BASE_URL = BuildConfig.BASE_URL
        const val CONNECTION_TIME_OUT: Long = 1 // 1 Minute
    }

    /**
     * Returns a Retrofit instance which stores all the request on cache and if there is
     * no internet connection retrieves the cached data. Useful to quickly support offline mode.
     */
    val retrofitInstance: Retrofit by lazy {
        noCachedOkHttpClient = createOkHttpClientInstance()
        createRetrofitInstance(noCachedOkHttpClient!!)
    }

    private fun createOkHttpClientInstance(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })

                addNetworkInterceptor(ChuckerInterceptor(context))

                // Timeout Configs
                connectTimeout(CONNECTION_TIME_OUT, TimeUnit.MINUTES)
                readTimeout(CONNECTION_TIME_OUT, TimeUnit.MINUTES)
            }
            .build()
    }

    private fun createRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}