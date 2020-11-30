package com.masiad.arescaperoom.gamelogic.newlogic.animation.property

import com.google.ar.sceneform.math.Vector3

data class PropertyAnimationModel(
    val propertyName: String,
    val animationVector: Vector3,
    val duration: Long
)