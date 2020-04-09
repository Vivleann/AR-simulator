package com.google.ar.sceneform.samples.hellosceneform

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MotionEvent
import android.widget.Toast
import com.google.ar.core.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.*
import com.google.ar.sceneform.samples.hellosceneform.ar.ArUtils
import com.google.ar.sceneform.samples.hellosceneform.ui.LoginFragment
import com.google.ar.sceneform.samples.hellosceneform.ui.WaitingFragment
import com.google.ar.sceneform.ux.ArFragment
import java.lang.Exception

class ArActivity : AppCompatActivity() {

    private var waitingFragment: WaitingFragment? = null

    private lateinit var arFragment: ArFragment
    private var andyRenderable: ModelRenderable? = null
    private lateinit var sphereRenderable: Renderable
    private lateinit var cylinderRenderable: Renderable
    private lateinit var cubeRenderable: Renderable

    private var npcPlaced = false
    private var markerPlaces = false
    private val borders = arrayOf(
            Vector3(-.4f, .0f, -.4f),
            Vector3(-.4f, .0f, .4f),
            Vector3(.4f, .0f, .4f),
            Vector3(.4f, .0f, -.4f)
    )
    private val yRoatationVector = Vector3(0f, 1f, 0f)
    private var currentAngel = 90f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!ArUtils.checkIsSupportedDeviceOrFinish(this))
            return

        setContentView(R.layout.activity_ux)

        if (PreferencesUtils.getString(PreferencesUtils.LOGIN_KEY) != "" &&
                PreferencesUtils.getString(PreferencesUtils.PASSWORD_KEY) != "") {
            //todo: start game
            openWaitingFragment(WaitingFragment.GAME_START)
        } else {
            changeFragment(LoginFragment(), R.id.main_content)
        }


//        if ((supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment?) != null)
//            arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment


    }

    fun onGameConnected() {
        waitingFragment!!.dismiss()
        waitingFragment = null
        arFragment = ArFragment()
        changeFragment(arFragment, R.id.main_content)
        setAr()
    }

    private fun setAr() {
        initFieldCube()
        initSphere()
        initNpc()

        arFragment.arSceneView.scene.addOnUpdateListener {
            val frame = arFragment.arSceneView.arFrame
            if (frame != null) {
                //processPlane(frame)
                //processAugmentedImage(frame)
            }
        }

        arFragment.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane, motionEvent: MotionEvent ->
            val anchor = hitResult.createAnchor()
            if (!npcPlaced) {
                //placeCylinder(anchor)
                placeField(anchor)
                npcPlaced = true
            }
        }
    }

    fun changeFragment(fragment: Fragment, frame: Int) {
        try {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(frame, fragment)
            fragmentTransaction.commit()
        } catch (e: Exception) {
            AndroidUtils.showProblemToast(this)
            println(e)
        }
    }

    fun openWaitingFragment(title: String) {
        waitingFragment = WaitingFragment(title)
        waitingFragment!!.show(supportFragmentManager, WaitingFragment.TAG)
    }

    private fun processPlane(frame: Frame) {
        val planes = frame.getUpdatedTrackables(Plane::class.java)
        for (plane in planes) {
            if (plane.trackingState == TrackingState.TRACKING) {
                if (!npcPlaced) {
                    val anchor = plane.createAnchor(plane.centerPose)
                    //placeCylinder(anchor)
                    npcPlaced = true
                }
                break
            }
        }
    }

    private fun processAugmentedImage(frame: Frame) {
        val images = frame.getUpdatedTrackables(AugmentedImage::class.java)
        for (img in images) {
            if (img.trackingState == TrackingState.TRACKING) {
                if (img.name == "field" && !markerPlaces) {
                    val anchor = img.createAnchor(img.centerPose)
                    Toast.makeText(this, "${anchor.pose.tx()}\n${anchor.pose.ty()}\n${anchor.pose.tz()}", Toast.LENGTH_LONG).show()
                    markerPlaces = true
                }
            }
        }
    }

    private fun initSphere() {
        val color = Color(0x00FF00)
        val radius = .05f
        val center = Vector3(0.0f, 0.0f, 0.0f)
        MaterialFactory.makeOpaqueWithColor(this, color)
                .thenAccept {
                   material -> sphereRenderable = ShapeFactory.makeSphere(radius, center, material)
                }
    }

    private fun initCylinder() {
        val color = Color(-0x10000)
        val radius = .05f
        val height = .3f
        val center = Vector3(0.0f, 0.0f, 0.0f)
        MaterialFactory.makeOpaqueWithColor(this, color)
                .thenAccept {
                    material -> cylinderRenderable = ShapeFactory.makeCylinder(radius, height, center, material)
                }
    }

    private fun initFieldCube() {
        val color = Color(AndroidUtils.adjustAlpha(-0xffff01, .5f))
        val size = Vector3(.8f, 0f, .8f)
        MaterialFactory.makeOpaqueWithColor(this, color)
                .thenAccept {
                    material -> cubeRenderable = ShapeFactory.makeCube(size, Vector3.zero(), material)
                }
    }

    private fun initNpc() {
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

    private fun placeSphere(anchor: Anchor) {
        val containerNode = AnchorNode(anchor)
        containerNode.setParent(arFragment.arSceneView.scene)
        val node = Node()
        node.setParent(containerNode)
        node.renderable = sphereRenderable
        node.localPosition = Vector3(.4f, 0f, .2f)
        //animateNpc(node)
    }

    private fun placeCylinder(anchor: Anchor) {
        for (i in 0 until 4) {
            val containerNode = AnchorNode(anchor)
            containerNode.setParent(arFragment.arSceneView.scene)
            val node = Node()
            node.setParent(containerNode)
            node.renderable = cylinderRenderable
            node.localPosition = borders[i]
        }
        placeSphere(anchor)
    }

    private fun placeField(anchor: Anchor) {
        val containerNode = AnchorNode(anchor)
        containerNode.setParent(arFragment.arSceneView.scene)
        val node = Node()
        node.setParent(containerNode)
        node.renderable = cubeRenderable
        node.localPosition = Vector3.zero()
        placeNpc(anchor)
    }

    private fun placeNpc(anchor: Anchor) {
        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(arFragment.arSceneView.scene)
        val andy = Node()
        andy.setParent(anchorNode)
        andy.localPosition = borders[0]
        andy.renderable = andyRenderable
        andy.localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), currentAngel)
        animateNpc(andy)
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

    private fun animateRotation(node: Node) {
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