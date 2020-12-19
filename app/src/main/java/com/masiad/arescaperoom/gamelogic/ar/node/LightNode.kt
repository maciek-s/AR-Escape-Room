package com.masiad.arescaperoom.gamelogic.ar.node

import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Light
import com.masiad.arescaperoom.gamelogic.ar.node.model.LightModel

/**
 * Node with light
 */
class LightNode(model: LightModel) : GameNode() {

    var isLightOn: Boolean = true
        set(value) {
            field = value
            light?.intensity = if (value) {
                lightIntensity
            } else {
                0f
            }
        }

    private var lightIntensity: Float = 0f

    init {
        localScale = Vector3(2f, 2f, 2f)
        with(model) {
            light = Light.builder(type).apply {
                shadowCastingEnabled?.let {
                    setShadowCastingEnabled(it)
                }
                color?.let {
                    setColor(it)
                }
                temperature?.let {
                    setColorTemperature(it)
                }
                intensity?.let {
                    setIntensity(it)
                }
                falloffRadius?.let {
                    setFalloffRadius(it)
                }
                innerConeAngle?.let {
                    setInnerConeAngle(it)
                }
                outerConeAngle?.let {
                    setOuterConeAngle(it)
                }
            }.build()
            isOn?.let {
                isLightOn = it
            }
            light?.intensity?.let {
                lightIntensity = it
            }
        }
    }

    override fun performTapAction() {
        isLightOn = !isLightOn
    }
}