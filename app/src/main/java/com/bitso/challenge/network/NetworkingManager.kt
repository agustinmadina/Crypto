package com.bitso.sample.network

import android.content.Context
import com.bitso.sample.BuildConfig
import com.bitso.sample.coroutines.DispatcherProvider
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

// We can add support to Apollo here.
class NetworkingManager(
    private val context: Context,
    private val dispatcher: DispatcherProvider
) {
    private var okHttpClient: OkHttpClient? = null
    private var noCachedOkHttpClient: OkHttpClient? = null
    private var cachedOkHttpClient: OkHttpClient? = null

    companion object {
        const val BASE_URL = BuildConfig.BASE_URL

        const val CACHE_SIZE: Long = 10 * 1024 * 1024 // 10 MB
        const val CONNECTION_TIME_OUT: Long = 1 // 1 Minute
        const val MAX_CACHED_DATA_DAYS = 7 // The cached data is only valid for 7 days

        const val HEADER_CACHE_CONTROL = "Cache-Control"
        const val HEADER_PRAGMA = "Pragma"

        const val HEADER_AUTH = "Authorization"
        const val HEADER_BEARER = "Bearer "
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

                addInterceptor(provideOfflineCacheInterceptor())
                addNetworkInterceptor(provideCacheInterceptor())
                addNetworkInterceptor(ChuckerInterceptor(context))
                addNetworkInterceptor(provideAuthHeaderInterceptor())
                cache(cache)

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

    private val cache: Cache by lazy {
        Cache(File(context.cacheDir, "http-cache"), CACHE_SIZE)
    }

    private fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            val cacheControl =
                CacheControl.Builder()
                    .maxAge(0, TimeUnit.SECONDS)
                    .build()

            response.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()
        }
    }

    private fun provideOfflineCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()

            val cacheControl = CacheControl.Builder()
                .maxStale(MAX_CACHED_DATA_DAYS, TimeUnit.DAYS)
                .build()

            request = request.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .cacheControl(cacheControl)
                .build()

            chain.proceed(request)
        }
    }

    private fun provideForcedOfflineCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()

            val cacheControl = CacheControl.Builder()
                .maxStale(MAX_CACHED_DATA_DAYS, TimeUnit.DAYS)
                .build()

            request = request.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .cacheControl(cacheControl)
                .build()

            chain.proceed(request)
        }
    }

    private fun provideAuthHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            runBlocking {
                val request = chain.request()
                val accessToken = BuildConfig.READ_ACCESS_TOKEN
                val newRequest = if (!accessToken.isNullOrEmpty()) {
                    request.newBuilder()
                        .addHeader(HEADER_AUTH, HEADER_BEARER + accessToken)
                        .build()
                } else {
                    request
                }

                chain.proceed(newRequest)
            }
        }
    }
}