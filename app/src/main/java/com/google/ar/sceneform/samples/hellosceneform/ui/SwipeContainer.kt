package com.google.ar.sceneform.samples.hellosceneform.ui

import android.animation.ValueAnimator
import android.content.Context
import android.support.design.widget.BottomSheetBehavior
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.google.ar.sceneform.samples.hellosceneform.R
import com.google.ar.sceneform.samples.hellosceneform.android.AndroidUtils
import com.google.ar.sceneform.samples.hellosceneform.ar.ArActivity
import kotlinx.android.synthetic.main.swipe_container.view.*

class SwipeContainer(
        context: Context
) : FrameLayout(context) {

    companion object {
        const val APPEAR_DURATION = 300L
    }

    private lateinit var behavior: BottomSheetBehavior<SwipeContainerContentView>
    private val contentView = SwipeContainerContentView(context)
    private lateinit var activity: ArActivity


    fun init(contentLayout: Int) {
        LayoutInflater.from(context).inflate(R.layout.swipe_container, this, true)

        activity = context as ArActivity

        contentView.init(this, contentLayout)
        initBottomSheetBehavior()

        content.setOnClickListener {
            contentView.close()
        }

        content.addView(contentView)
    }

    private fun initBottomSheetBehavior() {
        behavior = BottomSheetBehavior.from(contentView)
        behavior.isHideable = false
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
            }

            override fun onStateChanged(view: View, state: Int) {
                if (state == BottomSheetBehavior.STATE_COLLAPSED) {

                }
            }
        })
    }

    fun setPeekHeight(height: Int) {
//        val animator = ValueAnimator.ofInt(0, AndroidUtils.dpToPx(context, 63f).toInt())
//        animator.addUpdateListener {
//            behavior.peekHeight = it.animatedValue as Int
//        }
//        animator.duration = APPEAR_DURATION
//        animator.start()
        behavior.peekHeight = AndroidUtils.dpToPx(context, 63f).toInt()
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun appear() {
        content.animate()
                .alpha(1f)
                .duration = APPEAR_DURATION
    }

    fun disappear() {
        content.animate()
                .alpha(0f)
                .duration = APPEAR_DURATION
    }

}