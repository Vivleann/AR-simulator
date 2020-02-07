package com.google.ar.sceneform.samples.hellosceneform

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MotionEvent
import android.widget.Toast
import com.google.ar.core.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class ArActivity : AppCompatActivity() {

    private lateinit var arFragment: ArFragment
    private var andyRenderable: ModelRenderable? = null

    private var npcPlaced = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!ArUtils.checkIsSupportedDeviceOrFinish(this))
            return

        setContentView(R.layout.activity_ux)

        if ((supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment?) != null)
            arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment

        loadNpc()

        arFragment.arSceneView.scene.addOnUpdateListener {
            val frame = arFragment.arSceneView.arFrame
            if (frame != null) {
//                val planes = frame.getUpdatedTrackables(Plane::class.java)
//                for (plane in planes) {
//                    if (plane.trackingState == TrackingState.TRACKING) {
//                        if (!npcPlaced) {
//                            val anchor = plane.createAnchor(plane.centerPose)
//                            createNpc(anchor)
//                            npcPlaced = true
//                        }
//                        break
//                    }
//                }

                val images = frame.getUpdatedTrackables(AugmentedImage::class.java)
                for (img in images) {
                    if (img.trackingState == TrackingState.TRACKING) {
                        if (img.name == "border_marker") {
                            val anchor = img.createAnchor(img.centerPose)
                            createNpc(anchor)
                        }
                    }
                }
            }
        }

        arFragment.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane, motionEvent: MotionEvent ->
            val anchor = hitResult.createAnchor()
            createNpc(anchor)
        }
    }

    private fun createNpc(anchor: Anchor) {
        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(arFragment.arSceneView.scene)

        val andy = TransformableNode(arFragment.transformationSystem)
        andy.setParent(anchorNode)
        andy.renderable = andyRenderable
        andy.select()
    }

    private fun loadNpc() {
        ModelRenderable.builder()
                .setSource(this, R.raw.andy)
                .build()
                .thenAccept { renderable -> andyRenderable = renderable }
                .exceptionally { throwable ->
                    val toast = Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                    null
                }
    }

    fun setUpDatabase(config: Config, session: Session) {
        val border = BitmapFactory.decodeResource(resources, R.drawable.border_marker_2)
        val aid = AugmentedImageDatabase(session)
        aid.addImage("border_marker", border)
        config.augmentedImageDatabase = aid
    }



}