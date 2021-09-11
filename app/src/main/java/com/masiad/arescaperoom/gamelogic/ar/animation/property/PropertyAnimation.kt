package com.masiad.arescaperoom.gamelogic.ar.animation.property

import android.animation.ObjectAnimator
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.QuaternionEvaluator
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.math.Vector3Evaluator
import com.masiad.arescaperoom.gamelogic.ar.animation.NodeAnimation
import com.masiad.arescaperoom.gamelogic.ar.node.GameNode

class PropertyAnimation(
    private val propertyAnimationModel: PropertyAnimationModel,
    private val node: GameNode
) : NodeAnimation {

    override var isReverse: Boolean = false

    private val objectAnimator: ObjectAnimator by lazy {
        createObjectAnimator(propertyAnimationModel)
    }

    private val initPosition: Vector3 by lazy {
        node.localPosition
    }
    private val animationVector: Vector3 by lazy {
        (propertyAnimationModel as? PositionAnimationModel)?.let {
            Vector3.add(initPosition, it.animationVector)
        } ?: initPosition
    }

    private val initRotation: Quaternion by lazy {
        node.localRotation
    }
    private val animationQuaternion: Quaternion by lazy {
        (propertyAnimationModel as? RotationAnimationModel)?.let {
            Quaternion.multiply(initRotation, it.animationQuaternion)
        } ?: initRotation
    }

    override fun startNextAnimation() {
        animate()
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun animate() {
        val objectValues = when (propertyAnimationModel) {
            is PositionAnimationModel -> {
                if (isReverse) initPosition else animationVector
            }
            is RotationAnimationModel -> {
                if (isReverse) initRotation else animationQuaternion
            }
        }
        isReverse = !isReverse
        objectAnimator.apply {
            setObjectValues(objectValues)
            start()
        }
    }

    private fun createObjectAnimator(model: PropertyAnimationModel): ObjectAnimator {
        return ObjectAnimator().apply {
            target = node
            setPropertyName(model.propertyName)
            duration = model.duration
            when (model) {
                is PositionAnimationModel -> {
                    setObjectValues(animationVector)
                    setEvaluator(Vector3Evaluator())
                }
                is RotationAnimationModel -> {
                    setObjectValues(animationQuaternion)
                    setEvaluator(QuaternionEvaluator())
                }
            }
        }
    }
}