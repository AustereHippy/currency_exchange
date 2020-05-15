package com.test.currencyexchange.extensions

import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.test.currencyexchange.presentation.ui.global.ItemTouchHelperAdapter
import java.util.*

fun RecyclerView.addVerticalTouchListener(adapter: ItemTouchHelperAdapter) {
    val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            dragged: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            adapter.onItemMove(dragged.adapterPosition, target.adapterPosition)
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }
    })

    itemTouchHelper.attachToRecyclerView(this)
}

fun Calendar.today(): Date = time

fun Calendar.yesterday(): Date = this.apply { add(Calendar.DATE, -1) }.time

fun Calendar.tomorrow(): Date = this.apply { add(Calendar.DATE, 1) }.time

fun View.visible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}