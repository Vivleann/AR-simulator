package com.google.ar.sceneform.samples.hellosceneform.ui.text_views

import android.content.Context
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.EditText
import com.google.ar.sceneform.samples.hellosceneform.R

class CustomEditText(
        context: Context,
        attrs: AttributeSet
) : EditText(context, attrs) {

    init {
        typeface = Typeface.createFromAsset(context.assets, "fonts/Gilroy-SemiBold.ttf")
        setTextColor(ContextCompat.getColor(context, R.color.color_black))
        background = ContextCompat.getDrawable(context, R.drawable.custom_edit_text)
    }

}