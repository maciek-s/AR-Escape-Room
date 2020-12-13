package com.masiad.arescaperoom.ui.game

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.google.ar.core.Plane
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.*
import com.google.ar.sceneform.collision.Ray
import com.google.ar.sceneform.math.Vector3
import com.masiad.arescaperoom.R
import com.masiad.arescaperoom.adapter.inventory.*
import com.masiad.arescaperoom.databinding.GameFragmentBinding
import com.masiad.arescaperoom.gamelogic.GameConstants
import com.masiad.arescaperoom.gamelogic.GamePhase
import com.masiad.arescaperoom.gamelogic.Inventory
import com.masiad.arescaperoom.gamelogic.Level
import com.masiad.arescaperoom.gamelogic.ar.node.*
import com.masiad.arescaperoom.gamelogic.ar.node.factory.GameNodeFactory
import com.masiad.arescaperoom.helper.StringHelper
import com.masiad.arescaperoom.ui.ar.ArCoreFragment
import com.masiad.arescaperoom.util.extenstion.*
import com.masiad.arescaperoom.util.model.ModelLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Main AR game fragment
 */
@AndroidEntryPoint
class GameFragment : Fragment(R.layout.game_fragment), GameNode.OnTapListener {

    private val args: GameFragmentArgs by navArgs()

    @Inject
    lateinit var modelLoader: ModelLoader

    @Inject
    lateinit var gameNodeFactory: GameNodeFactory

    @Inject
    lateinit var inventoryAdapter: InventoryAdapter

    @Inject
    lateinit var stringHelper: StringHelper

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

    // Root objects node
    private val rootAnchorNode by lazy { AnchorNode() }

    // Updated placing node
    private val placingNode by lazy { Node() }

    // Root room node
    private lateinit var roomNode: GameNode

    // Room entry node
    private lateinit var doorNode: GameNode

    private val hitPosition by lazy {
        Pair(
            arSceneView.width * GameConstants.hitMultiplierX,
            arSceneView.height * GameConstants.hitMultiplierY
        )
    }

    private var sceneUpdateListener: Scene.OnUpdateListener? = null

