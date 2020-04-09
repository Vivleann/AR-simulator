package com.google.ar.sceneform.samples.hellosceneform.npc

import com.google.ar.sceneform.math.Vector3
import java.util.*

class NpcRotation {

    enum class CoorType {
        X, Z
    }

    private val xStart = -.4f
    private val xEnd = .4f
    private val zStart = -.4f
    private val zEnd = .4f
    private val angels = floatArrayOf(0f, 90f, 180f, 270f)
    private val xAxis = angels // change
    private val zAxis = angels // change
    private var currentAngel = 0f
    private var currentCoor = CoorType.X
    private var finalCoorValue = 0f

//    fun updateRotation() {
//        when (currentAngel) {
//            0f -> {
//                currentCoor = CoorType.Z
//                finalCoorValue =
//            }
//        }
//    }
//
//    private fun getXAxisValue(): Float {
//
//    }
//
//    private fun getZAxisValue(): Float {
//
//    }

    fun getCurrentCoor(): CoorType {
        return currentCoor
    }

    fun getNewAngel(): Float {
        currentAngel = angels[(0..3).random()]
        return currentAngel
    }

    fun getCurrentAngel(): Float {
        return currentAngel
    }

    fun getFirstPosition(): Vector3 {
        return Vector3(getRandomFloat(xStart, xEnd), 0f, getRandomFloat(zStart, zEnd))
    }

    private fun getRandomFloat(min: Float, max: Float): Float {
        //todo: generate arr and choose random from it
        return Random().nextFloat() * (max - min) + max
    }
}