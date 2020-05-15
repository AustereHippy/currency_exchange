package com.test.currencyexchange.presentation.ui.settings

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.currencyexchange.R
import com.test.currencyexchange.extensions.addVerticalTouchListener
import com.test.currencyexchange.extensions.visible
import com.test.currencyexchange.presentation.presenter.settings.CurrencySettingsPresenter
import com.test.currencyexchange.presentation.presenter.settings.CurrencySettingsView
import com.test.currencyexchange.presentation.presenter.settings.CurrencySettingsViewState
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
            setOnMenuItemClickListener {
                selectMenuAction(it.itemId)
                true
            }
            setNavigationOnClickListener { presenter.onNavigationClicked() }
        }

        rvCurrencySettings.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = currencySettingsAdapter
        }

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

    override fun render(state: CurrencySettingsViewState) {
        when (state) {
            is CurrencySettingsViewState.CurrencyLoadingState -> {
                pbSettings.visible(true)
            }
            is CurrencySettingsViewState.CurrencyEmptyState -> {
                pbSettings.visible(false)
            }
            is CurrencySettingsViewState.CurrencyLoadedState -> {
                pbSettings.visible(false)
                currencySettingsAdapter.setItems(state.currencies)
            }
            is CurrencySettingsViewState.CurrencyUpdateState -> {
                pbSettings.visible(false)
                currencySettingsAdapter.updateItem(state.currency)
            }
            is CurrencySettingsViewState.CurrencyErrorState -> {
                pbSettings.visible(false)
            }
            is CurrencySettingsViewState.CurrencyFinishState -> {
                view?.findNavController()?.popBackStack()
            }
        }
    }
}