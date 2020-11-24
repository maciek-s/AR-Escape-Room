package com.masiad.arescaperoom.gamelogic.ar.node.common

import androidx.fragment.app.Fragment
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.masiad.arescaperoom.gamelogic.ar.node.animated.filament.FilamentAnimationNode
import com.masiad.arescaperoom.gamelogic.ar.node.animated.property.PropertyAnimationNode
import com.masiad.arescaperoom.gamelogic.ar.node.light.LightNode
import com.masiad.arescaperoom.gamelogic.ar.node.stationary.StationaryNode
import com.masiad.arescaperoom.util.model.ModelLoader
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class GameNodeFactory @Inject constructor(
    private val fragment: Fragment,
    private val modelLoader: ModelLoader
) {

    suspend fun createNode(parent: Node?, model: GameNodeModel): GameNode {
        val node = when (model.nodeType) {
            NodeType.STATIONARY, null -> {
                val node = StationaryNode()
                node
            }
            NodeType.FILAMENT -> {
                val node = FilamentAnimationNode()
                node

            }
            NodeType.PROPERTY -> {
                requireNotNull(model.propertyAnimation) { "NodeType.PROPERTY but propertyAnimation is null" }
                val node = PropertyAnimationNode(model.propertyAnimation)
                node
            }
            NodeType.LIGHT -> {
                requireNotNull(model.lightModel) { "NodeType.LIGHT but lightModel is null" }
                val node = LightNode(model.lightModel)
                node
            }
        }
        with(node) {
            setParent(parent)
            // todo remoce try catch after implement sealed class
            try {
                renderable = modelLoader.load(model.modelName)
            } catch (e: Exception) {
            }
            name = model.modelName
            model.isVisible?.let {
                isVisible = it
            }
            model.localPosition?.let {
                localPosition = it
            }
            model.localRotation?.let {
                localRotation = Quaternion.axisAngle(Vector3(it.x, it.y, it.z), it.w)
            }
            model.childrenDataList?.forEach {
                createNode(node, it)
            }
            if (fragment is GameNode.OnTapListener) {
                setOnGameNodeTapListener(fragment)
            }
        }
        return node
    }
}