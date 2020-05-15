package com.test.currencyexchange.presentation.ui.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.currencyexchange.R
import com.test.currencyexchange.domain.entity.CurrencyParameters
import com.test.currencyexchange.presentation.ui.global.ItemTouchHelperAdapter
import java.util.*
import kotlin.collections.ArrayList

class CurrencySettingsAdapter(
    private val currencyPositionListener: (CurrencyParameters, Int) -> Unit,
    private val currencyVisibilityListener: (CurrencyParameters, Boolean) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    ItemTouchHelperAdapter {

    private val items = ArrayList<CurrencyParameters>()

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = R.layout.item_currency_settings

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)

        return CurrencySettingsViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = items[position]
        (holder as CurrencySettingsViewHolder).bind(currentItem, currencyVisibilityListener)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(items, fromPosition, toPosition)

        val firstMovedItem = items[fromPosition]
        val secondMovedItem = items[toPosition]

        currencyPositionListener.invoke(firstMovedItem, fromPosition)
        currencyPositionListener.invoke(secondMovedItem, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    fun setItems(newItems: List<CurrencyParameters>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun updateItem(newItem: CurrencyParameters) {
        items.apply {
            val oldCurrency = find { it.id == newItem.id }
            val pos = items.indexOf(oldCurrency)
            remove(oldCurrency)
            oldCurrency?.let { add(pos, newItem) }
        }
    }


}