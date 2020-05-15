package com.test.currencyexchange.data.repository.exchange

import com.test.currencyexchange.application.system.SchedulerProvider
import com.test.currencyexchange.data.source.entity.ApiCurrency
import com.test.currencyexchange.data.source.entity.DBCurrency
import com.test.currencyexchange.data.source.server.ServerApi
import com.test.currencyexchange.data.source.storage.DatabaseDelegate
import com.test.currencyexchange.domain.entity.CurrencyExchangeRate
import io.reactivex.Single
import java.text.SimpleDateFormat
import java.util.*

class CurrencyExchangeRepositoryImpl(
    private val schedulerProvider: SchedulerProvider,
    private val databaseDelegate: DatabaseDelegate,
    private val serverApi: ServerApi
) : CurrencyExchangeRepository {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    override fun getCurrencyExchange(date: Date): Single<List<CurrencyExchangeRate>> =
        serverApi
            .getCurrencyExchangeRates(dateFormat.format(date))
            .observeOn(schedulerProvider.computation())
            .map { apiCurrencies ->
                apiCurrencies.map { CurrencyExchangeRate(it.id, it.rate, date) }
            }
            .subscribeOn(schedulerProvider.io())

    private fun ApiCurrency.toCurrencyExchangeRate(dateFormat: SimpleDateFormat) =
        CurrencyExchangeRate(
            id,
            rate,
            dateFormat.parse(date) ?: throw IllegalArgumentException("Can't parse Date")
        )


    private fun getDate(stringDate: String): Date {
        return dateFormat.parse(stringDate) ?: throw IllegalArgumentException("Can't parse Date")
    }

    private fun getCurrenciesOld(date: Date): Single<List<ApiCurrency>> =
        serverApi
            .getCurrencyExchangeRates(dateFormat.format(date))
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.computation())

    private fun getSettingsCurrencies(): Single<List<DBCurrency>> =
        databaseDelegate
            .getCurrencies()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.computation())
}