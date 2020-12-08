package com.masiad.arescaperoom.util.extenstion

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(snackbarText: String, timeLength: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, snackbarText, timeLength).apply {
        animationMode = Snackbar.ANIMATION_MODE_SLIDE
    }.show()
}

fun View.setupSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<String>,
    doneEvent: () -> Unit,
    timeLength: Int = Snackbar.LENGTH_SHORT
) {
    snackbarEvent.observe(lifecycleOwner, { message ->
        if (message.isNotBlank()) {
            showSnackbar(message, timeLength)
            doneEvent()
        }
    })
}