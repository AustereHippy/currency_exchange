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
            .subscribe({
                viewState.setCurrencies(it.currencyExchange)
                viewState.setDates(
                    dateFormat.format(it.firstDate),
                    dateFormat.format(it.secondDate)
                )
            }, {
                Log.e("TEST_TAG", "error: $it")
            })
            .connect()
    }
}