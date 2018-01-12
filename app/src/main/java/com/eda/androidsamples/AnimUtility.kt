package com.eda.androidsamples

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.os.Build
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View
import android.view.ViewAnimationUtils

/**
 * Created by kobayashiryou on 2018/01/12.
 *
 * アニメーション処理
 */
object AnimUtility {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun startCircularRevealAnimation(v: View,
                                     x: Int, y: Int,
                                     startColor: Int, endColor: Int,
                                     duration: Long,
                                     endListener: (() -> Unit)? = null) {
        v.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(view: View, i: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int) {
                v.removeOnLayoutChangeListener(this)
                val width = view.width
                val height = view.height
                val radius = Math.sqrt((width * width + height * height).toDouble()).toInt()
                val anim = ViewAnimationUtils.createCircularReveal(v, x, y, 0f, radius.toFloat())
                anim.interpolator = FastOutSlowInInterpolator()
                anim.duration = duration
                anim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        endListener?.invoke()
                    }
                })
                anim.start()
                startBackgroundColorAnimation(v, startColor, endColor, duration)
            }
        })
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun startCircularExitAnimation(v: View,
                                   x: Int, y: Int,
                                   startColor: Int, endColor: Int,
                                   duration: Long,
                                   endListener: (() -> Unit)? = null) {
        val width = v.width
        val height = v.height
        val radius = Math.sqrt((width * width + height * height).toDouble()).toInt()
        val anim = ViewAnimationUtils.createCircularReveal(v, x, y, radius.toFloat(), 0f)
        anim.interpolator = FastOutSlowInInterpolator()
        anim.duration = duration
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                endListener?.invoke()
            }
        })
        anim.start()
        startBackgroundColorAnimation(v, startColor, endColor, duration)
    }

    internal fun startBackgroundColorAnimation(v: View,
                                               startColor: Int,
                                               endColor: Int,
                                               duration: Long) {
        val anim = ValueAnimator()
        anim.setIntValues(startColor, endColor)
        anim.setEvaluator(ArgbEvaluator())
        anim.addUpdateListener { valueAnimator -> v.setBackgroundColor(valueAnimator.animatedValue as Int) }
        anim.duration = duration
        anim.start()
    }
}