package com.test.currencyexchange.presentation.presenter.exchange

import com.test.currencyexchange.domain.entity.CurrencyExchangeRateGrouped
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface CurrencyExchangeView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setCurrencies(currencies: List<CurrencyExchangeRateGrouped>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setDates(firstDate: String, secondDate: String)
}