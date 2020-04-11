package com.google.ar.sceneform.samples.hellosceneform.ui

import android.animation.Animator
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
    private var contentLayout = 0


    fun init(contentLayout: Int) {
        this.contentLayout = contentLayout

        LayoutInflater.from(context).inflate(R.layout.swipe_container, this, true)

        activity = context as ArActivity

        contentView.init(this, contentLayout)
        initBottomSheetBehavior()

        content.addView(contentView)
    }

    private fun initBottomSheetBehavior() {
        behavior = BottomSheetBehavior.from(contentView)
        behavior.isHideable = contentLayout != SwipeContainerContentView.CHOOSE_VIEW
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
            }

            override fun onStateChanged(view: View, state: Int) {
                when (state) {
                    BottomSheetBehavior.STATE_COLLAPSED -> removeBg()
                    BottomSheetBehavior.STATE_EXPANDED -> appear()
                    BottomSheetBehavior.STATE_HIDDEN -> disappear()
                }
            }
        })
    }

    fun setPeekHeight(height: Int) {
        if (contentLayout == SwipeContainerContentView.CHOOSE_VIEW) {
            behavior.peekHeight = AndroidUtils.dpToPx(context, 63f).toInt()
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        else {
            val animator = ValueAnimator.ofInt(0, height)
            animator.addUpdateListener {
                val value = it.animatedValue as Int
                behavior.peekHeight = value
            }
            animator.duration = APPEAR_DURATION
            animator.start()
        }
    }

    fun collapse() {
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun appear() {
        bg.animate()
                .alpha(1f)
                .duration = APPEAR_DURATION
    }

    fun disappear() {
        bg.animate()
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        if (contentLayout == SwipeContainerContentView.DESCRIPTION_VIEW)
                            activity.removeDescription()
                        else
                            activity.removeSettingsView()
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                    }

                })
                .alpha(0f)
                .duration = APPEAR_DURATION
    }

    private fun removeBg() {
        bg.animate()
                .alpha(0f)
                .duration = APPEAR_DURATION
    }

}