package com.jhj.prompt.dialog.progress.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * 处于加载中的ProgressView基类
 * Created by jhj on 2018-3-3 0003.
 */
open class BaseLoadingView<T : BaseLoadingView<T>>(private val mContext: Context, attrs: AttributeSet?, defStyleAttr: Int) : View(mContext, attrs, defStyleAttr) {

    constructor(mContext: Context, attrs: AttributeSet?) : this(mContext, attrs, 0)

    constructor(mContext: Context) : this(mContext, null)

    protected var mPaint: Paint = Paint()
    protected var currentAngle = 0f //当前旋转角度
    protected val density = mContext.resources.displayMetrics.density

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measureHandle(widthMeasureSpec)
        val height = measureHandle(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    @SuppressLint("SwitchIntDef")
    private fun measureHandle(measureSpec: Int): Int {
        val defaultSize = 50 * density.toInt()
        val spec = MeasureSpec.getSize(measureSpec)
        val mode = MeasureSpec.getMode(measureSpec)

        return when (mode) {
            MeasureSpec.EXACTLY -> spec
            MeasureSpec.AT_MOST -> Math.min(spec, defaultSize)
            else -> defaultSize
        }
    }


    @get:Synchronized
    var circleColor: Int = Color.BLACK
        @Synchronized set(value) {
            field = value
        }

    @get:Synchronized
    var circleWidth: Float = 2 * density
        @Synchronized set(value) {
            if (value < 0) {
                throw IllegalArgumentException("circleWidth not less than 0")
            }
            field = value
        }


    @get:Synchronized
    var bottomCircleColor: Int = Color.LTGRAY
        @Synchronized set(value) {
            field = value
        }



}