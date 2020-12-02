package com.masiad.arescaperoom.gamelogic.ar.node.model

import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory
import com.masiad.arescaperoom.gamelogic.ar.animation.AnimationType

private enum class Type {
    NORMAL, INVENTORY, PUZZLE, LIGHT
}

private interface Model {
    val isVisible: Boolean?
    val isClickable: Boolean?
    val localPosition: Vector3?
    val localRotation: Quaternion?
    val childrenModels: List<GameNodeModel>?
}

interface RenderableModel {
    val modelName: String
    val animationType: AnimationType?
}

sealed class GameNodeModel(
    val type: String
) : Model {
    companion object {
        val runtimeAdapterFactory: RuntimeTypeAdapterFactory<GameNodeModel> by lazy {
            RuntimeTypeAdapterFactory.of(GameNodeModel::class.java)
                .registerSubtype(NormalModel::class.java, Type.NORMAL.name)
                .registerSubtype(InventoryModel::class.java, Type.INVENTORY.name)
                .registerSubtype(PuzzleModel::class.java, Type.PUZZLE.name)
                .registerSubtype(LightNodeModel::class.java, Type.LIGHT.name)
        }
    }
}

data class NormalModel(
    override val isVisible: Boolean?,
    override val isClickable: Boolean?,
    override val localPosition: Vector3?,
    override val localRotation: Quaternion?,
    override val childrenModels: List<GameNodeModel>?,
    override val modelName: String,
    override val animationType: AnimationType?,
) : GameNodeModel(Type.NORMAL.name), RenderableModel

data class InventoryModel(
    override val isVisible: Boolean?,
    override val isClickable: Boolean?,
    override val localPosition: Vector3?,
    override val localRotation: Quaternion?,
    override val childrenModels: List<GameNodeModel>?,
    override val modelName: String,
    override val animationType: AnimationType?,
    val visibleAdditionalSize: Vector3?
) : GameNodeModel(Type.INVENTORY.name), RenderableModel

data class PuzzleModel(
    override val isVisible: Boolean?,
    override val isClickable: Boolean?,
    override val localPosition: Vector3?,
    override val localRotation: Quaternion?,
    override val childrenModels: List<GameNodeModel>?,
    override val modelName: String,
    override val animationType: AnimationType?,
    val isLocked: Boolean?,
    val openInventoryName: String?
) : GameNodeModel(Type.PUZZLE.name), RenderableModel

data class LightNodeModel(
    override val isVisible: Boolean?,
    override val isClickable: Boolean?,
    override val localPosition: Vector3?,
    override val localRotation: Quaternion?,
    override val childrenModels: List<GameNodeModel>?,
) : GameNodeModel(Type.LIGHT.name)