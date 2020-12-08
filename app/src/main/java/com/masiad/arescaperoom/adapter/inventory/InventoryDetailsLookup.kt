package com.masiad.arescaperoom.adapter.inventory

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.masiad.arescaperoom.gamelogic.Inventory

class InventoryDetailsLookup(
    private val recyclerView: RecyclerView
) : ItemDetailsLookup<Inventory>() {

    override fun getItemDetails(e: MotionEvent): ItemDetails<Inventory>? =
        recyclerView.findChildViewUnder(e.x, e.y)?.let {
            (recyclerView.getChildViewHolder(it) as? InventoryViewHolder)?.getItemDetails()
        }
}