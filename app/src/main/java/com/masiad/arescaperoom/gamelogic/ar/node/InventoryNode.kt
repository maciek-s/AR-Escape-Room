package com.masiad.arescaperoom.gamelogic.ar.node

import com.masiad.arescaperoom.gamelogic.Inventory

/**
 * Node that can be picked up and use on puzzle node
 */
class InventoryNode : GameNode() {

    lateinit var inventory: Inventory

    override fun performTapAction() {
        tapListener?.onInventoryPickUp(this)
        setParent(null)
    }
}