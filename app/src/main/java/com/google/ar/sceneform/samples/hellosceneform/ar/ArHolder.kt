package com.google.ar.sceneform.samples.hellosceneform.ar

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
import com.google.ar.sceneform.rendering.*
import com.google.ar.sceneform.samples.hellosceneform.R
import com.google.ar.sceneform.samples.hellosceneform.android.AndroidUtils
import com.google.ar.sceneform.samples.hellosceneform.android.Database
import com.google.ar.sceneform.ux.ArFragment

class ArHolder {

    private val objects = HashMap<Pair<Int, Int>, ArObject?>()
    private var andyRenderable: ModelRenderable? = null
    val objectRenderables = ArrayList<ModelRenderable>()
    private lateinit var cubeRenderable1: Renderable
    private lateinit var cubeRenderable2: Renderable
    private lateinit var cubeRenderable3: Renderable
    var currentSettingObj: ArObject? = null
    val objList = ArrayList<ArObject>()

    private val rotations = arrayOf(
            0f, 90f, 180f, 270f
    )

    private val modelSrces = arrayOf(
            R.raw.base_1,
            R.raw.base_3,
            R.raw.base_5,
            R.raw.base_6,
            R.raw.base_7
    )

    private val enemyPositions = arrayOf(
            Pair(6, 6),
            Pair(7, 5),
            Pair(7, 4),
            Pair(5, 3)
    )

    fun removeObj() {
        currentSettingObj = null
    }

    fun init(activity: ArActivity) {
        initObject(activity)
        initFieldCube1(activity)
        initFieldCube2(activity)
        initFieldCube3(activity)
    }

    fun placeEnemyFigures(anchor: Anchor, arFragment: ArFragment, activity: ArActivity) {
        for (i in 0..3) {
            val y = enemyPositions[i].second
            val x = enemyPositions[i].first
            placeObject(
                    Vector3(y * 0.2f, 0f, x * 0.2f),
                    anchor,
                    arFragment,
                    rotations[(0..3).random()]
            )
        }
    }

    fun placeObject(position: Vector3, anchor: Anchor, arFragment: ArFragment, rotation: Float) {
        val scene = arFragment.arSceneView.scene

        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(scene)

        val node = Node()
        node.setParent(anchorNode)
        node.localPosition = position
        node.renderable = objectRenderables[(0..4).random()]
        node.localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), rotation)
    }

    fun createObject(i: Int, j: Int, activity: ArActivity, vector3: Vector3, anchor: Anchor, arFragment: ArFragment) {
        val db = Database.instance
        if (db.figuresLeft > 0) {
            db.figuresLeft--
            if (db.figuresLeft == 0)
                Toast.makeText(activity, "You have rich max figures amount", Toast.LENGTH_LONG).show()

            val arObject = ArObject(
                    Database.instance.currentId,
                    i,
                    j,
                    Node(),
                    90f,
                    activity
            )

            objList.add(arObject)
            objects[Pair(i, j)] = arObject
            arObject.placeObject(vector3, anchor, arFragment)

        } else
            Toast.makeText(activity, "You have rich max figures amount", Toast.LENGTH_LONG).show()
    }

    private fun initFieldCube1(activity: ArActivity) {
        val color = Color(AndroidUtils.adjustAlpha(-0xffff01, .5f))
        val size = Vector3(.15f, 0f, .15f)
        MaterialFactory.makeOpaqueWithColor(activity, color)
                .thenAccept {
                    material -> cubeRenderable1 = ShapeFactory.makeCube(size, Vector3.zero(), material)
                }
    }

    private fun initFieldCube2(activity: ArActivity) {
        val color = Color(AndroidUtils.adjustAlpha(-0xff01, .5f))
        val size = Vector3(.15f, 0f, .15f)
        MaterialFactory.makeOpaqueWithColor(activity, color)
                .thenAccept {
                    material -> cubeRenderable2 = ShapeFactory.makeCube(size, Vector3.zero(), material)
                }
    }

    private fun initFieldCube3(activity: ArActivity) {
        val color = Color(AndroidUtils.adjustAlpha(-0x1000000, .5f))
        val size = Vector3(.15f, 0f, .15f)
        MaterialFactory.makeOpaqueWithColor(activity, color)
                .thenAccept {
                    material -> cubeRenderable3 = ShapeFactory.makeCube(size, Vector3.zero(), material)
                }
    }

    private fun initObject(activity: ArActivity) {
        for (modelSrc in modelSrces) {
            ModelRenderable.builder()
                    .setSource(activity, modelSrc)
                    .build()
                    .thenAccept { renderable ->
                        objectRenderables.add(renderable)
                    }
                    .exceptionally { throwable ->
                        val toast = Toast.makeText(activity, "Unable to load andy renderable", Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                        null
                    }
        }
    }

    fun placeField(anchor: Anchor, arFragment: ArFragment, activity: ArActivity) {
        val containerNode = AnchorNode(anchor)
        containerNode.setParent(arFragment.arSceneView.scene)
        for (i in 0 until 8) {
            for (j in 0 until 10) {
                if ((i == 3 && j == 0) || (j == 9 && i == 5)) {
                    val node = CustomeNode(i, j)
                    node.setParent(containerNode)
                    node.renderable = cubeRenderable3
                    node.localPosition = Vector3(i * 0.2f, 0f, j * 0.2f)
                } else {
                    val node = CustomeNode(i, j)
                    node.setOnTapListener(object : Node.OnTapListener {
                        override fun onTap(hitResult: HitTestResult?, event: MotionEvent?) {
                            if (hitResult != null) {
                                val n = hitResult.node as CustomeNode
                                if (n.j < 5)
                                    createObject(n.i, n.j, activity, n.localPosition, anchor, arFragment)
                                else
                                    Toast.makeText(activity, "You can't place your figures here!", Toast.LENGTH_LONG).show()
                            }
                        }

                    })
                    node.setParent(containerNode)
                    node.renderable = if (j < 5) cubeRenderable1 else cubeRenderable2
                    node.localPosition = Vector3(i * 0.2f, 0f, j * 0.2f)
                }
            }
        }
    }

    fun getObjectRenderable(): ModelRenderable? {
        return andyRenderable
    }

    fun getObjects(): HashMap<Pair<Int, Int>, ArObject?> {
        return objects
    }

    private object Holder {
        val INSTANCE = ArHolder()
    }

    companion object {
        val instance: ArHolder by lazy { Holder.INSTANCE }
    }

}