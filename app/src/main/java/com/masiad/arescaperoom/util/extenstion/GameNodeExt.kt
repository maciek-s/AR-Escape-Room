package com.masiad.arescaperoom.util.extenstion

import android.content.Context
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.collision.Box
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.Material
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ShapeFactory
import com.masiad.arescaperoom.gamelogic.ar.node.GameNode

fun GameNode.showBoundingBox(
    context: Context,
    color: Color = Color(0.8f, 0.8f, 0.8f, 0.7f)
) {
    MaterialFactory.makeTransparentWithColor(context, color).thenAccept { material: Material? ->
        (collisionShape as? Box)?.let { box ->
            val cubeRenderable = ShapeFactory.makeCube(box.size, box.center, material)
            Node().apply {
                setParent(this@showBoundingBox)
                renderable = cubeRenderable
            }
        }
    }
}

val GameNode.boxShape: Box?
    get() = collisionShape as? Box