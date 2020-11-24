package com.masiad.arescaperoom.gamelogic.ar.node.stationary

import com.masiad.arescaperoom.data.Inventory
import com.masiad.arescaperoom.gamelogic.ar.node.common.ActionType
import com.masiad.arescaperoom.gamelogic.ar.node.common.GameNode

class StationaryNode(actionType: ActionType) : GameNode(actionType) {

    override fun startNextAnimation() {}

    override fun performAction(inventory: Inventory) {}
}