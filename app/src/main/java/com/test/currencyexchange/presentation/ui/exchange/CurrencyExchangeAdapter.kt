package com.test.currencyexchange.presentation.ui.exchange

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.currencyexchange.R
import com.test.currencyexchange.domain.entity.CurrencyExchangeRateGrouped

class CurrencyExchangeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<CurrencyExchangeRateGrouped>()

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = R.layout.item_currency_exchange

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)

        return CurrencyExchangeViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = items[position]
        (holder as CurrencyExchangeViewHolder).bind(currentItem)
    }

    fun setItems(newItems: List<CurrencyExchangeRateGrouped>) {
        val oldItems = ArrayList(items)

        items.clear()
        items.addAll(newItems)

        DiffUtil
            .calculateDiff(DiffUtilCallback(items, oldItems))
            .dispatchUpdatesTo(this)
    }

    private class DiffUtilCallback(
        private val newList: List<CurrencyExchangeRateGrouped>,
        private val oldList: List<CurrencyExchangeRateGrouped>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            newList[newItemPosition] == oldList[oldItemPosition]

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return compareValuesBy(
                oldItem,
                newItem,
                { it.id },
                { it.firstRate },
                { it.secondRate }
            ) == 0
        }
    }
}