package com.masiad.arescaperoom.adapter.inventory

import androidx.recyclerview.selection.ItemKeyProvider
import com.masiad.arescaperoom.gamelogic.Inventory

class InventoryKeyProvider(
    private val inventoryAdapter: InventoryAdapter
) : ItemKeyProvider<Inventory>(SCOPE_CACHED) {

    override fun getKey(position: Int): Inventory? = inventoryAdapter.currentList[position]

    override fun getPosition(key: Inventory): Int = inventoryAdapter.currentList.indexOf(key)
}