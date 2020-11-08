package com.masiad.arescaperoom.adapter.bindingadapter

import androidx.databinding.BindingAdapter
import com.google.android.material.progressindicator.ProgressIndicator

@BindingAdapter("animateProgress")
fun bindAnimateProgress(progressIndicator: ProgressIndicator, progress: Int) {
    progressIndicator.setProgress(progress, true)
}