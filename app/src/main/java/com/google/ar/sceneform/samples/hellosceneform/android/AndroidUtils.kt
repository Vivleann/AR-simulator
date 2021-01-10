package com.google.ar.sceneform.samples.hellosceneform.android

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.Toast
import java.io.IOException

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

    fun getAssetsIcon(name: String, context: Context): Drawable? {
        try {
            val ims = context.assets.open("icons/$name")
            return Drawable.createFromStream(ims, null)
        } catch (ex: IOException) {
            AndroidUtils.showProblemToast(context)
            println(ex)
        }
        return null
    }

    fun getScreenHeight(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun dpToPx(context: Context, dip: Float): Float {
        val r = context.resources
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.displayMetrics
        )
    }

}