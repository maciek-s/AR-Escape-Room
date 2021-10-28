package com.masiad.arescaperoom.ui.ar

import android.os.Bundle
import android.view.View
import com.google.ar.core.CameraConfig
import com.google.ar.core.CameraConfigFilter
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment
import java.util.*

class ArCoreFragment : ArFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arSceneView.scene.sunlight?.isEnabled = false
    }

    override fun onResume() {
        super.onResume()
        arSceneView.planeRenderer.isEnabled = false
        setShowPlaneDiscoveryController(false)
    }



//    override fun getSessionConfiguration(session: Session?): Config {
//        val cameraFilter = CameraConfigFilter(session)
//        cameraFilter.targetFps = EnumSet.of(CameraConfig.TargetFps.TARGET_FPS_30)
//        cameraFilter.depthSensorUsage = EnumSet.of(CameraConfig.DepthSensorUsage.DO_NOT_USE)
//        cameraFilter.stereoCameraUsage = EnumSet.of(CameraConfig.StereoCameraUsage.DO_NOT_USE)
//        session?.getSupportedCameraConfigs(cameraFilter)?.let {
//            session.cameraConfig = it[0]
//        }
//        return super.getSessionConfiguration(session).apply {
//            lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
//            planeFindingMode = Config.PlaneFindingMode.HORIZONTAL
//            updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
//            cloudAnchorMode = Config.CloudAnchorMode.DISABLED
//            augmentedFaceMode = Config.AugmentedFaceMode.DISABLED
//            focusMode = Config.FocusMode.AUTO
//            instantPlacementMode = Config.InstantPlacementMode.DISABLED
//            depthMode = Config.DepthMode.DISABLED
//        }
//    }

    fun setShowPlaneDiscoveryController(show: Boolean) {
//        if (show) {
//            planeDiscoveryController.show()
//        } else {
//            planeDiscoveryController.hide()
//        }
    }
}