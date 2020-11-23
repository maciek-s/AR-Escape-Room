package com.masiad.arescaperoom.gamelogic

import com.google.ar.sceneform.math.Vector3

object GameConstants {
    const val hitMultiplierX = 0.5f
    const val hitMultiplierY = 0.75f

    const val showRoomAnimationStartScale = 0.5f
    const val showRoomAnimationDurationMs = 500L

    val startPointAddVector = Vector3(0f, 0.2f, 0f)
    const val startGameDistanceThreshold = 0.2f
}