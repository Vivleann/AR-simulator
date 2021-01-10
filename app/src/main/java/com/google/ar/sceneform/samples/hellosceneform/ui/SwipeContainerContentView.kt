package com.google.ar.sceneform.samples.hellosceneform.ui

import android.animation.Animator
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.CoordinatorLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.google.ar.sceneform.samples.hellosceneform.R
import com.google.ar.sceneform.samples.hellosceneform.android.AndroidUtils
import com.google.ar.sceneform.samples.hellosceneform.ar.ArActivity

class SwipeContainerContentView(
        context: Context
) : FrameLayout(context) {

    companion object {
        const val CHOOSE_VIEW = R.layout.choose_view
        const val DESCRIPTION_VIEW = R.layout.about_view
        const val SETTINGS_VIEW = R.layout.figure_settings_view
    }

    private lateinit var container: SwipeContainer

    fun init(container: SwipeContainer, layout: Int) {
        this.container = container

        val params = CoordinatorLayout.LayoutParams(
                CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT
        )
        params.behavior = BottomSheetBehavior<FrameLayout>()
        layoutParams = params

        when (layout) {
            SETTINGS_VIEW -> addView(SettingsView(context))
            CHOOSE_VIEW -> addView(ChooseView(context))
            else -> addView(DescriptionView(context))
        }

//        LayoutInflater.from(context).inflate(layout, this, true)

        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                container.setPeekHeight(height)
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