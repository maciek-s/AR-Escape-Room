package com.masiad.arescaperoom.gamelogic.ar.node

import com.google.ar.sceneform.Node
import com.masiad.arescaperoom.gamelogic.ar.animation.NodeAnimation

/**
 * Base game node without any action
 */
open class GameNode : Node() {

    interface OnTapListener {
        fun onInventoryPickUp(inventoryNode: InventoryNode)
    }

    /**
     * When node is visible to user
     */
    var isVisible = true

    /**
     * Node tap listener
     */
    protected var tapListener: OnTapListener? = null
    fun setOnTapListener(listener: OnTapListener?) {
        tapListener = listener
    }

    /**
     * Node animation
     */
    var nodeAnimation: NodeAnimation? = null

    fun animate() {
        nodeAnimation?.startNextAnimation()
    }

    /**
     * Start animation when node is clickable and visible
     */
    fun onTap() {
        if (isVisible) {
            performTapAction()
        }
    }

    open fun performTapAction() {
        animate()
    }
}