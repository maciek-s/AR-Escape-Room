package com.masiad.arescaperoom.util.model

import com.google.ar.sceneform.rendering.ModelRenderable

interface ModelLoader {

    fun load(resId: Int, completed: (modelRenderable: ModelRenderable) -> Unit)
}