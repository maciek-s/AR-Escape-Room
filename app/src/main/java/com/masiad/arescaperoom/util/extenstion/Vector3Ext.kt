package com.masiad.arescaperoom.util.extenstion

import com.google.ar.sceneform.math.Vector3
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

fun Vector3.distanceBetween(vector: Vector3): Float {
    return Vector3.subtract(vector, this).length()
}

fun Vector3.horizontalDistanceBetween(vector: Vector3): Float {
    val subtracted = Vector3.subtract(vector, this)
    return sqrt(subtracted.x.pow(2) + subtracted.z.pow(2))
}

fun Vector3.shortDistanceToLineBetween(point1: Vector3, point2: Vector3): Float {
    val numerator = (point2.x - point1.x) * (point1.z - z) - (point1.x - x) * (point2.z - point1.z)
    val denominator = sqrt((point2.x - point1.x).pow(2) + (point2.z - point1.z).pow(2))
    return abs(numerator) / denominator
}

fun Vector3.scaledBy(vector: Vector3): Vector3 {
    return Vector3(x * vector.x, y * vector.y, z * vector.z)
}

val Vector3.horizontalVector: Vector3
    get() = Vector3(x, 0f, z)