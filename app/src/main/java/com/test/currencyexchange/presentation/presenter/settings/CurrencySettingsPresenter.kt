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
            .doOnSubscribe { viewState.render(CurrencySettingsViewState.CurrencyLoadingState) }
            .subscribe({
                currencySettings.addAll(it)
                viewState.render(CurrencySettingsViewState.CurrencyLoadedState(it))
            }, {
                viewState.render(CurrencySettingsViewState.CurrencyErrorState(it.message.toString()))
                Log.e(TAG, "error: $it")
            })
            .connect()
    }

    fun onSaveClicked() {
        currencySettingsInteractor
            .saveCurrencyParameters(currencySettings)
            .doOnSubscribe { viewState.render(CurrencySettingsViewState.CurrencyLoadingState) }
            .subscribe({
                viewState.render(CurrencySettingsViewState.CurrencyFinishState)
            }, {
                viewState.render(CurrencySettingsViewState.CurrencyErrorState(it.message.toString()))
                Log.e(TAG, "error: $it")
            })
            .connect()
    }

    fun onNavigationClicked() {
        viewState.render(CurrencySettingsViewState.CurrencyFinishState)
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
                viewState.render(CurrencySettingsViewState.CurrencyUpdateState(it))
            }
        }
    }

    fun onRestoreState() {
        if (currencySettings.isNotEmpty()) {
            currencySettings.sortBy { it.order }
            viewState.render(CurrencySettingsViewState.CurrencyLoadedState(currencySettings))
        }
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}