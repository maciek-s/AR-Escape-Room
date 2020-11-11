package com.masiad.arescaperoom.gamelogic

import com.masiad.arescaperoom.gamelogic.ar.ModelData

data class Level(
    val number: Int,
    val instructionName: String,
    val placingModelName: String,
    val roomModelData: ModelData,
    val doorModelData: ModelData,
    val modelDataList: List<ModelData>
)