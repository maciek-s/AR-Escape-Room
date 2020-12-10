package com.masiad.arescaperoom.gamelogic.ar.node

import com.google.ar.sceneform.collision.Box
import com.google.ar.sceneform.math.Vector3
import com.masiad.arescaperoom.util.extenstion.boxShape

/**
 * Node that contains multiple logic connected nodes
 */
open class ComplexNode : GameNode() {

    protected fun setChildrenVisible(isVisible: Boolean) {
        children.filterIsInstance<InventoryNode>()
            .forEach { node ->
                with(node) {
                    this.isVisible = isVisible
                    visibleAdditionalSize?.let {
                        setCollisionShapeSize(it)
                    }
                }
            }
    }
}

private fun GameNode.setCollisionShapeSize(additionalSize: Vector3) {
    collisionShape = boxShape?.let { box ->
        if (isVisible) {
            box.addVectorSize(additionalSize)
        } else {
            box.subtractVectorSize(additionalSize)
        }
    }
}

private fun Box.addVectorSize(additionalSize: Vector3): Box? {
    size = Vector3.add(size, additionalSize)
    center = Vector3.add(center, additionalSize.scaled(0.5f))
    return this
}

private fun Box.subtractVectorSize(additionalSize: Vector3): Box? {
    size = Vector3.subtract(size, additionalSize)
    center = Vector3.subtract(center, additionalSize.scaled(0.5f))
    return this
}