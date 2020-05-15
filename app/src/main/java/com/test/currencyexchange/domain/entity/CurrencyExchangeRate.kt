package com.test.currencyexchange.domain.entity

import java.util.*

data class CurrencyExchangeRate(
    val id: Int,
    val rate: Float,
    val date: Date
)