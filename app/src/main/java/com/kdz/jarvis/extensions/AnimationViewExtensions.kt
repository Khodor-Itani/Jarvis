package com.kdz.jarvis.extensions

import android.animation.Animator
import com.airbnb.lottie.LottieAnimationView

fun LottieAnimationView?.doOnAnimationEnd(onAnimationEnd: () -> Unit) {
    if (this == null) return

    addAnimatorListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
            onAnimationEnd()
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationRepeat(animation: Animator?) {
        }
    }
    )
}