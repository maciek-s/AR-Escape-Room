package com.masiad.arescaperoom.adapter.inventory

import com.masiad.arescaperoom.gamelogic.Inventory

fun interface SelectionChecker {
    fun isSelected(inventory: Inventory): Boolean
}