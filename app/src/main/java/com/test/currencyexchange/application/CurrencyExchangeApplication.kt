package com.test.currencyexchange.application

import android.app.Application
import android.util.Log
import com.test.currencyexchange.di.module.KoinModule
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CurrencyExchangeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        RxJavaPlugins.setErrorHandler {
            Log.e(this::class.java.simpleName, "Undelivered error: $it")
        }

        startKoin {
            androidLogger()
            androidContext(this@CurrencyExchangeApplication)
            KoinModule.apply {
                modules(appModule, networkModule, presentationModule)
            }
        }
    }
}