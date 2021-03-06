package com.jhj.prompt.fragment.progress

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import org.jetbrains.anko.dip

/**
 * 带百分比的加载框
 * Created by jhj on 2018-3-4 0004.
 */
class PercentView(mContext: Context, attrs: AttributeSet?, defStyleAttr: Int) : BaseLoadingView<LoadingOldStyleView>(mContext, attrs, defStyleAttr) {

    private val minRect = Rect()

    constructor(mContext: Context) : this(mContext, null)

    constructor(mContext: Context, attrs: AttributeSet?) : this(mContext, attrs, 0)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = Math.min(width, height) / 2f - circleWidth / 2f //最小正方形内切圆半径
        val startX = width / 2f - radius
        val startY = height / 2f - radius
        val endX = width / 2f + radius
        val endY = height / 2f + radius
        val oval = RectF(startX, startY, endX, endY)

        /**
         * 底层圆环
         */
        mPaint.reset()
        mPaint.isAntiAlias = true // 消除锯齿
        mPaint.color = bottomCircleColor //
        mPaint.style = Paint.Style.STROKE // 设置空心
        mPaint.strokeWidth = circleWidth
        canvas.drawCircle(centerX, centerY, radius, mPaint)


        /**
         * 画圆弧
         */
        mPaint.color = circleColor
        canvas.drawArc(oval, 270f, (360 * this.progress / maxProgress).toFloat(), false, mPaint) // 根据进度画圆弧


        /**
         * 写字
         */
        if (showScale) {
            val scaleDensity = resources.displayMetrics.scaledDensity
            val text = progress.toString() + "%"
            mPaint.reset()
            mPaint.textSize = scaleDensity * scaleSize
            mPaint.isAntiAlias = true
            mPaint.color = scaleColor
            mPaint.strokeWidth = dip(2).toFloat()
            mPaint.getTextBounds(text, 0, text.length, minRect)
            val baseLine = height / 2 - (mPaint.fontMetrics.bottom + mPaint.fontMetrics.top) / 2
            val textWidth = minRect.width()
            val textStartX = width / 2 - textWidth / 2
            canvas.drawText(text, textStartX.toFloat(), baseLine, mPaint)
        }
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     */
    @get:Synchronized
    var progress: Int = 0
        @Synchronized set(value) {
            var progress = value
            if (progress < 0) {
                throw IllegalArgumentException("progress not less than 0")
            }
            if (progress > maxProgress) {
                progress = maxProgress
            }
            if (progress <= maxProgress) {
                field = progress
                postInvalidate()
            }

        }


    /**
     * 设置进度最大值
     */
    @get:Synchronized
    var maxProgress: Int = 100
        @Synchronized set(value) {
            if (value < 0) {
                throw  IllegalArgumentException("max not less than 0")
            }
            field = value
        }


    /**
     * 中心百分比是否显示
     */
    @get:Synchronized
    var showScale: Boolean = false
        @Synchronized set(value) {
            field = value
        }

    @get:Synchronized
    var scaleSize: Float = 12f
        @Synchronized set(value) {
            field = value
        }
    /**
     * 中心百分比颜色
     */
    @get:Synchronized
    var scaleColor: Int = Color.BLACK
        @Synchronized set(value) {
            field = value
        }

}