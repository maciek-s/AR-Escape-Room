package com.masiad.arescaperoom.gamelogic.ar.node.factory

import androidx.fragment.app.Fragment
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.masiad.arescaperoom.gamelogic.ar.animation.NodeAnimationFactory
import com.masiad.arescaperoom.gamelogic.ar.node.GameNode
import com.masiad.arescaperoom.gamelogic.ar.node.InventoryNode
import com.masiad.arescaperoom.gamelogic.ar.node.LightNode
import com.masiad.arescaperoom.gamelogic.ar.node.PuzzleNode
import com.masiad.arescaperoom.gamelogic.ar.node.model.*
import com.masiad.arescaperoom.util.model.ModelLoader
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class GameNodeFactory @Inject constructor(
    private val fragment: Fragment,
    private val modelLoader: ModelLoader,
    private val nodeAnimationFactory: NodeAnimationFactory
) {

    suspend fun createNode(parent: Node?, model: GameNodeModel): GameNode {
        // todo specifics properties
        val node = when (model) {
            is NormalModel -> GameNode().apply {

            }
            is InventoryModel -> InventoryNode().apply {

            }
            is PuzzleModel -> PuzzleNode().apply {
                model.isLocked?.let {
                    isLocked = it
                }
            }
            is LightNodeModel -> LightNode().apply {

            }
        }
        with(node) {
            setParent(parent)
            model.isVisible?.let {
                isVisible = it
            }
            if (model.isClickable == true) {
                setOnTapListener { _, _ ->
                    performTap()
                }
                //todo delete on delegate tap to fragment
                if (fragment is GameNode.OnTapListener) {
                    setTapListener(fragment)
                }
            }
            model.localPosition?.let {
                localPosition = it
            }
            model.localRotation?.let {
                localRotation = Quaternion.axisAngle(Vector3(it.x, it.y, it.z), it.w)
            }
            model.childrenModels?.forEach {
                createNode(this, it)
            }
            if (model is RenderableModel) {
                renderable = modelLoader.load(model.modelName)
                model.animationType?.let {
                    nodeAnimation = nodeAnimationFactory.createAnimation(it, this)
                }
            }
        }
        return node
    }
}