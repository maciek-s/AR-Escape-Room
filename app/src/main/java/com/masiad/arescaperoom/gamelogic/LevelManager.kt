package com.masiad.arescaperoom.gamelogic

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@ActivityRetainedScoped
class LevelManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val levelType by lazy {
        object : TypeToken<Level>() {}.type
    }

    private val gson by lazy { Gson() }

    suspend fun loadLevel(levelNumber: Int): Level {
        return withContext(Dispatchers.IO) {
            suspendCoroutine { continuation ->
                val levelFilename = "lvl_$levelNumber.json"
                context.assets.open(levelFilename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val level = gson.fromJson<Level>(jsonReader, levelType)
                        continuation.resume(level)
                    }
                }
            }
        }
    }
}