package com.google.ar.sceneform.samples.hellosceneform.ar.npc

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.samples.hellosceneform.R

class Npc (
        private val context: Context
) {

    val ROTATE_TIME = 700L
    val MOVING_TIME = 4000L

    private var npcRotation = NpcRotation()
    private lateinit var andyRenderable: Renderable
    private val yRoatationVector = Vector3(0f, 1f, 0f)
    private val node = Node()

    init {
        initNpcModel()
    }

    fun place(anchor: Anchor, scene: Scene) {
        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(scene)
        node.setParent(anchorNode)
        node.localPosition = npcRotation.getFirstPosition()
        node.renderable = andyRenderable
        processRotation()
    }

    private fun processRotation() {
        val animator = ValueAnimator.ofFloat(npcRotation.getCurrentAngel(), npcRotation.getNewAngel())
        animator.addUpdateListener {
            node.localRotation = Quaternion.axisAngle(yRoatationVector, it.animatedValue as Float)
        }
        animator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }
            override fun onAnimationEnd(animation: Animator?) {
                processMoving()
            }
            override fun onAnimationCancel(animation: Animator?) {
            }
            override fun onAnimationStart(animation: Animator?) {
            }
        })
        animator.duration = ROTATE_TIME
        animator.start()
    }

    private fun processMoving() {
        val animator = ValueAnimator.ofFloat(node.localPosition.x, node.localPosition.x * -1)
        animator.addUpdateListener {animation ->
            val value = animation.animatedValue as Float
            val nextVector = Vector3(value, node.localPosition.y, node.localPosition.z)
            node.localPosition = nextVector

        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }
            override fun onAnimationEnd(animation: Animator?) {
                processRotation()
            }
            override fun onAnimationCancel(animation: Animator?) {

            }
            override fun onAnimationStart(animation: Animator?) {

            }

        })
        animator.duration = MOVING_TIME
        animator.start()
    }

    private fun initNpcModel() {
        ModelRenderable.builder()
                .setSource(context, R.raw.andy)
                .build()
                .thenAccept { renderable -> andyRenderable = renderable }
                .exceptionally { throwable ->
                    val toast = Toast.makeText(context, "Unable to load andy renderable", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                    null
                }
    }

}