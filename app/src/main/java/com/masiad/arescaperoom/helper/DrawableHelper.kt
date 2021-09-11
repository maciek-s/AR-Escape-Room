package com.masiad.arescaperoom.helper

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrawableHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun resolveDrawableResId(identifierName: String): Int {
        return context.resources.getIdentifier(
            identifierName,
            "drawable",
            context.packageName
        )
    }
}