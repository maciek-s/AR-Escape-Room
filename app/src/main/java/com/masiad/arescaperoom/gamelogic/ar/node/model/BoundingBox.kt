package com.masiad.arescaperoom.gamelogic.ar.node.model

import com.google.ar.sceneform.math.Vector3

data class BoundingBox(
    val centerTransform: Vector3,
    val sizeScale: Vector3
)