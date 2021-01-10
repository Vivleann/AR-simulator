package com.google.ar.sceneform.samples.hellosceneform.ui

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.google.ar.sceneform.samples.hellosceneform.R
import com.google.ar.sceneform.samples.hellosceneform.android.AndroidUtils
import com.google.ar.sceneform.samples.hellosceneform.android.Database
import kotlinx.android.synthetic.main.about_view.view.*

class DescriptionView(
        context: Context
) : FrameLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.about_view, this, true)

        val id = Database.instance.currentId
        fg_img.setImageDrawable(AndroidUtils.getAssetsIcon("planet$id.png", context))

        val descriptions = Database.instance.descriptions
        fg_title.text = "${descriptions[id].title}"
        fg_dis.text = "${descriptions[id].body}"
    }

}