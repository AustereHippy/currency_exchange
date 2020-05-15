package com.test.currencyexchange.data.repository.exchange

import com.test.currencyexchange.domain.entity.CurrencyExchangeRate
import io.reactivex.Single
import java.util.*

interface CurrencyExchangeRepository {

    fun getCurrencyExchange(date: Date): Single<List<CurrencyExchangeRate>>
}