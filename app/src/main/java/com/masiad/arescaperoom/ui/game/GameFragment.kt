package com.masiad.arescaperoom.ui.game

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.ar.core.Plane
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.*
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.masiad.arescaperoom.R
import com.masiad.arescaperoom.adapter.InventoryAdapter
import com.masiad.arescaperoom.data.Inventory
import com.masiad.arescaperoom.databinding.GameFragmentBinding
import com.masiad.arescaperoom.gamelogic.GameConstants
import com.masiad.arescaperoom.gamelogic.GamePhase
import com.masiad.arescaperoom.gamelogic.Level
import com.masiad.arescaperoom.gamelogic.ar.node.animated.filament.FilamentAnimationNode
import com.masiad.arescaperoom.gamelogic.ar.node.animated.property.PropertyAnimationNode
import com.masiad.arescaperoom.gamelogic.ar.node.common.ActionType
import com.masiad.arescaperoom.gamelogic.ar.node.common.AnimationType
import com.masiad.arescaperoom.gamelogic.ar.node.stationary.StationaryNode
import com.masiad.arescaperoom.ui.ar.ArCoreFragment
import com.masiad.arescaperoom.util.extenstion.hasAnchor
import com.masiad.arescaperoom.util.extenstion.horizontalDistanceBetween
import com.masiad.arescaperoom.util.model.ModelLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Main AR game fragment
 */
@AndroidEntryPoint
class GameFragment : Fragment(R.layout.game_fragment) {

    private val args: GameFragmentArgs by navArgs()

    @Inject
    lateinit var modelLoader: ModelLoader

    @Inject
    lateinit var inventoryAdapter: InventoryAdapter

    private lateinit var binding: GameFragmentBinding

    private val viewModel: GameViewModel by viewModels()

    // UI
    private val arCoreFragment: ArCoreFragment
        get() = childFragmentManager.findFragmentById(R.id.ar_core_fragment) as ArCoreFragment

    private val arSceneView: ArSceneView
        get() = arCoreFragment.arSceneView

    private val cameraNode: Camera
        get() = arSceneView.scene.camera

    private val isTrackingState: Boolean
        get() = arSceneView.arFrame?.camera?.trackingState == TrackingState.TRACKING

    private val rootNode by lazy { AnchorNode() }
    private val placingNode by lazy { Node() }
    private val roomNode by lazy { Node() }

    private val doorNode by lazy { FilamentAnimationNode() }

    private val hitPosition by lazy {
        Pair(
            arSceneView.width * GameConstants.hitMultiplierX,
            arSceneView.height * GameConstants.hitMultiplierY
        )
    }

