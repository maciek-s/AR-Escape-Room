package com.masiad.arescaperoom.gamelogic.ar

import com.google.ar.sceneform.SkeletonNode
import com.google.ar.sceneform.animation.ModelAnimator
import com.google.ar.sceneform.rendering.ModelRenderable

class GameNode : SkeletonNode() {

    private var animationIdx = -1
    private var animationCount = 0

    private var modelAnimator: ModelAnimator? = null

    private var modelRenderable: ModelRenderable? = null
        set(value) {
            value?.let {
                field = it
                animationCount = it.animationDataCount
            }
        }

    fun startAnimation() {
        if (animationCount > 0) {
            animationIdx++
            if (animationIdx >= animationCount) {
                animationIdx = 0
            }
        }
        modelRenderable?.getAnimationData(animationIdx)?.let { animationData ->
            modelAnimator = ModelAnimator(animationData, modelRenderable).apply {
                start()
            }
        }
    }
}