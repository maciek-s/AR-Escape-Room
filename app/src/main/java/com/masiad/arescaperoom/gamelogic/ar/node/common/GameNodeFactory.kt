package com.masiad.arescaperoom.gamelogic.ar.node.common

import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.masiad.arescaperoom.gamelogic.ar.node.animated.filament.FilamentAnimationNode
import com.masiad.arescaperoom.gamelogic.ar.node.animated.property.PropertyAnimationNode
import com.masiad.arescaperoom.gamelogic.ar.node.stationary.StationaryNode
import com.masiad.arescaperoom.util.model.ModelLoader
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class GameNodeFactory @Inject constructor(
    private val modelLoader: ModelLoader
) {

    suspend fun createNode(parent: Node?, model: GameNodeModel): GameNode {
        val node = when (model.animationType) {
            AnimationType.NONE, null -> {
                val node = StationaryNode()
                node
            }
            AnimationType.FILAMENT -> {
                val node = FilamentAnimationNode()
                node

            }
            AnimationType.PROPERTY -> {
                requireNotNull(model.propertyAnimation) { "AnimationType.PROPERTY but propertyAnimation is null" }
                val node = PropertyAnimationNode(model.propertyAnimation)
                node
            }
        }
        with(node) {
            when (model.actionType) {
                ActionType.NONE, null -> {

                }
                ActionType.PICK_UP -> {

                }
                ActionType.ANIMATE -> {
                    setOnTapListener { _, _ ->
                        startNextAnimation()
                    }
                }
                ActionType.USE_INVENTORY -> {

                }
            }
            setParent(parent)
            renderable = modelLoader.load(model.modelName)
            model.localPosition?.let {
                localPosition = it
            }
            model.localRotation?.let {
                localRotation = Quaternion.axisAngle(Vector3(it.x, it.y, it.z), it.w)
            }
            model.childrenDataList?.forEach {
                createNode(node, it)
            }
        }
        return node
    }
}