package com.masiad.arescaperoom.gamelogic.ar.node

/**
 * Puzzle node that need to be solved to open
 */
class PuzzleNode : ComplexNode() {

    /**
     * When node need to be unlock before tap action
     */
    var isLocked = true

    /**
     * User see puzzle node content eg. drawer is open
     * show proper animation and set children visibility
     */
    private var isOpen = false
        set(value) {
            field = value
            animate()
            setChildrenVisible(value)
        }

    /**
     * Node name witch open
     */
    var openInventoryName: String? = null

    fun unlock(inventoryName: String?) {
        isLocked = inventoryName != openInventoryName
        if (!isLocked) {
            isOpen = true
        }
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
}