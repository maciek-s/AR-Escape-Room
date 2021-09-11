package com.masiad.arescaperoom.adapter.bindingadapter

import androidx.databinding.BindingAdapter
import com.google.android.material.progressindicator.LinearProgressIndicator

@BindingAdapter("animateProgress")
fun bindAnimateProgress(progressIndicator: LinearProgressIndicator, progress: Int) {
    progressIndicator.setProgress(progress, true)
}