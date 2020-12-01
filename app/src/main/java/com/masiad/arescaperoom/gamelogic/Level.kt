package com.masiad.arescaperoom.gamelogic

import com.masiad.arescaperoom.gamelogic.ar.node.model.GameNodeModel

data class Level(
    val number: Int,
    val placingModelName: String,
    val roomModel: GameNodeModel,
    val doorModel: GameNodeModel,
    val insideModels: List<GameNodeModel>
)