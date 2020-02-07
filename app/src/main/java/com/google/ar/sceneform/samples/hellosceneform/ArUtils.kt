package com.google.ar.sceneform.samples.hellosceneform

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.google.ar.core.Anchor

object ArUtils {

    private val TAG = "AR_UTILS"
    private const val MIN_OPENGL_VERSION = 3.0

    internal fun getMetersBetweenAnchors(anchor1: Anchor, anchor2: Anchor): Float {
        val distanceVector = anchor1.pose.inverse()
                .compose(anchor2.pose).translation
        var totalDistanceSquared = 0f
        for (i in 0..2)
            totalDistanceSquared += distanceVector[i] * distanceVector[i]
        return Math.sqrt(totalDistanceSquared.toDouble()).toFloat()
    }

    fun checkIsSupportedDeviceOrFinish(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later")
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show()
            activity.finish()
            return false
        }
        val openGlVersionString = (activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                .deviceConfigurationInfo
                .glEsVersion
        if (java.lang.Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later")
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show()
            activity.finish()
            return false
        }
        return true
    }

}