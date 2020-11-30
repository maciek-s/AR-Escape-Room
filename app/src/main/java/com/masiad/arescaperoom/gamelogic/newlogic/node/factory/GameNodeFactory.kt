package com.masiad.arescaperoom.gamelogic.newlogic.node.factory

import androidx.fragment.app.Fragment
import com.google.ar.sceneform.Node
import com.masiad.arescaperoom.gamelogic.newlogic.animation.NodeAnimationFactory
import com.masiad.arescaperoom.gamelogic.newlogic.node.GameNode
import com.masiad.arescaperoom.gamelogic.newlogic.node.InventoryNode
import com.masiad.arescaperoom.gamelogic.newlogic.node.LightNode
import com.masiad.arescaperoom.gamelogic.newlogic.node.PuzzleNode
import com.masiad.arescaperoom.gamelogic.newlogic.node.model.*
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

            }
            is LightNodeModel -> LightNode().apply {

            }
        }
        with(node) {
            setParent(parent)
            model.isVisible?.let {
                isVisible = it
            }
            model.localPosition?.let {
                localPosition = it
            }
            model.localRotation?.let {
                localRotation = it
            }
            model.childrenModels?.forEach {
                createNode(this, it)
            }
            if (model is RenderableModel) {
                renderable = modelLoader.load(model.modelName)
                model.animationType?.let {
                    nodeAnimation = nodeAnimationFactory.createAnimation(it, this)
                    val x = 3
                }
            }
        }
        return node
    }
}