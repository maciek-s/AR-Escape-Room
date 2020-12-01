package com.masiad.arescaperoom.gamelogic.ar.animation.property

import android.animation.ObjectAnimator
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.math.Vector3Evaluator
import com.masiad.arescaperoom.gamelogic.ar.animation.NodeAnimation
import com.masiad.arescaperoom.gamelogic.ar.node.GameNode

class PropertyAnimation(
    private val propertyAnimationModel: PropertyAnimationModel,
    private val node: GameNode
) : NodeAnimation {

    override var isReverse: Boolean = false

    // todo extendts to rotation not only vector 3 evaluator
    private val objectAnimator: ObjectAnimator by lazy {
        ObjectAnimator().apply {
            target = node
            setPropertyName(propertyAnimationModel.propertyName)
            setObjectValues(propertyAnimationModel.animationVector)
            setEvaluator(Vector3Evaluator())
            duration = propertyAnimationModel.duration
        }
    }

    override fun startNextAnimation() {
        animate()
    }

    private fun animate() {
        val objectValues = if (isReverse) {
            isReverse = false
            Vector3.zero()
        } else {
            isReverse = true
            propertyAnimationModel.animationVector
        }
        objectAnimator.apply {
            setObjectValues(objectValues)
            start()
        }
    }
}