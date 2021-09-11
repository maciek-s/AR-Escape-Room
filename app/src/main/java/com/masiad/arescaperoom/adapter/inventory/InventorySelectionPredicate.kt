package com.masiad.arescaperoom.adapter.inventory

import androidx.recyclerview.selection.SelectionTracker
import com.masiad.arescaperoom.gamelogic.Inventory

class InventorySelectionPredicate : SelectionTracker.SelectionPredicate<Inventory>() {
    override fun canSetStateForKey(key: Inventory, nextState: Boolean): Boolean = true

    override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean = true

    override fun canSelectMultiple(): Boolean = false
}