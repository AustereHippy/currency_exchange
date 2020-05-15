package com.test.currencyexchange.domain.entity

import java.util.*

data class CurrencyExchangeRates(
    val firstDate: Date,
    val secondDate: Date,
    val currencyExchange: List<CurrencyExchangeRateGrouped>
)
