package com.test.currencyexchange.presentation.presenter.settings

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface CurrencySettingsView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun render(state: CurrencySettingsViewState)
}