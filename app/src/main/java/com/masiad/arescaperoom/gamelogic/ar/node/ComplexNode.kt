package com.masiad.arescaperoom.gamelogic.ar.node

/**
 * Node that contains multiple logic connected nodes
 */
open class ComplexNode : GameNode() {

    protected fun setChildrenVisible(isVisible: Boolean) {
        children.filterIsInstance<GameNode>()
            .forEach { node ->
                node.isVisible = isVisible
            }
    }
}