package com.test.currencyexchange.presentation.ui.settings

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.test.currencyexchange.domain.entity.CurrencyParameters
import kotlinx.android.synthetic.main.item_currency_exchange.view.tvCharCode
import kotlinx.android.synthetic.main.item_currency_exchange.view.tvCurrencyNameAndScale
import kotlinx.android.synthetic.main.item_currency_settings.view.*

class CurrencySettingsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: CurrencyParameters, switchListener: (CurrencyParameters, Boolean) -> Unit) {
        view.apply {
            tvCharCode.text = item.charCode
            tvCurrencyNameAndScale.text = "${item.scale} ${item.name}"
            swCurrency.isChecked = item.visible
            swCurrency.setOnClickListener { switchListener.invoke(item, swCurrency.isChecked) }
        }
    }
}