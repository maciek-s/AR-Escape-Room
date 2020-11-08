package com.masiad.arescaperoom.ui.ar

import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment

class ArCoreFragment : ArFragment() {

    override fun onResume() {
        super.onResume()
        arSceneView.planeRenderer.isEnabled = false
        setShowPlaneDiscoveryController(false)
    }

    override fun getSessionConfiguration(session: Session?): Config {
        return super.getSessionConfiguration(session).apply {
            lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
            planeFindingMode = Config.PlaneFindingMode.HORIZONTAL
            updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
            cloudAnchorMode = Config.CloudAnchorMode.DISABLED
            augmentedFaceMode = Config.AugmentedFaceMode.DISABLED
            focusMode = Config.FocusMode.AUTO
            instantPlacementMode = Config.InstantPlacementMode.DISABLED
            depthMode = Config.DepthMode.DISABLED
        }
    }

    fun setShowPlaneDiscoveryController(show: Boolean) {
        if (show) {
            planeDiscoveryController.show()
        } else {
            planeDiscoveryController.hide()
        }
    }
}