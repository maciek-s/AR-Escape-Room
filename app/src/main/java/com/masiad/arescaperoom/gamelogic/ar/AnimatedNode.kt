package com.masiad.arescaperoom.gamelogic.ar

import android.animation.ValueAnimator
import com.google.android.filament.gltfio.Animator
import com.google.ar.sceneform.Node
import kotlin.math.roundToLong

class AnimatedNode : Node() {

    private val animator: Animator?
        get() = renderableInstance?.filamentAsset?.animator

    private var lastValueSeconds: Float = 0f

    fun startAnimation(index: Int = 0) {
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

    fun startAnimationBackward(index: Int = 0) {
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