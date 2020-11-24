package com.masiad.arescaperoom.gamelogic.ar.node.common

import android.view.MotionEvent
import com.google.ar.sceneform.HitTestResult
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.collision.Box
import com.google.ar.sceneform.collision.CollisionShape
import com.google.ar.sceneform.math.Vector3
import com.masiad.arescaperoom.data.Inventory

abstract class GameNode(
    protected val actionType: ActionType
) : Node() {

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
        set(value) {
            field = value
            handleNodeOpen()
        }

    // todo tmp update height
    var visibleBoundingBox: CollisionShape? = null

    private var tapListener: OnTapListener? = null

    abstract fun startNextAnimation()

    abstract fun performAction(inventory: Inventory)

    init {
        // Set tap listener only if node has any action
        if (actionType != ActionType.NONE) {
            setOnTapListener { hitTestResult, motionEvent ->
                // TODO, handle tap based on node state and type
                // visible and unlock -> perform node action
                // todo need to rename isOpen, make children visible, update bounding box if needed?
                // visible and lock -> perform inventory action // feature code action?
                // hide picked up inventory
                tapListener?.onNodeTap(this, hitTestResult, motionEvent)
            }
        }
    }

    private fun handleNodeOpen() {
        startNextAnimation()
        gameNodeChildren().forEach {
            it.isVisible = isOpen
            it.collisionShape = if (isOpen) {
                val default = it.renderable?.collisionShape as Box
                default.apply {
                    size = Vector3.add(size, Vector3(0f, 0.2f, 0f))
                }
                null
            } else {
                null
            }
        }
    }

    fun setOnGameNodeTapListener(listener: GameNode.OnTapListener?) {
        this.tapListener = listener
    }

    final override fun setOnTapListener(onTapListener: Node.OnTapListener?) {
        super.setOnTapListener(onTapListener)
    }

    protected fun gameNodeChildren(): List<GameNode> =
        children.filterIsInstance(GameNode::class.java)
}