package com.test.currencyexchange.presentation.presenter.settings

import com.test.currencyexchange.domain.entity.CurrencyParameters

sealed class CurrencySettingsViewState {
    object CurrencyLoadingState : CurrencySettingsViewState()
    object CurrencyEmptyState : CurrencySettingsViewState()
    data class CurrencyLoadedState(
        val currencies: List<CurrencyParameters>
    ) : CurrencySettingsViewState()
    data class CurrencyUpdateState(val currency: CurrencyParameters): CurrencySettingsViewState()
    data class CurrencyErrorState(val errorMessage: String) : CurrencySettingsViewState()
    object CurrencyFinishState : CurrencySettingsViewState()
}