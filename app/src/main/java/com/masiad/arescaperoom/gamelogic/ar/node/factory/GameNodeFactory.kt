package com.masiad.arescaperoom.gamelogic.ar.node.factory

import androidx.fragment.app.Fragment
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.collision.Box
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.masiad.arescaperoom.gamelogic.Inventory
import com.masiad.arescaperoom.gamelogic.ar.animation.NodeAnimationFactory
import com.masiad.arescaperoom.gamelogic.ar.node.GameNode
import com.masiad.arescaperoom.gamelogic.ar.node.InventoryNode
import com.masiad.arescaperoom.gamelogic.ar.node.LightNode
import com.masiad.arescaperoom.gamelogic.ar.node.PuzzleNode
import com.masiad.arescaperoom.gamelogic.ar.node.model.*
import com.masiad.arescaperoom.helper.DrawableHelper
import com.masiad.arescaperoom.helper.StringHelper
import com.masiad.arescaperoom.util.extenstion.boxShape
import com.masiad.arescaperoom.util.extenstion.scaledBy
import com.masiad.arescaperoom.util.model.ModelLoader
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class GameNodeFactory @Inject constructor(
    private val fragment: Fragment,
    private val modelLoader: ModelLoader,
    private val nodeAnimationFactory: NodeAnimationFactory,
    private val stringHelper: StringHelper,
    private val drawableHelper: DrawableHelper
) {

    suspend fun createNode(parent: Node?, model: GameNodeModel): GameNode {
        // todo specifics properties
        val node = when (model) {
            is NormalModel -> GameNode().apply {

            }
            is InventoryModel -> InventoryNode().apply {
                requireNotNull(model.nodeNameId) { "nodeNameId is null but inventory should be created" }
                val name = stringHelper.resolveNodeName(model.nodeNameId!!)
                val drawableResId = drawableHelper.resolveDrawableResId(model.drawableNameId)
                inventory = Inventory(name, model.unlockName, drawableResId)
            }
            is PuzzleModel -> PuzzleNode().apply {
                model.isLocked?.let {
                    isLocked = it
                }
                model.unlockInventoryName?.let {
                    unlockInventoryName = it
                }
            }
            is LightNodeModel -> LightNode().apply {

            }
        }
        with(node) {
            setParent(parent)
            model.nodeNameId?.let {
                name = stringHelper.resolveNodeName(it)
            }
            model.isVisible?.let {
                isVisible = it
            }
            if (model.isClickable == true) {
                setOnTapListener { _, _ ->
                    onTap()
                }
                //todo delete on delegate tap to fragment
                if (fragment is GameNode.OnTapListener) {
                    setOnTapListener(fragment)
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
                if (model.isCollisionShapeEnabled == false) {
                    collisionShape = Box(Vector3.zero())
                }
                model.boundingBox?.let { boundingBox ->
                    collisionShape = boxShape?.apply {
                        center = Vector3.add(center, boundingBox.centerTransform)
                        size = size.scaledBy(boundingBox.sizeScale)
                    }
                }
            }
            //node.showBoundingBox(fragment.requireContext())
        }
        return node
    }
}