package com.masiad.arescaperoom.model

import androidx.lifecycle.MutableLiveData

class PinModel(
    private val correctPin: String,
    private val callback: PinChecker
) {
    val pin: MutableLiveData<String> by lazy { MutableLiveData("") }

    fun onKeyClick(key: String) {
        pin.value?.let {
            val input = it.plus(key)
            pin.value = input
            if (input.length == correctPin.length) {
                checkPin(input)
            }
        }
    }

    private fun checkPin(current: String) {
        callback.isCorrect(current == correctPin)
    }
}