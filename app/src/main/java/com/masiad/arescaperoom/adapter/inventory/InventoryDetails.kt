package com.masiad.arescaperoom.adapter.inventory

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import com.masiad.arescaperoom.gamelogic.Inventory

data class InventoryDetails(
    private val position: Int,
    private val inventory: Inventory?
) : ItemDetailsLookup.ItemDetails<Inventory>() {

    override fun getPosition(): Int = position

    override fun getSelectionKey(): Inventory? = inventory

    override fun inSelectionHotspot(e: MotionEvent): Boolean = true
}