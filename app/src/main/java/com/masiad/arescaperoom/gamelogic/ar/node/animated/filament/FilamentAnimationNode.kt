package com.masiad.arescaperoom.gamelogic.ar.node.animated.filament

import android.animation.ValueAnimator
import com.google.android.filament.gltfio.Animator
import com.masiad.arescaperoom.data.Inventory
import com.masiad.arescaperoom.gamelogic.ar.node.common.ActionType
import com.masiad.arescaperoom.gamelogic.ar.node.common.GameNode
import kotlin.math.roundToLong

class FilamentAnimationNode(actionType: ActionType) : GameNode(actionType) {

    private val animator: Animator?
        get() = renderableInstance?.filamentAsset?.animator

    private var lastValueSeconds: Float = 0f

    override fun startNextAnimation() {
        if (lastValueSeconds > 0) {
            startAnimationBackward()
        } else {
            startAnimation()
        }
    }

    override fun performAction(inventory: Inventory) {}

    // PRIVATE
    // todo combain to one function
    // todo multiple index animations?
    private fun startAnimation(index: Int = 0) {
        animator?.getAnimationDuration(index)?.let { durationSeconds ->
            ValueAnimator.ofFloat(0f, durationSeconds.withoutLastFrame()).apply {
                this.duration = durationSeconds.secondToMs()
                addUpdateListener {
                    lastValueSeconds = it.animatedValue as Float
                    animator?.applyAnimation(index, lastValueSeconds)
                    animator?.updateBoneMatrices()
                }
            }.start()
        }
    }

    private fun startAnimationBackward(index: Int = 0) {
        animator?.getAnimationDuration(index)?.let { durationSeconds ->
            ValueAnimator.ofFloat(lastValueSeconds, 0f).apply {
                this.duration = durationSeconds.secondToMs()
                addUpdateListener {
                    lastValueSeconds = it.animatedValue as Float
                    animator?.applyAnimation(index, lastValueSeconds)
                    animator?.updateBoneMatrices()
                }
            }.start()
        }
    }

    private fun Float.secondToMs(): Long {
        return (this * 1000).roundToLong()
    }

    private fun Float.withoutLastFrame(): Float {
        val frame = 1f / 30
        return this - frame
    }
}