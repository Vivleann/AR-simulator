package com.google.ar.sceneform.samples.hellosceneform.ui

import android.animation.Animator
import android.content.Context
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.CoordinatorLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.google.ar.sceneform.samples.hellosceneform.R
import com.google.ar.sceneform.samples.hellosceneform.android.AndroidUtils
import com.google.ar.sceneform.samples.hellosceneform.ar.ArActivity

class SwipeContainer2ContentView(
        context: Context
) : FrameLayout(context) {

    companion object {
        const val CHOOSE_VIEW = R.layout.choose_view
    }

    private lateinit var container: SwipeContainer2

    fun init(container: SwipeContainer2, layout: Int) {
        this.container = container

        LayoutInflater.from(context).inflate(R.layout.about_view, this, true)
        val params = CoordinatorLayout.LayoutParams(
                CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT
        )
        params.behavior = BottomSheetBehavior<FrameLayout>()
        layoutParams = params
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                container.setPeekHeight(height)
                Log.d("SwipeContainerContentView", "height == $height")
            }
        })
    }

    fun close() {
        animate()
                .setListener(object : Animator.AnimatorListener{
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        container.disappear()
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                    }

                })
                .y(AndroidUtils.getScreenHeight(context as ArActivity).toFloat())
                .duration = SwipeContainer.APPEAR_DURATION
    }

}