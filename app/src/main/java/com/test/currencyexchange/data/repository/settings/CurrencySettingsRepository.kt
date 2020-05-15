package com.test.currencyexchange.data.repository.settings

import com.test.currencyexchange.domain.entity.CurrencyParameters
import io.reactivex.Completable
import io.reactivex.Single

interface CurrencySettingsRepository {

    fun getCurrencyParameters(): Single<List<CurrencyParameters>>

    fun createDefaultCurrencyParameters(): Single<List<CurrencyParameters>>

    fun saveCurrencyParameters(currencySettings: List<CurrencyParameters>): Completable
}