    private var sceneUpdateListener: Scene.OnUpdateListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GameFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        bindData()
        return binding.root
    }

    override fun onDestroyView() {
        arSceneView.scene.removeOnUpdateListener(sceneUpdateListener)
        super.onDestroyView()
    }

    // PRIVATE
    private fun bindData() {
        binding.viewModel = viewModel
        setupInventory()
        observeGamePhase()
        observeLevel()
    }

    private fun setupInventory() {
        binding.inventory.recyclerView.apply {
            adapter = inventoryAdapter
        }
        //todo disable this list and obserwe some in vm
        inventoryAdapter.submitList(listOf(Inventory("a"), Inventory("b")))
        binding.inventoryToggleClickListener = View.OnClickListener {
            viewModel.informInventoryToggle()
        }
    }

    private fun observeGamePhase() {
        viewModel.gamePhase.observe(viewLifecycleOwner, { gamePhase: GamePhase? ->
            when (gamePhase) {
                GamePhase.LOADING -> prepareGame()
                GamePhase.GAME_LOADED -> showInstruction()
                GamePhase.PLACING -> setupPlacing()
                GamePhase.PLACED -> showEscapeRoom()
                GamePhase.GAME_STARTED -> TODO()
                GamePhase.ESCAPING -> TODO()
                GamePhase.ESCAPED -> TODO()
            }
        })
    }

    private fun observeLevel() {
        viewModel.level.observe(viewLifecycleOwner, { level ->
            lifecycleScope.launch {
                prepareEscapeRoom(level)
                viewModel.informPreparingEnded()
            }
        })
    }

    private fun prepareGame() {
        viewModel.loadLevel(args.levelNumber)
    }

    private fun showInstruction() {
        binding.alert.infoText = getString(R.string.instruction_placing)
        binding.alert.clickListener = View.OnClickListener {
            binding.alert.infoText = null
            viewModel.informInstructionAlertClosed()
        }
    }

    private fun setupPlacing() {
        placingNode.setParent(rootNode)
        placingNode.setOnTapListener { hitTestResult, motionEvent ->
            viewModel.informOnSceneClicked(rootNode.hasAnchor())
        }
        arSceneView.updateSceneOnUpdateListener {
            run {
                arSceneView.arFrame
                    ?.hitTest(hitPosition.first, hitPosition.second)
                    ?.forEach { hitResult ->
                        val trackable = hitResult.trackable
                        if (trackable is Plane && trackable.trackingState == TrackingState.TRACKING) {
                            val anchor = hitResult.createAnchor()
                            rootNode.anchor?.detach()
                                ?: rootNode.setParent(arSceneView.scene)
                            rootNode.anchor = anchor
                            return@run
                        }
                    }
                rootNode.anchor?.detach()
            }
        }
    }

    private suspend fun prepareEscapeRoom(level: Level) {
        // Placing preview node
        placingNode.renderable = modelLoader.load(level.placingModelName)

        // Room node
        val roomModelData = level.roomModelData
        //todo correct room model (walls)
        roomNode.renderable = modelLoader.load(roomModelData.modelName)
        roomNode.localPosition = roomModelData.localPosition

        // Door node
        //doorNode.setParent(roomNode)
        val doorModelData = level.doorModelData
        doorNode.renderable = modelLoader.load(doorModelData.modelName)
        doorNode.localPosition = doorModelData.localPosition

        // TODO INSIDE
        // TODO recursive inner node build (child location to 000
        level.modelDataList.forEach { model ->
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
                setParent(roomNode)
                renderable = modelLoader.load(model.modelName)
                localPosition = model.localPosition
                model.localRotation?.let {
                    localRotation = Quaternion.axisAngle(Vector3(it.x, it.y, it.z), it.w)
                }
            }
        }
    }

    private fun showEscapeRoom() {
        placingNode.setParent(null)
        roomNode.setParent(rootNode)

        ValueAnimator.ofFloat(GameConstants.showRoomAnimationStartScale, 1f).apply {
            duration = GameConstants.showRoomAnimationDurationMs
            addUpdateListener {
                val value = it.animatedValue as Float
                roomNode.localScale = Vector3(value, value, value)
            }
            doOnEnd {
                doorNode.startNextAnimation()
            }
        }.start()

        arSceneView.updateSceneOnUpdateListener {
            if (isTrackingState) {
                val distanceToStart =
                    cameraNode.worldPosition.horizontalDistanceBetween(doorNode.worldPosition)
                binding.logs.text = "$distanceToStart"
                if (distanceToStart < GameConstants.startGameDistanceThreshold) {
                    arSceneView.removeSceneOnUpdateListener()
                    doorNode.startNextAnimation()
                    showGameStartedInstruction()
                }
            }
        }
    }

    private fun showGameStartedInstruction() {
        val instructionResId =
            resources.getIdentifier(viewModel.instructionName, "string", context?.packageName)
        binding.alert.infoText = getString(instructionResId)
        binding.alert.clickListener = View.OnClickListener {
            binding.alert.infoText = null
            viewModel.informGameStarted()
            // TODO start timeout
            // Show inventory
        }
    }

    private inline fun ArSceneView.updateSceneOnUpdateListener(crossinline updateAction: (frameTime: FrameTime?) -> Unit) {
        removeSceneOnUpdateListener()
        sceneUpdateListener = Scene.OnUpdateListener {
            updateAction(it)
        }.also { scene.addOnUpdateListener(it) }
    }

    private fun ArSceneView.removeSceneOnUpdateListener() {
        sceneUpdateListener?.let {
            scene.removeOnUpdateListener(it)
        }
    }
}