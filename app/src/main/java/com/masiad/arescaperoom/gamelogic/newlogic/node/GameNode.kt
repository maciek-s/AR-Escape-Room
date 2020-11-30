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
     * Is node visible to user in current setup
     */
    var isVisible = true

    /**
     * Node tap listener
     */
    protected var tapListener: OnTapListener? = null

    /**
     * Node animation
     */
    var nodeAnimation: NodeAnimation? = null

    fun animate() {
        nodeAnimation?.startNextAnimation()
    }
}