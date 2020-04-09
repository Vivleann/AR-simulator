package com.google.ar.sceneform.samples.hellosceneform.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.google.ar.sceneform.samples.hellosceneform.R

class ChooseView(
        context: Context,
        attrs: AttributeSet
) : FrameLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.choose_view, this, true)
    }

}