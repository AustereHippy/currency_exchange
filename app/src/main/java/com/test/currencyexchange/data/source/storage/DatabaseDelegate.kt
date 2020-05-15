package com.test.currencyexchange.data.source.storage

import com.test.currencyexchange.data.source.entity.DBCurrency

class DatabaseDelegate(private val database: Database) {

    private val currencyDao = database.currencyDao

    fun getCurrencies() = currencyDao.get()

    fun updateCurrencies(vararg currencies: DBCurrency) = currencyDao.update(currencies)

    fun addCurrencies(currencies: List<DBCurrency>) = currencyDao.insert(currencies)
}