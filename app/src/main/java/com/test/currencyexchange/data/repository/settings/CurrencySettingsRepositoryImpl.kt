package com.test.currencyexchange.data.repository.settings

import com.test.currencyexchange.application.system.SchedulerProvider
import com.test.currencyexchange.data.source.entity.ApiCurrency
import com.test.currencyexchange.data.source.entity.DBCurrency
import com.test.currencyexchange.data.source.server.ServerApi
import com.test.currencyexchange.data.source.storage.DatabaseDelegate
import com.test.currencyexchange.domain.entity.CurrencyParameters
import com.test.currencyexchange.extensions.today
import io.reactivex.Completable
import io.reactivex.Single
import java.text.SimpleDateFormat
import java.util.*

class CurrencySettingsRepositoryImpl(
    private val schedulerProvider: SchedulerProvider,
    private val databaseDelegate: DatabaseDelegate,
    private val serverApi: ServerApi
) : CurrencySettingsRepository {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val defaultCurrencies = arrayListOf(145, 292, 298)

    override fun getCurrencyParameters(): Single<List<CurrencyParameters>> =
        databaseDelegate
            .getCurrencies()
            .map { dbCurrencies -> dbCurrencies.map { it.toCurrencyParameters() } }
            .subscribeOn(schedulerProvider.io())

    override fun createDefaultCurrencyParameters(): Single<List<CurrencyParameters>> =
        serverApi
            .getCurrencyExchangeRates(dateFormat.format(Calendar.getInstance().today()))
            .observeOn(schedulerProvider.computation())
            .map { apiCurrencies ->
                var index = defaultCurrencies.size
                apiCurrencies.map {
                    val isDefault = checkForDefault(it.id)
                    val order = if (isDefault.first) isDefault.second else index++
                    val visibility = isDefault.first
                    it.toDBCurrency(visibility, order)
                }
            }
            .flatMap {
                databaseDelegate
                    .addCurrencies(it)
                    .subscribeOn(schedulerProvider.io())
                    .andThen(Single.just(it))
            }
            .map { dbCurrencies -> dbCurrencies.map { it.toCurrencyParameters() } }
            .subscribeOn(schedulerProvider.io())

    private fun checkForDefault(id: Int): Pair<Boolean, Int> =
        if (defaultCurrencies.contains(id)) Pair(true, defaultCurrencies.indexOf(id))
        else Pair(false, -1)

    override fun saveCurrencyParameters(currencySettings: List<CurrencyParameters>): Completable =
        databaseDelegate
            .addCurrencies(currencySettings.map { it.toDBCurrency() })
            .subscribeOn(schedulerProvider.io())

    private fun DBCurrency.toCurrencyParameters() =
        CurrencyParameters(id, charCode, scale, name, visible, order)

    private fun ApiCurrency.toDBCurrency(visible: Boolean, order: Int) =
        DBCurrency(id, charCode, scale, name, visible, order)

    private fun CurrencyParameters.toDBCurrency() =
        DBCurrency(id, charCode, scale, name, visible, order)
}