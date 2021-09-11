package com.masiad.arescaperoom.gamelogic.ar.animation

import com.google.gson.typeadapters.RuntimeTypeAdapterFactory
import com.masiad.arescaperoom.gamelogic.ar.animation.property.PropertyAnimationModel

private enum class Type {
    NONE, FILAMENT, PROPERTY
}

sealed class AnimationType(val type: String) {
    companion object {
        val runtimeAdapterFactory: RuntimeTypeAdapterFactory<AnimationType> by lazy {
            RuntimeTypeAdapterFactory.of(AnimationType::class.java)
                .registerSubtype(None::class.java, Type.NONE.name)
                .registerSubtype(Filament::class.java, Type.FILAMENT.name)
                .registerSubtype(Property::class.java, Type.PROPERTY.name)
        }
    }
}

object None : AnimationType(Type.NONE.name)
object Filament : AnimationType(Type.FILAMENT.name)
data class Property(
    val propertyAnimationModel: PropertyAnimationModel
) : AnimationType(Type.PROPERTY.name)