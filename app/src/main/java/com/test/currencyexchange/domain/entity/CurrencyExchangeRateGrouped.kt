package com.test.currencyexchange.domain.entity

data class CurrencyExchangeRateGrouped(
    val id: Int,
    val charCode: String,
    val scale: Int,
    val name: String,
    val firstRate: Float,
    val secondRate: Float,
    val visible: Boolean,
    val order: Int
)
