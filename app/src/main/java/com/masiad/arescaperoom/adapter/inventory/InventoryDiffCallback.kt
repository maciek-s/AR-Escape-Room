package com.masiad.arescaperoom.adapter.inventory

import androidx.recyclerview.widget.DiffUtil
import com.masiad.arescaperoom.gamelogic.Inventory

class InventoryDiffCallback : DiffUtil.ItemCallback<Inventory>() {

    override fun areItemsTheSame(oldItem: Inventory, newItem: Inventory): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: Inventory, newItem: Inventory): Boolean {
        return oldItem == newItem
    }
}