package com.masiad.arescaperoom.gamelogic.ar.node

/**
 * Puzzle node that need to be solved to open
 */
class PuzzleNode : ComplexNode() {

    /**
     * When node need to be unlock before tap action
     */
    var isLocked = true

    override fun performTap() {
        if (!isLocked) {
            super.performTap()
        }
    }
}