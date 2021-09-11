package com.masiad.arescaperoom.gamelogic.ar.animation

import com.masiad.arescaperoom.gamelogic.ar.animation.filament.FilamentAnimation
import com.masiad.arescaperoom.gamelogic.ar.animation.property.PropertyAnimation
import com.masiad.arescaperoom.gamelogic.ar.node.GameNode
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NodeAnimationFactory @Inject constructor() {

    fun createAnimation(type: AnimationType, node: GameNode): NodeAnimation? {
        with(node) {
            return when (type) {
                is None -> null
                is Filament -> FilamentAnimation(renderableInstance)
                is Property -> PropertyAnimation(type.propertyAnimationModel, node)
            }
        }
    }
}