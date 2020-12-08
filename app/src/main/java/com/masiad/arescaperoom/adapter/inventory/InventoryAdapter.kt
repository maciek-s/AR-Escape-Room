package com.masiad.arescaperoom.adapter.inventory

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.masiad.arescaperoom.databinding.InventoryItemBinding
import com.masiad.arescaperoom.gamelogic.Inventory
import com.masiad.arescaperoom.util.extenstion.TAG
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class InventoryAdapter @Inject constructor(
) : ListAdapter<Inventory, InventoryViewHolder>(InventoryDiffCallback()) {

    var selectionChecker: SelectionChecker? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InventoryViewHolder = InventoryViewHolder(
        InventoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: InventoryViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(
            selectionChecker?.isSelected(item) ?: false,
            position,
            item
        )
    }
}