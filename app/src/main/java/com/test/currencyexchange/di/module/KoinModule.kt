package com.test.currencyexchange.di.module

import com.test.currencyexchange.application.system.SchedulerProvider
import com.test.currencyexchange.data.repository.exchange.CurrencyExchangeRepository
import com.test.currencyexchange.data.repository.exchange.CurrencyExchangeRepositoryImpl
import com.test.currencyexchange.data.repository.settings.CurrencySettingsRepository
import com.test.currencyexchange.data.repository.settings.CurrencySettingsRepositoryImpl
import com.test.currencyexchange.data.source.storage.DatabaseDelegate
import com.test.currencyexchange.di.provider.DatabaseProvider
import com.test.currencyexchange.di.provider.OkHttpClientProvider
import com.test.currencyexchange.di.provider.RetrofitProvider
import com.test.currencyexchange.domain.interactor.CurrencyExchangeInteractor
import com.test.currencyexchange.domain.interactor.CurrencySettingsInteractor
import com.test.currencyexchange.presentation.presenter.exchange.CurrencyExchangePresenter
import com.test.currencyexchange.presentation.presenter.settings.CurrencySettingsPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

object KoinModule {

    val appModule = module {
        factory { SchedulerProvider() }
        single { DatabaseProvider(get()).get() }
        factory { DatabaseDelegate(get()) }
    }

    val networkModule = module {
        single(named("server_url")) { "http://www.nbrb.by/" }
        factory { OkHttpClientProvider().get() }
        single { RetrofitProvider(get(), get(named("server_url"))).get() }

        factory { CurrencyExchangeInteractor(get(), get(), get()) }
        factory<CurrencyExchangeRepository> { CurrencyExchangeRepositoryImpl(get(), get(), get()) }

        factory { CurrencySettingsInteractor(get(), get()) }
        factory<CurrencySettingsRepository> { CurrencySettingsRepositoryImpl(get(), get(), get()) }
    }

    val presentationModule = module {
        factory { CurrencyExchangePresenter(get()) }
        factory { CurrencySettingsPresenter(get()) }
    }
}