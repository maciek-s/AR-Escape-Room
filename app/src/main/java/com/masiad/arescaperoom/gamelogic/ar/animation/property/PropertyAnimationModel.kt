package com.masiad.arescaperoom.gamelogic.ar.animation.property

import com.google.ar.sceneform.math.Vector3

data class PropertyAnimationModel(
    val propertyName: String,
    val animationVector: Vector3,
    val duration: Long
)