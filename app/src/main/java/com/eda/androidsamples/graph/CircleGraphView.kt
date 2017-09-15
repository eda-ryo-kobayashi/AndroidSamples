package com.eda.androidsamples.graph

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.eda.androidsamples.R

/**
 * Created by kobayashiryou on 2017/09/14.
 *
 * 円グラフ
 */
class CircleGraphView(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    companion object {
        private val DEFAULT_BORDER_WIDTH = 10
        private val DEFAULT_BORDER_COLOR = Color.RED
        private val DEFAULT_DEGREE = 360.0f
    }

    private val paint = Paint()
    private val path = Path()

    private var borderWidth = DEFAULT_BORDER_WIDTH.toFloat()
    private var startDegree = 0.0f
    private var arcDegree = DEFAULT_DEGREE
    private var arcDegreeAnimator: ValueAnimator? = null

    init {
        paint.isAntiAlias = true

        val res = context?.theme?.obtainStyledAttributes(attrs, R.styleable.CircleGraphView, defStyleAttr, 0)
        try {

            run {
                val borderWidth = res?.getDimensionPixelSize(R.styleable.CircleGraphView_cgv_border_width, DEFAULT_BORDER_WIDTH)
                borderWidth?.let {
                    this.borderWidth = borderWidth.toFloat()
                }
            }

            run {
                val color = res?.getColor(R.styleable.CircleGraphView_cgv_border_color, DEFAULT_BORDER_COLOR)
                color?.let {
                    paint.color = color
                }
            }

            run {
                val degree = res?.getFloat(R.styleable.CircleGraphView_cgv_arc_degree, DEFAULT_DEGREE)
                degree?.let {
                    arcDegree = degree
                }
            }
            run {
                val degree = res?.getFloat(R.styleable.CircleGraphView_cgv_start_degree, DEFAULT_DEGREE)
                degree?.let {
                    startDegree = degree
                }
            }
        } finally {
            res?.recycle()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        arcDegreeAnimator?.removeAllUpdateListeners()
        arcDegreeAnimator?.cancel()
    }

    fun setStartAngle(degree: Float): CircleGraphView {
        startDegree = degree
        return this
    }
    fun setAngle(degree: Float): CircleGraphView {
        arcDegree =
            if(degree > 360f) {
                360f
            } else {
                if(degree < 0)
                    0f
                else degree
            }
        return this
    }
    fun setAngleByPercent(percent: Float): CircleGraphView {
        arcDegree =
            if(percent > 100) {
                360f
            } else {
                if(percent < 0)
                    0f
                else 3.6f * percent // 360.0f * percent / 100.0f
            }
        return this
    }
    fun setAngleByRatio(ratio: Float): CircleGraphView {
        arcDegree =
            if(ratio > 1.0f) {
                360f
            } else {
                if(ratio < 0.0f)
                    0f
                else 360f * ratio
            }
        return this
    }
    fun setBorderWidth(width: Float): CircleGraphView {
        borderWidth = width
        return this
    }

    fun animateAngle(toAngle: Float, duration: Long): CircleGraphView {
        arcDegreeAnimator?.removeAllUpdateListeners()
        arcDegreeAnimator?.cancel()
        val ta =
            if(toAngle > 360f) {
                360f
            } else {
                if(toAngle < 0)
                    0f
                else toAngle
            }
        arcDegreeAnimator = ValueAnimator.ofFloat(arcDegree, ta)
        arcDegreeAnimator?.duration = duration
        arcDegreeAnimator?.addUpdateListener { value ->
            arcDegree = value.animatedValue as Float
            invalidate()
        }
        arcDegreeAnimator?.start()
        return this
    }

    fun update() {
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        path.reset()
        val width = measuredWidth.toFloat()
        val origin = width * 0.5f
        val innerRadius = origin - borderWidth
        path.addCircle(origin, origin, innerRadius, Path.Direction.CCW)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas?.clipOutPath(path)
        } else {
            canvas?.clipPath(path, Region.Op.DIFFERENCE)
        }

        canvas?.drawArc(
            0.0f, 0.0f,
            width, width,
            startDegree, arcDegree,
            true, paint)
    }
}