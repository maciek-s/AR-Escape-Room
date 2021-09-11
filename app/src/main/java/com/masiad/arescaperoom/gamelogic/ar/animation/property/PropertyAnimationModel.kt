package com.masiad.arescaperoom.gamelogic.ar.animation.property

import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory

private enum class Type {
    POSITION, ROTATION
}

private interface PropertyModel {
    val propertyName: String
    val duration: Long
}

sealed class PropertyAnimationModel(
    val type: String
) : PropertyModel {
    companion object {
        val runtimeAdapterFactory: RuntimeTypeAdapterFactory<PropertyAnimationModel> by lazy {
            RuntimeTypeAdapterFactory.of(PropertyAnimationModel::class.java)
                .registerSubtype(PositionAnimationModel::class.java, Type.POSITION.name)
                .registerSubtype(RotationAnimationModel::class.java, Type.ROTATION.name)
        }
    }
}

data class PositionAnimationModel(
    override val propertyName: String,
    override val duration: Long,
    val animationVector: Vector3
) : PropertyAnimationModel(Type.POSITION.name)

data class RotationAnimationModel(
    override val propertyName: String,
    override val duration: Long,
    val animationQuaternion: Quaternion
) : PropertyAnimationModel(Type.ROTATION.name)