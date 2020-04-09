package com.google.ar.sceneform.samples.hellosceneform.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.google.ar.sceneform.samples.hellosceneform.R
import com.google.ar.sceneform.samples.hellosceneform.android.AndroidUtils
import com.google.ar.sceneform.samples.hellosceneform.android.Database
import com.google.ar.sceneform.samples.hellosceneform.ar.ArActivity
import kotlinx.android.synthetic.main.choose_element_view.view.*

class FigureElementView(
        context: Context,
        attrs: AttributeSet
) : FrameLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.choose_element_view, this, true)
        setIcon()

//        setOnLongClickListener {
//            (context as ArActivity)
//            true
//        }
    }

    private fun setIcon() {
        val url = Database.instance.getPolygonIcPrefix()
        img.setImageDrawable(AndroidUtils.getAssetsIcon(url, context))
    }

}