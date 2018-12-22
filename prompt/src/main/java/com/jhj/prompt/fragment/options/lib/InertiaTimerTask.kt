package com.jhj.prompt.fragment.options.lib

import java.util.*

/**
 * 滚动惯性的实现
 */
internal class InertiaTimerTask(private val loopView: WheelView, private val velocityY: Float) : TimerTask() {

    private var value: Float = 0.toFloat()

    init {
        value = Integer.MAX_VALUE.toFloat()
    }

    override fun run() {
        if (value == Integer.MAX_VALUE.toFloat()) {
            value = if (Math.abs(velocityY) > 2000f) {
                if (velocityY > 0.0f) {
                    2000f
                } else {
                    -2000f
                }
            } else {
                velocityY
            }
        }
        if (Math.abs(value) in 0.0f..20f) {
            loopView.cancelFuture()
            loopView.handler.sendEmptyMessage(MessageHandler.WHAT_SMOOTH_SCROLL)
            return
        }
        val i = (value * 10f / 1000f).toInt()
        loopView.totalScrollY = loopView.totalScrollY - i
        if (!loopView.isLoop) {
            val itemHeight = loopView.itemHeight
            var top = -loopView.initPosition * itemHeight
            var bottom = (loopView.itemsCount - 1 - loopView.initPosition) * itemHeight
            if (loopView.totalScrollY - itemHeight * 0.25 < top) {
                top = loopView.totalScrollY + i
            } else if (loopView.totalScrollY + itemHeight * 0.25 > bottom) {
                bottom = loopView.totalScrollY + i
            }

            if (loopView.totalScrollY <= top) {
                value = 40f
                loopView.totalScrollY = top.toInt().toFloat()
            } else if (loopView.totalScrollY >= bottom) {
                loopView.totalScrollY = bottom.toInt().toFloat()
                value = -40f
            }
        }
        if (value < 0.0f) {
            value += 20f
        } else {
            value -= 20f
        }
        loopView.handler.sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW)
    }

}
