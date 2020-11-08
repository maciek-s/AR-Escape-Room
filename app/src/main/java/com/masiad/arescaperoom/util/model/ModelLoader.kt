package com.masiad.arescaperoom.util.model

import com.google.ar.sceneform.rendering.ModelRenderable

interface ModelLoader {

    suspend fun load(modelName: String): ModelRenderable

    suspend fun load(resId: Int): ModelRenderable
}