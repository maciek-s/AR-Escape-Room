package com.masiad.arescaperoom.gamelogic.newlogic.node

import android.view.MotionEvent
import com.google.ar.sceneform.HitTestResult
import com.google.ar.sceneform.Node
import com.masiad.arescaperoom.gamelogic.newlogic.animation.NodeAnimation

/**
 * Base game node without any action
 */
open class GameNode : Node() {

    interface OnTapListener {
        fun onNodeTap(node: GameNode, hitTestResult: HitTestResult?, motionEvent: MotionEvent?)
    }

    /**
     * When node is visible to user
     */
    var isVisible = true

    /**
     * Node tap listener
     */
    private var tapListener: OnTapListener? = null
    fun setTapListener(listener: OnTapListener?) {
        tapListener = listener
    }

    /**
     * Node animation
     */
    var nodeAnimation: NodeAnimation? = null

    fun animate() {
        nodeAnimation?.startNextAnimation()
    }

    /**
     * Start animation when node is clickable and visible
     */
    fun performTap() {
        if (isVisible) {
            animate()
        }
    }
}