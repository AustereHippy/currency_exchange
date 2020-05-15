package com.test.currencyexchange.presentation.ui.exchange

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.test.currencyexchange.domain.entity.CurrencyExchangeRateGrouped
import kotlinx.android.synthetic.main.item_currency_exchange.view.*

class CurrencyExchangeViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: CurrencyExchangeRateGrouped) {
        view.apply {
            tvCharCode.text = item.charCode
            tvCurrencyNameAndScale.text = "${item.scale} ${item.name}"
            tvFirstRate.text = item.firstRate.toString()
            tvSecondRate.text = item.secondRate.toString()
        }
    }
}