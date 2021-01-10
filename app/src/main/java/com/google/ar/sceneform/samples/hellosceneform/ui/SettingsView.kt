package com.google.ar.sceneform.samples.hellosceneform.ui

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.google.ar.sceneform.samples.hellosceneform.R
import com.google.ar.sceneform.samples.hellosceneform.ar.ArActivity
import com.google.ar.sceneform.samples.hellosceneform.ar.ArHolder
import kotlinx.android.synthetic.main.figure_settings_view.view.*

class SettingsView(
        context: Context
) : FrameLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.figure_settings_view, this, true)

        val holder = ArHolder.instance

        btn_0.setOnClickListener {
            holder.currentSettingObj!!.rotate(0f)
        }

        btn_90.setOnClickListener {
            holder.currentSettingObj!!.rotate(90f)
        }

        btn_180.setOnClickListener {
            holder.currentSettingObj!!.rotate(180f)
        }

        btn_270.setOnClickListener {
            holder.currentSettingObj!!.rotate(270f)
        }

        delete_btn.setOnClickListener {
            holder.currentSettingObj!!.removeObject()
        }

    }

}