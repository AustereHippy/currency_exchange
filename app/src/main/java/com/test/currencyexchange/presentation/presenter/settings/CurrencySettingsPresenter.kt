package com.test.currencyexchange.presentation.presenter.settings

import android.util.Log
import com.test.currencyexchange.domain.entity.CurrencyParameters
import com.test.currencyexchange.domain.interactor.CurrencySettingsInteractor
import com.test.currencyexchange.presentation.presenter.global.BasePresenter

class CurrencySettingsPresenter(private val currencySettingsInteractor: CurrencySettingsInteractor) :
    BasePresenter<CurrencySettingsView>() {

    private val currencySettings = ArrayList<CurrencyParameters>()

    override fun onFirstViewAttach() {
        currencySettingsInteractor
            .getCurrencyParameters()
            .subscribe({
                currencySettings.addAll(it)
                viewState.setCurrencies(it)
            }, {
                Log.e("TEST)TAG", "ERROR: $it")
            })
            .connect()
    }

    fun onSaveClicked() {
        currencySettingsInteractor
            .saveCurrencyParameters(currencySettings)
            .subscribe({
                viewState.exit()
            }, {
                Log.e("TEST)TAG", "ERROR: $it")
            })
            .connect()
    }

    fun onCurrencyMoved(currency: CurrencyParameters, newPosition: Int) {
        currencySettings.apply {
            val oldCurrency = find { it.id == currency.id }
            remove(oldCurrency)
            oldCurrency?.let { add(it.copy(order = newPosition)) }
        }
    }

    fun onCurrencyVisibilityChanged(currency: CurrencyParameters, visible: Boolean) {
        currencySettings.apply {
            val oldCurrency = find { it.id == currency.id }
            remove(oldCurrency)
            val newItem = oldCurrency?.copy(visible = visible)
            newItem?.let {
                add(it)
                viewState.updateCurrency(it)
            }
        }
    }

    fun onRestoreState() {
        if (currencySettings.isNotEmpty()) {
            val test = currencySettings
            test.sortBy { it.order }
            viewState.setCurrencies(test)
        }
    }
}