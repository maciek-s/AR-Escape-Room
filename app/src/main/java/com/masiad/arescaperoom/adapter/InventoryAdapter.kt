package com.masiad.arescaperoom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.masiad.arescaperoom.databinding.InventoryItemBinding
import com.masiad.arescaperoom.gamelogic.Inventory
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class InventoryAdapter @Inject constructor() :
    ListAdapter<Inventory, InventoryAdapter.ViewHolder>(InventoryDiffCallback()) {

    interface OnInventoryClickListener {
        fun onInventoryClick(inventory: Inventory)
    }

    private var inventoryClickListener: OnInventoryClickListener? = null

    fun setOnInventoryClickListener(listener: (Inventory) -> Unit) {
        this.inventoryClickListener = object : OnInventoryClickListener {
            override fun onInventoryClick(inventory: Inventory) {
                listener(inventory)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            InventoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            inventoryClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val inventory = getItem(position)
        holder.bind(inventory)
    }

    class ViewHolder(
        private val binding: InventoryItemBinding,
        private val inventoryClickListener: OnInventoryClickListener?
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                binding.inventory?.let { inventory ->
                    inventoryClickListener?.onInventoryClick(inventory)
                }
            }
        }

        fun bind(item: Inventory) {
            binding.apply {
                inventory = item
                executePendingBindings()
            }
        }
    }
}

private class InventoryDiffCallback : DiffUtil.ItemCallback<Inventory>() {

    override fun areItemsTheSame(oldItem: Inventory, newItem: Inventory): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: Inventory, newItem: Inventory): Boolean {
        return oldItem == newItem
    }
}