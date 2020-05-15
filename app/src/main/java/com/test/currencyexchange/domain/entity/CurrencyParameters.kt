package com.test.currencyexchange.domain.entity

data class CurrencyParameters(
    val id: Int,
    val charCode: String,
    val scale: Int,
    val name: String,
    val visible: Boolean,
    val order: Int
)