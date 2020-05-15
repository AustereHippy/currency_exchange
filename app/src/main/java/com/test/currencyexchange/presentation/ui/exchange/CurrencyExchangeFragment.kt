package com.test.currencyexchange.presentation.ui.exchange

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.currencyexchange.R
import com.test.currencyexchange.domain.entity.CurrencyExchangeRateGrouped
import com.test.currencyexchange.presentation.presenter.exchange.CurrencyExchangePresenter
import com.test.currencyexchange.presentation.presenter.exchange.CurrencyExchangeView
import com.test.currencyexchange.presentation.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_currency_exchange.*
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.inject

class CurrencyExchangeFragment :
    BaseFragment(),
    CurrencyExchangeView {

    override val layoutRes: Int = R.layout.fragment_currency_exchange

    private val currencyExchangePresenter: CurrencyExchangePresenter by inject()
    private val presenter by moxyPresenter { currencyExchangePresenter }

    private val currencyExchangeAdapter = CurrencyExchangeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tbCurrencyExchangeToolbar.apply {
            inflateMenu(R.menu.currency_exchange_menu_tb)
            setOnMenuItemClickListener {
                selectMenuAction(it.itemId)
                true
            }
        }

        rvCurrencyExchange.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = currencyExchangeAdapter
        }
    }

    private fun selectMenuAction(itemId: Int) {
        when (itemId) {
            R.id.actionSettings -> onSettingsClicked()
        }
    }

    private fun onSettingsClicked() {
        view?.findNavController()
            ?.navigate(R.id.action_currencyExchangeFragment_to_currencySettingsFragment)
    }

    override fun onResume() {
        super.onResume()

        presenter.onResume()
    }

    override fun setCurrencies(currencies: List<CurrencyExchangeRateGrouped>) {
        currencyExchangeAdapter.setItems(currencies)
    }

    override fun setDates(firstDate: String, secondDate: String) {
        tvFirstColumnDate.text = firstDate
        tvSecondColumnDate.text = secondDate
    }
}