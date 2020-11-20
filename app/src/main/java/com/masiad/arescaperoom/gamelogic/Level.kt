package com.masiad.arescaperoom.gamelogic

import com.masiad.arescaperoom.gamelogic.ar.node.common.GameNodeModel

data class Level(
    val number: Int,
    val instructionName: String,
    val placingModelName: String,
    val roomModelData: GameNodeModel,
    val doorModelData: GameNodeModel,
    val modelDataList: List<GameNodeModel>
)