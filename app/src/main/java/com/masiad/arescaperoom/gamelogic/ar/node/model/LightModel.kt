package com.masiad.arescaperoom.gamelogic.ar.node.model

import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.Light

data class LightModel(
    val type: Light.Type,
    val isOn: Boolean?,
    val shadowCastingEnabled: Boolean?,
    val color: Color?,
    val temperature: Float?,
    val intensity: Float?,
    val falloffRadius: Float?,
    val innerConeAngle: Float?,
    val outerConeAngle: Float?
)