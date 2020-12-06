package com.masiad.arescaperoom.adapter.bindingadapter

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("imageRes")
fun bindImageRes(imageView: ImageView, @DrawableRes imageResId: Int) {
    imageView.setImageResource(imageResId)
}