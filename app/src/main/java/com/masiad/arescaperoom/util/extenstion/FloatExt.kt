package com.masiad.arescaperoom.util.extenstion

import kotlin.math.roundToLong

fun Float.toMilliseconds(): Long = (this * 1000).roundToLong()