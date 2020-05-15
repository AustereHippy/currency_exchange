package com.test.currencyexchange.domain.interactor

import com.test.currencyexchange.application.system.SchedulerProvider
import com.test.currencyexchange.data.repository.settings.CurrencySettingsRepository
import com.test.currencyexchange.domain.entity.CurrencyParameters

class CurrencySettingsInteractor(
    private val currencySettingsRepository: CurrencySettingsRepository,
    private val schedulerProvider: SchedulerProvider
) {

    fun getCurrencyParameters() =
        currencySettingsRepository
            .getCurrencyParameters()
            .observeOn(schedulerProvider.computation())
            .map { params -> params.sortedBy { it.order } }
            .observeOn(schedulerProvider.ui())

    fun saveCurrencyParameters(parameters: List<CurrencyParameters>) =
        currencySettingsRepository
            .saveCurrencyParameters(parameters)
            .observeOn(schedulerProvider.ui())
}