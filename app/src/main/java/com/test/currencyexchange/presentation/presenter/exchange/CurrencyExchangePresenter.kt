package com.test.currencyexchange.presentation.presenter.exchange

import android.util.Log
import com.test.currencyexchange.domain.interactor.CurrencyExchangeInteractor
import com.test.currencyexchange.presentation.presenter.global.BasePresenter
import java.text.SimpleDateFormat
import java.util.*

class CurrencyExchangePresenter(private val currencyExchangeInteractor: CurrencyExchangeInteractor) :
    BasePresenter<CurrencyExchangeView>() {

    private val dateFormat = SimpleDateFormat("dd-MM-yy", Locale.US)

    fun onResume() {
        getCurrenciesExchange()
    }

    private fun getCurrenciesExchange() {
        currencyExchangeInteractor
            .getCurrencyExchangeRates()
            .doOnSubscribe { viewState.render(CurrencyExchangeViewState.CurrencyLoadingState) }
            .subscribe({
                viewState.render(
                    CurrencyExchangeViewState.CurrencyLoadedState(
                        dateFormat.format(it.firstDate),
                        dateFormat.format(it.secondDate),
                        it.currencyExchange
                    )
                )
            }, {
                viewState.render(CurrencyExchangeViewState.CurrencyErrorState(it.message.toString()))
                Log.e(TAG, "error: $it")
            })
            .connect()
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}