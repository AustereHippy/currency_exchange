package com.test.currencyexchange.domain.interactor

import com.test.currencyexchange.application.system.SchedulerProvider
import com.test.currencyexchange.data.repository.exchange.CurrencyExchangeRepository
import com.test.currencyexchange.data.repository.settings.CurrencySettingsRepository
import com.test.currencyexchange.domain.entity.CurrencyExchangeRate
import com.test.currencyexchange.domain.entity.CurrencyExchangeRateGrouped
import com.test.currencyexchange.domain.entity.CurrencyExchangeRates
import com.test.currencyexchange.domain.entity.CurrencyParameters
import com.test.currencyexchange.extensions.today
import com.test.currencyexchange.extensions.tomorrow
import com.test.currencyexchange.extensions.yesterday
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import java.util.*

class CurrencyExchangeInteractor(
    private val currencyExchangeRepository: CurrencyExchangeRepository,
    private val currencySettingsRepository: CurrencySettingsRepository,
    private val schedulerProvider: SchedulerProvider
) {

    fun getCurrencyExchangeRates(): Single<CurrencyExchangeRates> =
        Single
            .zip(
                getCurrencyParameters(),
                getCurrencyExchangeRatesNew(),
                BiFunction<List<CurrencyParameters>, Pair<List<CurrencyExchangeRate>,
                        List<CurrencyExchangeRate>>, CurrencyExchangeRates>
                { params, lists ->
                    CurrencyExchangeRates(
                        lists.first.first().date,
                        lists.second.first().date,
                        groupCurrencyExchangeRates(lists.first, lists.second, params)
                    )
                }
            )
            .observeOn(schedulerProvider.computation())
            .map { currencies ->
                val modifiedCurrencies = currencies.currencyExchange.toMutableList()
                modifiedCurrencies.apply { removeAll { !it.visible } }
                modifiedCurrencies.sortBy { it.order }
                currencies.copy(currencyExchange = modifiedCurrencies)
            }
            .observeOn(schedulerProvider.ui())

    private fun getCurrencyParameters() =
        currencySettingsRepository
            .getCurrencyParameters()
            .flatMap {
                if (it.isEmpty()) {
                    currencySettingsRepository.createDefaultCurrencyParameters()
                } else {
                    Single.just(it)
                }
            }

    private fun getCurrencyExchangeRatesNew() =
        Single
            .zip(
                currencyExchangeRepository.getCurrencyExchange(Calendar.getInstance().yesterday()),
                currencyExchangeRepository.getCurrencyExchange(Calendar.getInstance().today()),
                currencyExchangeRepository.getCurrencyExchange(Calendar.getInstance().tomorrow()),
                Function3<List<CurrencyExchangeRate>, List<CurrencyExchangeRate>, List<CurrencyExchangeRate>,
                        Pair<List<CurrencyExchangeRate>, List<CurrencyExchangeRate>>> { yesterdayList, todayList, tomorrowList ->
                    if (tomorrowList.isEmpty()) (yesterdayList to todayList)
                    else (todayList to tomorrowList)
                }
            )

    private fun groupCurrencyExchangeRates(
        firstList: List<CurrencyExchangeRate>,
        secondList: List<CurrencyExchangeRate>,
        settingsList: List<CurrencyParameters>
    ): List<CurrencyExchangeRateGrouped> {
        val currencyExchange = arrayListOf<CurrencyExchangeRateGrouped>()
        firstList.forEach { firstCurrency ->
            val secondCurrency = secondList.find { secondCurrency ->
                secondCurrency.id == firstCurrency.id
            } ?: return@forEach

            val currencyParameters = settingsList.find { params ->
                params.id == firstCurrency.id
            } ?: return@forEach

            currencyParameters.apply {
                currencyExchange.add(
                    CurrencyExchangeRateGrouped(
                        id,
                        charCode,
                        scale,
                        name,
                        firstCurrency.rate,
                        secondCurrency.rate,
                        visible,
                        order
                    )
                )
            }
        }

        return currencyExchange
    }
}