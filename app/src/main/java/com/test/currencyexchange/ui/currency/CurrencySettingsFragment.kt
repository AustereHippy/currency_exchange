package com.test.currencyexchange.ui.currency

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.test.currencyexchange.R
import com.test.currencyexchange.ui.global.BaseFragment
import kotlinx.android.synthetic.main.fragment_currency_settings.*

class CurrencySettingsFragment : BaseFragment() {

    override val layoutRes: Int = R.layout.fragment_currency_settings

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
    }

    private fun selectMenuAction(itemId: Int) {
        when (itemId) {
            R.id.actionSave -> onSaveClicked()
        }
    }

    private fun onSaveClicked() {
        showMessage("on Save clicked")
    }

    private fun onNavigationBackClicked() {
        view?.findNavController()?.popBackStack()
    }
}