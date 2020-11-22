package com.masiad.arescaperoom.gamelogic.ar.node.common

import android.view.MotionEvent
import com.google.ar.sceneform.HitTestResult
import com.google.ar.sceneform.Node
import com.masiad.arescaperoom.data.Inventory

abstract class GameNode : Node() {

    interface OnTapListener {
        fun onNodeTap(node: GameNode, hitTestResult: HitTestResult?, motionEvent: MotionEvent?)
    }

    /**
     * Node is visible to user when eg. box is open and user see models inside
     */
    var isVisible = true

    /**
     * Node is locked when eg. needs an inventory to unlock
     */
    var isLocked = false

    /**
     * Node is in open state and user can see contents (children are visible now)
     */
    var isOpen = false

    private var tapListener: OnTapListener? = null

    fun setOnGameNodeTapListener(listener: GameNode.OnTapListener?) {
        this.tapListener = listener
    }

    init {
        setOnTapListener { hitTestResult, motionEvent ->
            tapListener?.onNodeTap(this, hitTestResult, motionEvent)
        }
    }

    final override fun setOnTapListener(onTapListener: Node.OnTapListener?) {
        super.setOnTapListener(onTapListener)
    }

    abstract fun startNextAnimation()

    abstract fun performAction(inventory: Inventory)
}