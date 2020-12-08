package com.masiad.arescaperoom.gamelogic

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Inventory(
    val name: String,
    val unlockName: String,
    @DrawableRes val drawableResId: Int
) : Parcelable