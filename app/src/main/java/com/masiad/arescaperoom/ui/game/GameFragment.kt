package com.masiad.arescaperoom.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.ar.core.Plane
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.*
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.Material
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ShapeFactory
import com.masiad.arescaperoom.R
import com.masiad.arescaperoom.databinding.GameFragmentBinding
import com.masiad.arescaperoom.gamelogic.GamePhase
import com.masiad.arescaperoom.ui.ar.ArCoreFragment
import com.masiad.arescaperoom.util.model.ModelLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Main AR game fragment
 */
@AndroidEntryPoint
class GameFragment : Fragment(R.layout.game_fragment), Scene.OnUpdateListener {

    private val args: GameFragmentArgs by navArgs()

    @Inject
    lateinit var modelLoader: ModelLoader

    private lateinit var binding: GameFragmentBinding

    private val viewModel: GameViewModel by viewModels()

    // UI
    private val arCoreFragment: ArCoreFragment
        get() = childFragmentManager.findFragmentById(R.id.ar_core_fragment) as ArCoreFragment

    private val arSceneView: ArSceneView
        get() = arCoreFragment.arSceneView

    private val rootNode by lazy { AnchorNode() }
    private val placingNode by lazy { Node() }

    private val hitPosition by lazy {
        Pair(arSceneView.width / 2f, arSceneView.height / 2f)
    }

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

    override fun onDestroy() {
        arSceneView.scene.removeOnUpdateListener(this)
        super.onDestroy()
    }

    override fun onUpdate(frameTime: FrameTime?) {
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

    // PRIVATE
    private fun bindData() {
        binding.viewModel = viewModel
        observeGamePhase()
    }

    private fun observeGamePhase() {
        viewModel.gamePhase.observe(viewLifecycleOwner, { gamePhase: GamePhase? ->
            when (gamePhase) {
                GamePhase.LOADING -> prepareGame()
                GamePhase.GAME_LOADED -> showInstruction()
                GamePhase.PLACING -> setupPlacing()
                GamePhase.PLACED -> TODO()
                GamePhase.GAME_STARTED -> TODO()
                GamePhase.ESCAPING -> TODO()
                GamePhase.ESCAPED -> TODO()
            }
        })
    }

    private fun prepareGame() {
        viewModel.loadLevel(args.levelNumber)
    }

    private fun showInstruction() {
        binding.alert.infoText = "Test sdm flsd msd fmsdl fmlsd mlsd mklfmksd lmls"
        binding.alert.clickListener = View.OnClickListener {
            lifecycleScope.launch {
                delay(2000)
                binding.alert.infoText = null
                viewModel.informInstructionAlertClosed()
            }
        }
    }

    private fun setupPlacing() {
        placingNode.setParent(rootNode)
        // show doors preview and create portal on tap
        MaterialFactory.makeOpaqueWithColor(context, Color(1f, 0f, 0f))
            .thenAccept { material: Material? ->
                placingNode.renderable =
                    ShapeFactory.makeSphere(0.01f, Vector3.zero(), material)
            }


        arSceneView.scene.addOnUpdateListener(this)
    }
}