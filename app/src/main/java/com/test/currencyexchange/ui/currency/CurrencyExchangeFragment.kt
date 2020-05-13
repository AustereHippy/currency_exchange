package com.test.currencyexchange.ui.currency

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.test.currencyexchange.R
import com.test.currencyexchange.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_currency_exchange.*

class CurrencyExchangeFragment : BaseFragment() {

    override val layoutRes: Int = R.layout.fragment_currency_exchange

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tbCurrencyExchangeToolbar.apply {
            inflateMenu(R.menu.currency_exchange_menu_tb)
            setOnMenuItemClickListener {
                selectMenuAction(it.itemId)
                true
            }
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
}