package com.masiad.arescaperoom.adapter.bindingadapter

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibility")
fun bindVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}