package com.masiad.arescaperoom.adapter.inventory

import androidx.recyclerview.widget.RecyclerView
import com.masiad.arescaperoom.databinding.InventoryItemBinding
import com.masiad.arescaperoom.gamelogic.Inventory

class InventoryViewHolder(
    private val binding: InventoryItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        isSelected: Boolean,
        position: Int,
        inventory: Inventory
    ) {
        binding.apply {
            this.isSelected = isSelected
            this.position = position
            this.inventory = inventory
            executePendingBindings()
        }
    }

    fun getItemDetails(): InventoryDetails = InventoryDetails(
        binding.position,
        binding.inventory
    )
}