package com.test.currencyexchange.presentation.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.currencyexchange.R
import com.test.currencyexchange.extensions.addVerticalTouchListener
import com.test.currencyexchange.domain.entity.CurrencyParameters
import com.test.currencyexchange.presentation.presenter.settings.CurrencySettingsPresenter
import com.test.currencyexchange.presentation.presenter.settings.CurrencySettingsView
import com.test.currencyexchange.presentation.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_currency_settings.*
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.inject

class CurrencySettingsFragment :
    BaseFragment(),
    CurrencySettingsView {

    override val layoutRes: Int = R.layout.fragment_currency_settings

    private val currencySettingsPresenter: CurrencySettingsPresenter by inject()

    private val presenter by moxyPresenter { currencySettingsPresenter }

    private val currencySettingsAdapter =
        CurrencySettingsAdapter({ currency, position ->
            presenter.onCurrencyMoved(currency, position)
        }, { currency, visibility ->
            presenter.onCurrencyVisibilityChanged(currency, visibility)
        })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tbCurrencySettingsToolbar.apply {
            inflateMenu(R.menu.currency_settings_menu_tb)
            setOnMenuItemClickListener {
                selectMenuAction(it.itemId)
                true
            }
            setNavigationOnClickListener { onNavigationBackClicked() }
        }

        rvCurrencySettings.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = currencySettingsAdapter
        }
        Log.d("TEST_TAG", "saved inst: $savedInstanceState")
        if (savedInstanceState != null) {
            presenter.onRestoreState()
        }

        rvCurrencySettings.addVerticalTouchListener(currencySettingsAdapter)
    }

    private fun selectMenuAction(itemId: Int) {
        when (itemId) {
            R.id.actionSave -> presenter.onSaveClicked()
        }
    }

    private fun onNavigationBackClicked() {
        view?.findNavController()?.popBackStack()
    }

    override fun setCurrencies(currencies: List<CurrencyParameters>) {
        currencies.forEach {
            Log.d("TEST_TAG", "settings: $it")
        }
        currencySettingsAdapter.setItems(currencies)
    }

    override fun updateCurrency(currency: CurrencyParameters) {
        currencySettingsAdapter.updateItem(currency)
    }

    override fun exit() {
        view?.findNavController()?.popBackStack()
    }
}