package com.test.currencyexchange.presentation.presenter.exchange

import com.test.currencyexchange.domain.entity.CurrencyExchangeRateGrouped

sealed class CurrencyExchangeViewState {
    object CurrencyLoadingState : CurrencyExchangeViewState()
    object CurrencyEmptyState : CurrencyExchangeViewState()
    data class CurrencyLoadedState(
        val firstDate: String,
        val secondDate: String,
        val currencyExchange: List<CurrencyExchangeRateGrouped>
    ) : CurrencyExchangeViewState()

    data class CurrencyErrorState(val errorMessage: String) : CurrencyExchangeViewState()
}