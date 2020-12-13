package com.masiad.arescaperoom.model

import androidx.lifecycle.MutableLiveData

class PinModel(
    private val correctPin: String,
    private val callback: PinChecker
) {
    val pin: MutableLiveData<String> by lazy { MutableLiveData("") }

    fun onKeyClick(key: String) {
        pin.value?.let {
            pin.value = it.plus(key)
            if (it.length == correctPin.length) {
                checkPin(it)
            }
        }
    }

    private fun checkPin(current: String) {
        callback.isCorrect(current == correctPin)
    }
}