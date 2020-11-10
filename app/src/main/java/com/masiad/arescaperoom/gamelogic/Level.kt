package com.masiad.arescaperoom.gamelogic

data class Level(
    val number: Int,
    val instructionName: String,
    val placingModelName: String,
    val roomModelName: String,
    val doorModelName: String,
    val gameModelNames: List<String>
)