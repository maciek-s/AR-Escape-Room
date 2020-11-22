package com.masiad.arescaperoom.gamelogic.ar.node.common

import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.masiad.arescaperoom.gamelogic.ar.node.animated.property.PropertyAnimation

data class GameNodeModel(
    val modelName: String,
    val isVisible: Boolean?,
    val localPosition: Vector3?,
    val localRotation: Quaternion?,
    val animationType: AnimationType?,
    val propertyAnimation: PropertyAnimation?,
    val actionType: ActionType?,
    val actionInventoryModelName: String?,
    val inventoryActionType: InventoryActionType?,
    val childrenDataList: List<GameNodeModel>?
)