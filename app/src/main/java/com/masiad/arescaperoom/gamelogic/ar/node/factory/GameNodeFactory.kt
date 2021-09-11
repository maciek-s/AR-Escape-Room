package com.masiad.arescaperoom.gamelogic.ar.node.factory

import androidx.fragment.app.Fragment
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.collision.Box
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.masiad.arescaperoom.gamelogic.Inventory
import com.masiad.arescaperoom.gamelogic.ar.animation.NodeAnimationFactory
import com.masiad.arescaperoom.gamelogic.ar.node.*
import com.masiad.arescaperoom.gamelogic.ar.node.model.*
import com.masiad.arescaperoom.helper.DrawableHelper
import com.masiad.arescaperoom.helper.StringHelper
import com.masiad.arescaperoom.util.extenstion.boxShape
import com.masiad.arescaperoom.util.extenstion.scaledBy
import com.masiad.arescaperoom.util.model.ModelLoader
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
        val node = withContext(Dispatchers.Main) {
            when (model) {
                is NormalModel -> GameNode()
                is InventoryModel -> InventoryNode().apply {
                    withContext(Dispatchers.IO) {
                        requireNotNull(model.nodeNameId) { "nodeNameId is null but inventory should be created" }
                        val name = stringHelper.resolveNodeName(model.nodeNameId!!)
                        val drawableResId =
                            drawableHelper.resolveDrawableResId(model.drawableNameId)
                        inventory = Inventory(name, model.unlockName, drawableResId)
                    }
                }
                is PuzzleModel -> PuzzleNode()
                is InventoryLockedModel -> InventoryLockedNode().apply {
                    unlockName = model.unlockInventoryName
                }
                is PinLockedModel -> PinLockedNode().apply {
                    unlockPin = model.unlockPin

                }
                is LightNodeModel -> LightNode(model.lightModel).apply {
                    collisionShape = Box(model.collisionShapeSize)
                }
            }
        }
        with(node) {
            withContext(Dispatchers.Main) {
                setParent(parent)
            }
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
                withContext(Dispatchers.Main) {
                    val modelRenderable = modelLoader.load(model.modelName)
                    renderable = modelRenderable
                }
                model.animationType?.let {
                    nodeAnimation = nodeAnimationFactory.createAnimation(it, this)
                }
                if (model.isCollisionShapeEnabled == false) {
                    withContext(Dispatchers.Main) {
                        collisionShape = Box(Vector3.zero())
                    }
                }
                model.boundingBox?.let { boundingBox ->
                    val shape = boxShape?.apply {
                        center = Vector3.add(center, boundingBox.centerTransform)
                        size = size.scaledBy(boundingBox.sizeScale)
                    }
                    withContext(Dispatchers.Main) {
                        collisionShape = shape
                    }
                }
            }
            if (model is Puzzle) {
                model.isLocked?.let {
                    (this as? PuzzleNode)?.isLocked = it
                }
            }
            //node.showBoundingBox(fragment.requireContext())
        }
        return node
    }
}