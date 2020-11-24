package com.masiad.arescaperoom.gamelogic.ar.node.light

import com.google.ar.sceneform.collision.Sphere
import com.google.ar.sceneform.rendering.Light
import com.masiad.arescaperoom.data.Inventory
import com.masiad.arescaperoom.gamelogic.ar.node.common.GameNode

class LightNode(model: LightModel) : GameNode() {

    init {
        val light = Light.builder(model.lightType)
            .build()
        this.light = light
        // todo shpere radius in model
        this.collisionShape = Sphere(0.2f)
    }

    override fun startNextAnimation() {
        // todo cant enable on clik of disabled node
        isEnabled = !isEnabled
    }

    override fun performAction(inventory: Inventory) {}
}