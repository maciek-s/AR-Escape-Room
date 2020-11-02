package com.masiad.arescaperoom

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class annotated with @HiltAndroidApp that triggers Hilt's code generation
 * https://developer.android.com/training/dependency-injection/hilt-android
 */
@HiltAndroidApp
class MainApplication : Application()