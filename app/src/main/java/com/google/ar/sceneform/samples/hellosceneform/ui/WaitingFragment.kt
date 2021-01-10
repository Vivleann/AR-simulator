package com.google.ar.sceneform.samples.hellosceneform.ui

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.ar.sceneform.samples.hellosceneform.R
import kotlinx.android.synthetic.main.fragment_waiting.*

@SuppressLint("ValidFragment")
class WaitingFragment(
        private val title: String
) : DialogFragment() {

    companion object {
        const val TAG = "WaitingFragment"
        const val GAME_START = "Waiting for the start of the game..."
        const val WAIT_APPONENT = "Waiting for the apponent..."
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_waiting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title_text.text = title
        spinPlanet()
        Handler(Looper.getMainLooper()).postDelayed({
            (context as MainActivity).onGameConnected()
        }, 1500)
    }

    private fun spinPlanet() {
        val animator = ValueAnimator.ofFloat(0f, 360f)
        animator.addUpdateListener {
            val value = it.animatedValue as Float
            planet.rotation = value
        }
        animator.duration = 2000
        animator.repeatCount = ValueAnimator.INFINITE
        animator.repeatMode = ValueAnimator.RESTART
        animator.start()
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context!!, R.color.color_white)))
        }
    }

}
