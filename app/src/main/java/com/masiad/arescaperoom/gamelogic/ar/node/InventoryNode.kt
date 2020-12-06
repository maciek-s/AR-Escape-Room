package com.masiad.arescaperoom.gamelogic.ar.node

import com.google.ar.sceneform.math.Vector3
import com.masiad.arescaperoom.gamelogic.Inventory

/**
 * Node that can be picked up and use on puzzle node
 */
class InventoryNode : GameNode() {

    lateinit var inventory: Inventory

    var visibleAdditionalSize: Vector3? = null

    override fun performTapAction() {
        tapListener?.onInventoryPickUp(this)
        setParent(null)
    }
}