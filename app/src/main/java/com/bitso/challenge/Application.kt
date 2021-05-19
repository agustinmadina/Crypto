package com.bitso.challenge

import android.app.Application
import com.bitso.challenge.di.initKoin
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