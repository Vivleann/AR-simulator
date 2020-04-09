package com.google.ar.sceneform.samples.hellosceneform.ui.text_views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

class GilroyMedium(
        context: Context,
        attrs: AttributeSet
) : TextView(context, attrs) {

    init {
        typeface = Typeface.createFromAsset(context.assets, "fonts/Gilroy-Medium.ttf")
    }

}