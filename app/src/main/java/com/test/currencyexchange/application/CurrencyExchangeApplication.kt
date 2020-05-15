package com.test.currencyexchange.application

import android.app.Application
import com.test.currencyexchange.di.module.KoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CurrencyExchangeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CurrencyExchangeApplication)
            KoinModule.apply {
                modules(appModule, networkModule, presentationModule)
            }
        }
    }
}