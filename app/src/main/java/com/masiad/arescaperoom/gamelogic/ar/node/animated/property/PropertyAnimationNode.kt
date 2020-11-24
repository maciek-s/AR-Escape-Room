package com.masiad.arescaperoom.gamelogic.ar.node.animated.property

import android.animation.ObjectAnimator
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.math.Vector3Evaluator
import com.masiad.arescaperoom.data.Inventory
import com.masiad.arescaperoom.gamelogic.ar.node.common.ActionType
import com.masiad.arescaperoom.gamelogic.ar.node.common.GameNode

class PropertyAnimationNode(
    actionType: ActionType,
    propertyAnimation: PropertyAnimation
) : GameNode(actionType) {

    private val objectAnimator = ObjectAnimator()

    private val animationVector = propertyAnimation.animationVector

    private var isReverse = false

    init {
        objectAnimator.apply {
            target = this@PropertyAnimationNode
            setPropertyName(propertyAnimation.propertyName)
            setObjectValues(propertyAnimation.animationVector)
            setEvaluator(Vector3Evaluator())
            duration = propertyAnimation.duration
        }
    }

    override fun startNextAnimation() {
        isReverse = if (isReverse) {
            objectAnimator.setObjectValues(Vector3.zero())
            false
        } else {
            objectAnimator.setObjectValues(animationVector)
            true
        }
        objectAnimator.start()
    }

    override fun performAction(inventory: Inventory) {}
}