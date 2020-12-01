package com.masiad.arescaperoom.gamelogic.ar.animation.filament

import android.animation.ValueAnimator
import com.google.android.filament.gltfio.Animator
import com.google.ar.sceneform.rendering.RenderableInstance
import com.masiad.arescaperoom.gamelogic.ar.animation.NodeAnimation
import com.masiad.arescaperoom.util.extenstion.toMilliseconds

class FilamentAnimation(
    renderableInstance: RenderableInstance?
) : NodeAnimation {

    // TODO temporary only 0 index and recerse on second click

    companion object {
        private const val ONE_FRAME = 1f / 24
    }

    override var isReverse = false

    private val animator: Animator? = renderableInstance?.filamentAsset?.animator

    private val durationSeconds: Float? = animator?.getAnimationDuration(0)

    private val valueAnimator: ValueAnimator? by lazy {
        durationSeconds?.let {
            ValueAnimator().apply {
                duration = durationSeconds.toMilliseconds()
                addUpdateListener {
                    val seconds = it.animatedValue as Float
                    animator?.applyAnimation(0, seconds)
                    animator?.updateBoneMatrices()
                }
            }
        }
    }

    override fun startNextAnimation() {
        animate()
    }

    private fun animate() {
        valueAnimator?.let {
            requireNotNull(durationSeconds)
            val (start, stop) = if (isReverse) {
                isReverse = false
                Pair(durationSeconds - ONE_FRAME, 0f)
            } else {
                isReverse = true
                Pair(0f, durationSeconds - ONE_FRAME)
            }
            it.apply {
                setFloatValues(start, stop)
                start()
            }
        }
    }
}