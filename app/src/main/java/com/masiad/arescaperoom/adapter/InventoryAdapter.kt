package com.masiad.arescaperoom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.masiad.arescaperoom.data.Inventory
import com.masiad.arescaperoom.databinding.InventoryItemBinding
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class InventoryAdapter @Inject constructor() :
    ListAdapter<Inventory, InventoryAdapter.ViewHolder>(InventoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            InventoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val inventory = getItem(position)
        holder.bind(inventory)
    }

    class ViewHolder(
        private val binding: InventoryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                binding.inventory?.let { inventory ->
                    // TODO open inventory modal? / make selected?
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