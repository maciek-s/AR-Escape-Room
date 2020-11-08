package com.masiad.arescaperoom.util.model

import android.content.Context
import com.google.ar.sceneform.rendering.ModelRenderable
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
class ModelLoaderImlp(
    private val context: Context
) : ModelLoader {

    override fun load(resId: Int, completed: (modelRenderable: ModelRenderable) -> Unit) {
        val future = ModelRenderable.builder()
            .setSource(context, resId)
            .build()
        future.thenApplyAsync {
            completed(it)
        }
    }
}