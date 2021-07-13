package com.dr.drhomework.util.extension

import android.animation.AnimatorListenerAdapter
import android.view.View
import androidx.annotation.DrawableRes


fun View.setOnAnimationClickListener(callback: (view: View) -> Unit) {
    this.setOnClickListener {
        setClickAnimation(callback)
    }
}

fun View.setClickAnimation(callback: (view: View) -> Unit) {
    val toggleDuration = 80
    val animationMinScale = 0.925f

    try {
        this.apply {
            isClickable = false
            animate().run {
                scaleXBy(1f)
                scaleX(animationMinScale)
                scaleYBy(1f)
                scaleY(animationMinScale)
                duration = toggleDuration.toLong()
                setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: android.animation.Animator) {
                        super.onAnimationEnd(animation)
                        animate().run {
                            scaleX(1f)
                            scaleY(1f)
                            duration = toggleDuration.toLong()
                            setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: android.animation.Animator) {
                                    super.onAnimationEnd(animation)
                                    isClickable = true
                                    callback.invoke(this@apply)
                                }
                            })
                        }
                    }
                })
            }
        }

    } catch (e: Exception) {
        callback.invoke(this)
    }
}

infix fun View.changeVisibleView(outView: View) {
    this.visibility = View.GONE
    outView.visibility = View.VISIBLE
}

fun View.setBackgroundDrawableResource(@DrawableRes drawableId: Int) {
    background = context.getDrawableCompat(drawableId)
}

