package com.google.ar.sceneform.samples.hellosceneform.ui

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.google.ar.sceneform.samples.hellosceneform.R
import com.google.ar.sceneform.samples.hellosceneform.ar.ArActivity
import kotlinx.android.synthetic.main.timer_view.view.*
import java.util.*

class TimerView(
        context: Context
) : FrameLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.timer_view, this, true)

        var left = 60

        Timer().scheduleAtFixedRate(object : TimerTask(){
            override fun run() {
                (context as ArActivity).runOnUiThread {
                    left--

                    if (left == -1) {
                        (context as ArActivity).closeTimer()
                    }

                    time.text = "Time left: ${left}s."
                }
            }
        }, 0, 1000)
    }

}