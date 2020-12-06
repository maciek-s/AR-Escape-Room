package com.masiad.arescaperoom.gamelogic

import androidx.annotation.DrawableRes

data class Inventory(
    val name: String,
    val unlockName: String,
    @DrawableRes val drawableResId: Int
)