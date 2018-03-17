package com.jhj.prompt.progress.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import org.jetbrains.anko.withAlpha

/**
 * 老样式：处于加载中的ProgressView
 * Created by jhj on 2018-3-4 0004.
 */
class LoadingOldStyleView(mContext: Context, attrs: AttributeSet?, defStyleAttr: Int) : BaseLoadingView<LoadingOldStyleView>(mContext, attrs, defStyleAttr) {

    constructor(mContext: Context, attrs: AttributeSet?) : this(mContext, attrs, 0)

    constructor(mContext: Context) : this(mContext, null)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = Math.min(width, height) / 2f - circleWidth / 2f //最小正方形内切圆半径
        val startX = width / 2f - radius
        val startY = height / 2f - radius
        val endX = width / 2f + radius
        val endY = height / 2f + radius
        val rect = RectF(startX, startY, endX, endY)


        //设置旋转
        canvas?.rotate(currentAngle, centerX, centerY)
        if (currentAngle >= 360) {
            currentAngle -= 360
        } else {
            currentAngle += 5f
        }


        //画渐变圆
        val matrix = Matrix()
        val sweepGradient = SweepGradient(centerX, centerY, shaderColors(circleColor), floatArrayOf(0.2f, 0.4f, 0.8f))
        matrix.setRotate(-90f, centerX, centerY)
        mPaint.strokeWidth = circleWidth
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
        mPaint.color = circleColor
        sweepGradient.setLocalMatrix(matrix)
        mPaint.shader = sweepGradient
        canvas?.drawArc(rect, 0f, 360f, false, mPaint)

        //设置起点为圆形
        mPaint.reset()
        mPaint.color = circleColor
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL

        val minRect = RectF(width / 2f - circleWidth / 2f, height / 2f - radius - circleWidth / 2f, width / 2f + circleWidth / 2f, height / 2f - radius + circleWidth / 2f)
        canvas?.drawArc(minRect, -90f, 180f, true, mPaint)
        postInvalidate()
    }

    /**
     * 颜色渲染
     */
    private fun shaderColors(color: Int): IntArray {
        val alpha = Color.alpha(color)
        return intArrayOf(color.withAlpha(alpha / 8), color.withAlpha(alpha / 5), color.withAlpha(alpha))
    }

}