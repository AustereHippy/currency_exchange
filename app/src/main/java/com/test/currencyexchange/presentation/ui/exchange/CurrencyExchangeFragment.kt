package com.test.currencyexchange.presentation.ui.exchange

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.currencyexchange.R
import com.test.currencyexchange.extensions.visible
import com.test.currencyexchange.presentation.presenter.exchange.CurrencyExchangePresenter
import com.test.currencyexchange.presentation.presenter.exchange.CurrencyExchangeView
import com.test.currencyexchange.presentation.presenter.exchange.CurrencyExchangeViewState
import com.test.currencyexchange.presentation.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_currency_exchange.*
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.getKoin

class CurrencyExchangeFragment :
    BaseFragment(),
    CurrencyExchangeView {

    override val layoutRes: Int = R.layout.fragment_currency_exchange

    private val presenter by moxyPresenter { getKoin().get<CurrencyExchangePresenter>() }

    private val currencyExchangeAdapter = CurrencyExchangeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tbCurrencyExchangeToolbar.apply {
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

    override fun render(state: CurrencyExchangeViewState) {
        when (state) {
            is CurrencyExchangeViewState.CurrencyLoadingState -> {
                pbExchange.visible(true)
            }
            is CurrencyExchangeViewState.CurrencyEmptyState -> {
                pbExchange.visible(false)
            }
            is CurrencyExchangeViewState.CurrencyErrorState -> {
                pbExchange.visible(false)
                showMessage(state.errorMessage)
                tbCurrencyExchangeToolbar.menu.findItem(R.id.actionSettings).isVisible = false
            }
            is CurrencyExchangeViewState.CurrencyLoadedState -> {
                pbExchange.visible(false)
                tvFirstColumnDate.text = state.firstDate
                tvSecondColumnDate.text = state.secondDate
                currencyExchangeAdapter.setItems(state.currencyExchange)
            }
        }
    }
}