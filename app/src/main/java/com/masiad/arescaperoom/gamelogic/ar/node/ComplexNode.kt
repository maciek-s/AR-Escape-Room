package com.masiad.arescaperoom.gamelogic.ar.node

import android.util.Log
import com.masiad.arescaperoom.util.extenstion.TAG

/**
 * Node that contains multiple logic connected nodes
 */
open class ComplexNode : GameNode() {

    protected fun setChildrenVisible(isVisible: Boolean) {
        children.filterIsInstance<GameNode>()
            .forEach {
                it.isVisible = isVisible
                Log.i(TAG, "Now visible $it")
                // todo update collision shape when visible
                if (isVisible) {

                } else {

                }
            }
    }
}