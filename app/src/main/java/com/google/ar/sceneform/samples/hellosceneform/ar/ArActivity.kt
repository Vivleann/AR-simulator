package com.google.ar.sceneform.samples.hellosceneform.ar

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import android.widget.Toast
import com.google.ar.core.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.*
import com.google.ar.sceneform.samples.hellosceneform.R
import com.google.ar.sceneform.samples.hellosceneform.server.model.Figure
import com.google.ar.sceneform.samples.hellosceneform.server.requests.PostFiguresRequest
import com.google.ar.sceneform.samples.hellosceneform.ui.SwipeContainer
import com.google.ar.sceneform.samples.hellosceneform.ui.SwipeContainerContentView
import com.google.ar.sceneform.samples.hellosceneform.ui.TimerView
import com.google.ar.sceneform.samples.hellosceneform.ui.WaitingFragment
import com.google.ar.sceneform.ux.ArFragment
import kotlinx.android.synthetic.main.activity_ux.*
import kotlin.concurrent.timer

class ArActivity : AppCompatActivity() {

    private val arHolder = ArHolder.instance
    private lateinit var arFragment: ArFragment
    private lateinit var sphereRenderable: Renderable
    private lateinit var cylinderRenderable: Renderable

    private lateinit var chooseView: SwipeContainer
    private var descriptionView: SwipeContainer? = null
    private var settingsView: SwipeContainer? = null
    private var timerView: TimerView? = null

    private var npcPlaced = false
    private var markerPlaces = false
    private val borders = arrayOf(
            Vector3(-.1f, .0f, -.1f),
            Vector3(-.1f, .0f, .1f),
            Vector3(.1f, .0f, .1f),
            Vector3(.1f, .0f, -.1f)
    )
    private val yRoatationVector = Vector3(0f, 1f, 0f)
    private var currentAngel = 90f

    private lateinit var defaultAnchor: Anchor
    private var currentSettingNode: Node? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!ArUtils.checkIsSupportedDeviceOrFinish(this))
            return

        setContentView(R.layout.activity_ux)

        setAr()

        ready_btn.setOnClickListener {
            ArHolder.instance.placeEnemyFigures(defaultAnchor, arFragment, this)
        }
    }

    private fun setAr() {
        if ((supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment?) != null)
            arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment

        arHolder.init(this)

        arFragment.arSceneView.scene.addOnUpdateListener {
            val frame = arFragment.arSceneView.arFrame
            if (frame != null) {
                processPlane(frame)
            }
        }
    }

    private fun processPlane(frame: Frame) {
        val planes = frame.getUpdatedTrackables(Plane::class.java)
        for (plane in planes) {
            if (plane.trackingState == TrackingState.TRACKING) {
                if (!npcPlaced) {
                    val anchor = plane.createAnchor(plane.centerPose)
                    defaultAnchor = anchor
                    arHolder.placeField(anchor, arFragment, this)
                    npcPlaced = true
                    openChooseView()
                    openTimer()
                }
                break
            }
        }
    }

    private fun openTimer() {
        timerView = TimerView(this)
        content.addView(timerView)
    }

    fun closeTimer() {
        content.removeView(timerView)
        timerView = null
        Toast.makeText(this, "Time left. Game is starting now", Toast.LENGTH_LONG).show()
    }

    private fun openChooseView() {
        Handler(Looper.getMainLooper()).postDelayed({
            chooseView = SwipeContainer(this)
            chooseView.init(SwipeContainerContentView.CHOOSE_VIEW)
            val params = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
            )
            chooseView.layoutParams = params
            content.addView(chooseView)
            chooseView.appear()
        }, 300)
    }

    fun collapseChooseView() {
        chooseView.collapse()
    }

    fun openDescription() {
        descriptionView = SwipeContainer(this)
        descriptionView!!.init(SwipeContainerContentView.DESCRIPTION_VIEW)
        val params = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        )
        descriptionView!!.layoutParams = params
        content.addView(descriptionView)
        descriptionView!!.appear()
    }

    fun openSettingsView() {
        settingsView = SwipeContainer(this)
        settingsView!!.init(SwipeContainerContentView.SETTINGS_VIEW)
        val params = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        )
        settingsView!!.layoutParams = params
        content.addView(settingsView)
        settingsView!!.appear()
    }

    fun removeDescription() {
        content.removeView(descriptionView)
        descriptionView = null
    }

    fun removeSettingsView() {
        content.removeView(settingsView)
        settingsView = null
    }

    fun postFigures() {
        val objs = ArHolder.instance.objList
        val res = ArrayList<Figure>()
        for (item in objs) {
            val angle = when (item.currentRotation) {
                0f -> 0
                90f -> 1
                180f -> 2
                else -> 3
            }
            val id = when (item.id) {
                0 -> 1
                1 -> 3
                2 -> 5
                3 -> 6
                else -> 7
            }
            res.add(Figure(
                    item.i,
                    item.j,
                    angle,
                    id
            ))
        }
        PostFiguresRequest("app", res).execute()
    }

    private fun placeSphere(anchor: Anchor) {
        val containerNode = AnchorNode(anchor)
        containerNode.setParent(arFragment.arSceneView.scene)
        val node = Node()
        node.setParent(containerNode)
        node.renderable = sphereRenderable
        node.localPosition = Vector3(.4f, 0f, .2f)
        //animateNpc(node)
    }



    private fun animateNpc(node: Node) {
        val animator = ValueAnimator.ofFloat(node.localPosition.x, node.localPosition.x * -1)
        animator.duration = 4000
        animator.addUpdateListener {animation ->
            val value = animation.animatedValue as Float
            val nextVector = Vector3(value, node.localPosition.y, node.localPosition.z)
            node.localPosition = nextVector

        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                animateRotation(node)
                Handler().postDelayed({
                    animateNpc(node)
                }, 701)
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })
        animator.start()
    }

    fun animateRotation(node: Node) {
        val from = currentAngel
        currentAngel += 180f
        if (currentAngel > 360f)
            currentAngel -= 360 * (currentAngel / 360).toInt()
        val animator = ValueAnimator.ofFloat(from, currentAngel)
        animator.duration = 700
        animator.addUpdateListener {
            node.localRotation = Quaternion.axisAngle(yRoatationVector, it.animatedValue as Float)
        }
        animator.start()
    }

    private fun animate(node: Node, direction: Boolean) {
        val a = if (direction) 0.0f else 1.0f
        val animator = ValueAnimator.ofFloat(a, 1 - a)
        animator.duration = 5000
        animator.startDelay = (Math.random() * 1000).toLong()
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            node.localPosition = Vector3(value, node.localPosition.y, node.localPosition.z)
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                animate(node, !direction)
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }

        })
        animator.start()
    }

}