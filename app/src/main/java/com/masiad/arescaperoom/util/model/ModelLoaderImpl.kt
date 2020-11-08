package com.masiad.arescaperoom.util.model

import android.content.Context
import com.google.ar.sceneform.rendering.ModelRenderable
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.future.await

@FragmentScoped
class ModelLoaderImpl(
    private val context: Context
) : ModelLoader {

    override suspend fun load(modelName: String): ModelRenderable {
        val modelResId = context.resources.getIdentifier(modelName, "raw", context.packageName)
        return load(modelResId)
    }

    override suspend fun load(resId: Int): ModelRenderable {
        return ModelRenderable.builder()
            .setSource(context, resId)
            .build()
            .await()
    }
}