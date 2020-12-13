package com.masiad.arescaperoom.gamelogic.ar.node

/**
 * Puzzle node that need to be solved to open if locked
 */
open class PuzzleNode : GameNode() {

    /**
     * When node need to be unlock before tap action
     */
    var isLocked = false

    /**
     * User see puzzle node content eg. drawer is open
     * show proper animation and set children visibility
     */
    protected var isOpen = false
        set(value) {
            field = value
            animate()
            setChildrenVisible(value)
        }

    /**
     * Open if not locked on tap
     */
    override fun performTapAction() {
        if (isLocked) {
            tapListener?.onTapLockedNode(this)
        } else {
            isOpen = !isOpen
        }
    }

    private fun setChildrenVisible(isVisible: Boolean) {
        children.filterIsInstance<GameNode>()
            .forEach { node ->
                node.isVisible = isVisible
            }
    }
}