package com.masiad.arescaperoom.adapter.bindingadapter

import android.content.res.Resources
import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView

@BindingAdapter("inventoryListTranslateX")
fun bindInventoryListTranslateX(view: View, isToggle: Boolean) {
    val value = if (isToggle) {
        0f
    } else {
        view.width.toFloat()
    }
    view.animate().translationX(value).start()
}

@BindingAdapter("inventoryToggleTranslateX")
fun bindInventoryToggleTranslateX(view: View, isToggle: Boolean) {
    val value = if (isToggle) {
        0f
    } else {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        (widthPixels - location[0] - view.width).toFloat()
    }
    view.animate().translationX(value).start()
}

@BindingAdapter("checked")
fun bindChecked(cardView: MaterialCardView, isChecked: Boolean) {
    cardView.isChecked = isChecked
}

private val widthPixels: Int
    get() = Resources.getSystem().displayMetrics.widthPixels