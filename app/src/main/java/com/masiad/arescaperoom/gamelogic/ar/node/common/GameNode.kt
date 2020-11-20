package com.masiad.arescaperoom.gamelogic.ar.node.common

import com.google.ar.sceneform.Node
import com.masiad.arescaperoom.data.Inventory

abstract class GameNode : Node() {

    abstract fun startNextAnimation()

    abstract fun performAction(inventory: Inventory)
}