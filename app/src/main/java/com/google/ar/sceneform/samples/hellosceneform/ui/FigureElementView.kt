package com.google.ar.sceneform.samples.hellosceneform.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import com.google.ar.sceneform.samples.hellosceneform.R
import com.google.ar.sceneform.samples.hellosceneform.android.AndroidUtils
import com.google.ar.sceneform.samples.hellosceneform.android.Database
import com.google.ar.sceneform.samples.hellosceneform.ar.ArActivity
import com.google.ar.sceneform.samples.hellosceneform.ar.ArHolder
import kotlinx.android.synthetic.main.choose_element_view.view.*

class FigureElementView(
        context: Context,
        attrs: AttributeSet
) : FrameLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.choose_element_view, this, true)

        val id = Database.instance.getId()

        Log.d("FigureElementView", "ID == $id")

        img.setImageDrawable(AndroidUtils.getAssetsIcon("planet$id.png", context))

        setOnLongClickListener {
            Database.instance.currentId = id
            (context as ArActivity).openDescription()
            true
        }

        setOnClickListener {
            Database.instance.currentId = id
            (context as ArActivity).collapseChooseView()
            Toast.makeText(context, "Place the figure", Toast.LENGTH_LONG).show()
        }
    }

}