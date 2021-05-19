package com.bitso.sample

import android.app.Application
import com.bitso.sample.di.initKoin
import org.koin.core.KoinExperimentalAPI
import timber.log.Timber

@KoinExperimentalAPI
class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}