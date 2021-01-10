package com.google.ar.sceneform.samples.hellosceneform.ar

import android.animation.ValueAnimator
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.widget.Toast
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.HitTestResult
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.samples.hellosceneform.R
import com.google.ar.sceneform.samples.hellosceneform.android.Database
import com.google.ar.sceneform.ux.ArFragment

class ArObject(
        val id: Int,
        val i: Int,
        val j: Int,
        var node: Node?,
        var currentRotation: Float,
        private val activity: ArActivity
) {

    private val yRoatationVector = Vector3(0f, 1f, 0f)
    private var andyRenderable: ModelRenderable? = null

    private lateinit var scene: Scene
    private lateinit var anchorNode: AnchorNode


    init {
        val renderables = ArHolder.instance.objectRenderables
        andyRenderable = renderables[id]
    }

    fun rotate(angle: Float) {
        val animator = ValueAnimator.ofFloat(currentRotation, angle)
        animator.addUpdateListener {
            node!!.localRotation = Quaternion.axisAngle(yRoatationVector, it.animatedValue as Float)
        }
        animator.duration = 500
        animator.start()
        currentRotation = angle
    }

    fun placeObject(position: Vector3, anchor: Anchor, arFragment: ArFragment) {
        this.scene = arFragment.arSceneView.scene

        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(scene)
        this.anchorNode = anchorNode

        node!!.setOnTapListener(object : Node.OnTapListener {
            override fun onTap(hitResult: HitTestResult?, event: MotionEvent?) {
                arFragment.onPeekTouch(hitResult, event)

                if (hitResult != null) {
                    ArHolder.instance.currentSettingObj = this@ArObject
                    activity.openSettingsView()
                }
            }

        })

        node!!.setParent(anchorNode)
        node!!.localPosition = position
        node!!.renderable = andyRenderable
        node!!.localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), currentRotation)
    }

    fun removeObject() {
        Database.instance.figuresLeft++
        scene.removeChild(anchorNode)
        ArHolder.instance.currentSettingObj = null
        ArHolder.instance.getObjects().remove(Pair(i, j))
        ArHolder.instance.objList.remove(this)
    }

}