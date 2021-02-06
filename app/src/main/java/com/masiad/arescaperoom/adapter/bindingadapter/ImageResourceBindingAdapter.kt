package com.masiad.arescaperoom.adapter.bindingadapter

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("imageRes")
fun bindImageRes(imageView: ImageView, @DrawableRes imageResId: Int) {
    Picasso.get()
        .load(imageResId)
        .into(imageView)
}