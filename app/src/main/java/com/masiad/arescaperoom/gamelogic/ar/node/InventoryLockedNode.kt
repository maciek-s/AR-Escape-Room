package com.masiad.arescaperoom.gamelogic.ar.node

class InventoryLockedNode : PuzzleNode() {

    /**
     * Inventory node name with unlock
     */
    lateinit var unlockName: String

    fun unlock(name: String) {
        if (name == unlockName) {
            isLocked = false
            isOpen = true
        }
    }
}