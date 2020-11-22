package com.masiad.arescaperoom.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout

// TODO delete if not ussage
class InterceptConstraintLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var motionEventListener: ((event: MotionEvent?) -> Unit)? = null

    fun setOnMotionEventListener(listener: (event: MotionEvent?) -> Unit) {
        motionEventListener = listener
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        motionEventListener?.invoke(event)
        return super.onInterceptTouchEvent(event)
    }
}