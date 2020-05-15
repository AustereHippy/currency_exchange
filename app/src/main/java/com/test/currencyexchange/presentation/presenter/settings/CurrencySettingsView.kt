package com.test.currencyexchange.presentation.presenter.settings

import com.test.currencyexchange.domain.entity.CurrencyParameters
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface CurrencySettingsView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun setCurrencies(currencies: List<CurrencyParameters>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun updateCurrency(currency: CurrencyParameters)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun exit()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setProgress(loading: Boolean)
}