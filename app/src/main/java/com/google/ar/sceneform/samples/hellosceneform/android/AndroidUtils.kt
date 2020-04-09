package com.google.ar.sceneform.samples.hellosceneform

import android.content.Context
import android.graphics.Color
import android.support.annotation.ColorInt
import android.widget.Toast

object AndroidUtils {

    @ColorInt
    fun adjustAlpha(@ColorInt color: Int, factor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * factor)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }

    fun showProblemToast(context: Context) {
        Toast.makeText(context, "Something went wrong\nPlease try again", Toast.LENGTH_LONG).show()
    }

}