    private var selectionTracker: SelectionTracker<Inventory>? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.post {
            viewModel.informPostOnViewCreated()
        }
    }

    override fun onDestroyView() {
        arSceneView.scene.removeOnUpdateListener(sceneUpdateListener)
        super.onDestroyView()
    }

    // PRIVATE
    private fun bindData() {
        binding.viewModel = viewModel
        setupSnackbar()
        setupMoveButton()
        setupInventory()
        observeGamePhase()
        observeLevel()
    }

    private fun setupSnackbar() {
        binding.root.setupSnackbar(
            viewLifecycleOwner,
            viewModel.showSnackBarEvent,
            viewModel::doneShowingSnackbar
        )
    }

    private fun setupMoveButton() {
        // TODO [Feature] Continuous move?
        binding.moveButton.setOnClickListener {
            lifecycleScope.launch {
                relocateRoom()
            }
        }
    }

    private suspend fun relocateRoom() {
        withContext(Dispatchers.IO) {
            val ray = Ray(cameraNode.worldPosition, cameraNode.forward)
            val stepVector = ray.direction.horizontalVector.scaled(0.1f)
            with(roomNode) {
                localPosition = Vector3.subtract(localPosition, stepVector)
            }
        }
    }

    private fun setupInventory() {
        binding.inventory.recyclerView.apply {
            adapter = inventoryAdapter
        }
        selectionTracker = SelectionTracker.Builder<Inventory>(
            "inventory-selection-id",
            binding.inventory.recyclerView,
            InventoryKeyProvider(inventoryAdapter),
            InventoryDetailsLookup(binding.inventory.recyclerView),
            StorageStrategy.createParcelableStorage(Inventory::class.java)
        )
            .withSelectionPredicate(InventorySelectionPredicate())
            .build()
        inventoryAdapter.selectionChecker = SelectionChecker {
            selectionTracker?.isSelected(it) ?: false
        }
        viewModel.inventoryList.observe(viewLifecycleOwner, {
            inventoryAdapter.submitList(it)
        })
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
                GamePhase.GAME_STARTED -> setupGameStartedUpdateListener()
                GamePhase.ESCAPED -> setupEscapedUpdateListener()
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
        placingNode.setParent(rootAnchorNode)
        placingNode.setOnTapListener { _, _ ->
            viewModel.informOnSceneClicked(rootAnchorNode.hasAnchor())
        }
        arSceneView.updateSceneOnUpdateListener {
            run {
                arSceneView.arFrame
                    ?.hitTest(hitPosition.first, hitPosition.second)
                    ?.forEach { hitResult ->
                        val trackable = hitResult.trackable
                        if (trackable is Plane && trackable.trackingState == TrackingState.TRACKING) {
                            val anchor = hitResult.createAnchor()
                            rootAnchorNode.anchor?.detach()
                                ?: rootAnchorNode.setParent(arSceneView.scene)
                            rootAnchorNode.anchor = anchor
                            return@run
                        }
                    }
                rootAnchorNode.anchor?.detach()
            }
        }
    }

    private suspend fun prepareEscapeRoom(level: Level) {
        placingNode.renderable = modelLoader.load(level.placingModelName)

        // Room node
        roomNode = gameNodeFactory.createNode(null, level.roomModel)

        // Door node
        doorNode = gameNodeFactory.createNode(roomNode, level.doorModel)

        // Room inside
        level.insideModels.forEach { model ->
            gameNodeFactory.createNode(roomNode, model)
        }
    }

    private fun showEscapeRoom() {
        placingNode.setParent(null)
        roomNode.setParent(rootAnchorNode)

        ValueAnimator.ofFloat(GameConstants.showRoomAnimationStartScale, 1f).apply {
            duration = GameConstants.showRoomAnimationDurationMs
            addUpdateListener {
                val value = it.animatedValue as Float
                roomNode.localScale = Vector3(value, value, value)
            }
            doOnEnd {
                doorNode.animate()
            }
        }.start()

        arSceneView.updateSceneOnUpdateListener {
            val point1 = Vector3.add(
                doorNode.worldPosition,
                Vector3(-GameConstants.doorPointXOffset, 0f, -GameConstants.doorPointZOffset)
            )
            val point2 = Vector3.add(
                doorNode.worldPosition,
                Vector3(GameConstants.doorPointXOffset, 0f, -GameConstants.doorPointZOffset)
            )
            checkCameraDistance(point1, point2, GameConstants.doorDistanceThreshold) {
                arSceneView.removeSceneOnUpdateListener()
                doorNode.apply {
                    isVisible = true
                    animate()
                }
                showGameStartedInstruction()
            }
        }
    }

    private fun checkCameraDistance(
        point1: Vector3,
        point2: Vector3,
        threshold: Float,
        reachedCallback: () -> Unit
    ) {
        if (!isTrackingState) {
            return
        }
        val distance = cameraNode.worldPosition.shortDistanceToLineBetween(point1, point2)
        binding.logs.text = distance.toString()
        if (distance < threshold) {
            reachedCallback()
        }
    }

    private fun showGameStartedInstruction() {
        val instruction = stringHelper.resolveInstruction(viewModel.levelNumber)
        binding.alert.infoText = instruction
        binding.alert.clickListener = View.OnClickListener {
            binding.alert.infoText = null
            viewModel.informGameStarted()
        }
    }

    private fun setupGameStartedUpdateListener() {
        // TODO [Feature] Detect user outside the room. Detect tracking lost
    }

    private fun setupEscapedUpdateListener() {
        doorNode.isVisible = false
        arSceneView.updateSceneOnUpdateListener {
            val point1 = Vector3.add(
                doorNode.worldPosition,
                Vector3(-GameConstants.doorPointXOffset, 0f, 0f)
            )
            val point2 = Vector3.add(
                doorNode.worldPosition,
                Vector3(GameConstants.doorPointXOffset, 0f, 0f)
            )
            checkCameraDistance(point1, point2, GameConstants.doorDistanceThreshold) {
                arSceneView.removeSceneOnUpdateListener()
                showEndGameInfo()
            }
        }
    }

    private fun showEndGameInfo() {
        binding.alert.infoText = getString(R.string.end_game_info)
        binding.alert.clickListener = View.OnClickListener {
            findNavController().navigateUp()
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

    // GameNode.OnTapListener

    override fun onInventoryPickUp(node: InventoryNode) {
        Log.i(TAG, "onInventoryPickUp $node")
        viewModel.informInventoryPickUp(node.inventory)
    }

    override fun onTapLockedNode(node: PuzzleNode) {
        Log.i(TAG, "onTapLockedNode $node")
        when (node) {
            is InventoryLockedNode -> {
                selectionTracker?.takeIf {
                    it.hasSelection()
                }?.selection?.let { selection ->
                    val inventory = selection.iterator().next()
                    node.unlock(inventory.unlockName)
                }
                viewModel.informNodeInventoryUnlock(
                    node.isLocked,
                    node.name,
                    node.unlockName,
                    node == doorNode
                )
            }
            is PinLockedNode -> {
                viewModel.informNodePinUnlock(node.unlockPin) {
                    node.unlock()
                }
            }
        }
    }
}