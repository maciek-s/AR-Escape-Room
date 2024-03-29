package com.masiad.arescaperoom.gamelogic.ar.node

class PinLockedNode : PuzzleNode() {

    /**
     * Unlock PIN combination
     */
    lateinit var unlockPin: String

    fun unlock() {
        isLocked = false
        isOpen = true
    }
}