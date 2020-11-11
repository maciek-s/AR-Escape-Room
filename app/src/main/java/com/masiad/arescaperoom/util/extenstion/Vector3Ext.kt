package com.masiad.arescaperoom.util.extenstion

import com.google.ar.sceneform.math.Vector3
import kotlin.math.pow
import kotlin.math.sqrt

fun Vector3.distanceBetween(vector: Vector3): Float {
    return Vector3.subtract(vector, this).length()
}

fun Vector3.horizontalDistanceBetween(vector: Vector3): Float {
    val subtracted = Vector3.subtract(vector, this)
    return sqrt(subtracted.x.pow(2) + subtracted.z.pow(2))
}