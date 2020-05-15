package com.test.currencyexchange.presentation.presenter.exchange

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface CurrencyExchangeView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun render(state: CurrencyExchangeViewState)
}