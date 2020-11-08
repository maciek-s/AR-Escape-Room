package com.masiad.arescaperoom.gamelogic

import com.masiad.arescaperoom.gamelogic.levels.Level
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@ActivityRetainedScoped
class LevelManager @Inject constructor() {

    suspend fun loadLevel(levelNumber: Int): Level {
        return withContext(Dispatchers.IO) {
            delay(2000)
            suspendCoroutine { continuation ->
                continuation.resume(Level(1))
            }
        }
    }
}