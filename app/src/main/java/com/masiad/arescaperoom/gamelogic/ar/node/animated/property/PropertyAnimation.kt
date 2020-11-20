package com.masiad.arescaperoom.gamelogic.ar.node.animated.property

import com.google.ar.sceneform.math.Vector3

data class PropertyAnimation(
    val propertyName: String,
    val animationVector: Vector3,
    val duration: Long
)