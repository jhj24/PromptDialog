package com.jhj.prompt.dialog.progress.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View

/**
 * 新样式：处于加载中的ProgressView
 * Created by jhj on 2018-3-4 0004.
 */
class LoadingNewStyleView(val mContext: Context, attrs: AttributeSet?, defStyleAttr: Int) : BaseLoadingView<LoadingOldStyleView>(mContext, attrs, defStyleAttr) {

    constructor(mContext: Context, attrs: AttributeSet?) : this(mContext, attrs, 0)

    constructor(mContext: Context) : this(mContext, null)

    private var lastTimeAnimated: Long = SystemClock.uptimeMillis()

    private var spinSpeed = 200f
    private var mProgress = 0.0f
    private val barLength = 10
    private var barExtraLength = 0f
    private var pauseGrowingTimer: Long = 0 //计时器
    private val pauseGrowingDuration: Long = 150 //圆弧最长或最短状态在界面上显示的时间
    private var startGrowingTimer = 0.0
    private var startGrowingDuration = 500.0 //圆弧由最长变最短或由最短变最长所有时间
    private var barGrowingFromFront = true
    private val barMaxLength = 300 //圆弧的最大值

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val radius = Math.min(width, height) / 2 - circleWidth / 2
        val startX = width / 2 - radius
        val startY = height / 2 - radius
        val endX = width / 2 + radius
        val endY = width / 2 + radius
        val oval = RectF(startX, startY, endX, endY)

        //画底层圆环
        mPaint.isAntiAlias = true
        mPaint.color = bottomCircleColor
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = circleWidth
        canvas?.drawArc(oval, -90f, 360f, false, mPaint)


        val deltaTime = SystemClock.uptimeMillis() - lastTimeAnimated
        val deltaNormalized = deltaTime * spinSpeed / 1000.0f
        updateBarLength(deltaTime)
        mProgress += deltaNormalized

        if (mProgress > 360) {
            mProgress -= 360f
        }

        var from = mProgress - 90 //
        var length = barLength + barExtraLength

        if (isInEditMode) {
            from = 0f
            length = 135f
        }
        mPaint.color = circleColor
        canvas?.drawArc(oval, from, length, false, mPaint)
        lastTimeAnimated = SystemClock.uptimeMillis()
        postInvalidate()

    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)

        if (visibility == View.VISIBLE) {
            lastTimeAnimated = SystemClock.uptimeMillis()
        }
    }

    private fun updateBarLength(deltaTimeInMilliSeconds: Long) {
        if (pauseGrowingTimer >= pauseGrowingDuration) {
            startGrowingTimer += deltaTimeInMilliSeconds
            if (startGrowingTimer > startGrowingDuration) {
                startGrowingTimer -= startGrowingDuration
                pauseGrowingTimer = 0
                barGrowingFromFront = !barGrowingFromFront
            }

            val distance = Math.cos((startGrowingTimer / startGrowingDuration + 1) * Math.PI).toFloat() / 2 + 0.5f
            val destLength = (barMaxLength - barLength).toFloat()

            if (barGrowingFromFront) {//圆弧边长
                barExtraLength = distance * destLength
            } else {
                val newLength = destLength * (1 - distance)
                mProgress += barExtraLength - newLength
                barExtraLength = newLength
            }
        } else {//圆弧处于最长或最短状态
            pauseGrowingTimer += deltaTimeInMilliSeconds
        }
    }
}