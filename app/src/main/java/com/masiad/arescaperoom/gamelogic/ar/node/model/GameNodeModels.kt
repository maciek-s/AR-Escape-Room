package com.masiad.arescaperoom.gamelogic.ar.node.model

import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory
import com.masiad.arescaperoom.gamelogic.ar.animation.AnimationType

private enum class Type {
    NORMAL, INVENTORY, PUZZLE, INVENTORY_LOCKED, PIN_LOCKED, LIGHT
}

private interface Model {
    val nodeNameId: String?
    val isVisible: Boolean?
    val isClickable: Boolean?
    val localPosition: Vector3?
    val localRotation: Quaternion?
    val childrenModels: List<GameNodeModel>?
}

interface RenderableModel {
    val modelName: String
    val animationType: AnimationType?
    val isCollisionShapeEnabled: Boolean?
    val boundingBox: BoundingBox?
}

interface Puzzle {
    val isLocked: Boolean?
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
                .registerSubtype(InventoryLockedModel::class.java, Type.INVENTORY_LOCKED.name)
                .registerSubtype(PinLockedModel::class.java, Type.PIN_LOCKED.name)
                .registerSubtype(LightNodeModel::class.java, Type.LIGHT.name)
        }
    }
}

data class NormalModel(
    override val nodeNameId: String?,
    override val isVisible: Boolean?,
    override val isClickable: Boolean?,
    override val localPosition: Vector3?,
    override val localRotation: Quaternion?,
    override val isCollisionShapeEnabled: Boolean?,
    override val boundingBox: BoundingBox?,
    override val childrenModels: List<GameNodeModel>?,
    override val modelName: String,
    override val animationType: AnimationType?,
) : GameNodeModel(Type.NORMAL.name), RenderableModel

data class InventoryModel(
    override val nodeNameId: String?,
    override val isVisible: Boolean?,
    override val isClickable: Boolean?,
    override val localPosition: Vector3?,
    override val localRotation: Quaternion?,
    override val isCollisionShapeEnabled: Boolean?,
    override val boundingBox: BoundingBox?,
    override val childrenModels: List<GameNodeModel>?,
    override val modelName: String,
    override val animationType: AnimationType?,
    val unlockName: String,
    val drawableNameId: String,
) : GameNodeModel(Type.INVENTORY.name), RenderableModel

data class PuzzleModel(
    override val nodeNameId: String?,
    override val isVisible: Boolean?,
    override val isClickable: Boolean?,
    override val localPosition: Vector3?,
    override val localRotation: Quaternion?,
    override val isCollisionShapeEnabled: Boolean?,
    override val boundingBox: BoundingBox?,
    override val childrenModels: List<GameNodeModel>?,
    override val modelName: String,
    override val animationType: AnimationType?,
    override val isLocked: Boolean?,
) : GameNodeModel(Type.PUZZLE.name), RenderableModel, Puzzle

data class InventoryLockedModel(
    override val nodeNameId: String?,
    override val isVisible: Boolean?,
    override val isClickable: Boolean?,
    override val localPosition: Vector3?,
    override val localRotation: Quaternion?,
    override val isCollisionShapeEnabled: Boolean?,
    override val boundingBox: BoundingBox?,
    override val childrenModels: List<GameNodeModel>?,
    override val modelName: String,
    override val animationType: AnimationType?,
    override val isLocked: Boolean?,
    val unlockInventoryName: String
) : GameNodeModel(Type.INVENTORY_LOCKED.name), RenderableModel, Puzzle

data class PinLockedModel(
    override val nodeNameId: String?,
    override val isVisible: Boolean?,
    override val isClickable: Boolean?,
    override val localPosition: Vector3?,
    override val localRotation: Quaternion?,
    override val isCollisionShapeEnabled: Boolean?,
    override val boundingBox: BoundingBox?,
    override val childrenModels: List<GameNodeModel>?,
    override val modelName: String,
    override val animationType: AnimationType?,
    override val isLocked: Boolean?,
    val unlockPin: String
) : GameNodeModel(Type.PIN_LOCKED.name), RenderableModel, Puzzle

data class LightNodeModel(
    override val nodeNameId: String?,
    override val isVisible: Boolean?,
    override val isClickable: Boolean?,
    override val localPosition: Vector3?,
    override val localRotation: Quaternion?,
    override val childrenModels: List<GameNodeModel>?,
    val lightModel: LightModel,
    val collisionShapeSize: Vector3
) : GameNodeModel(Type.LIGHT.name)