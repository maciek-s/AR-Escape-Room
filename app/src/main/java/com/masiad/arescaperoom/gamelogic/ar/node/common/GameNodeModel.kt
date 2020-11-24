package com.masiad.arescaperoom.gamelogic.ar.node.common

import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.masiad.arescaperoom.gamelogic.ar.node.animated.property.PropertyAnimation
import com.masiad.arescaperoom.gamelogic.ar.node.light.LightModel

data class GameNodeModel(
    val modelName: String,
    val isVisible: Boolean?,
    val localPosition: Vector3?,
    val localRotation: Quaternion?,
    val nodeType: NodeType?,
    val propertyAnimation: PropertyAnimation?,
    val actionType: ActionType?,
    val actionInventoryModelName: String?,
    val inventoryActionType: InventoryActionType?,
    val lightModel: LightModel?,
    val childrenDataList: List<GameNodeModel>?
)

// TODO sealed class with only type specyfic properties