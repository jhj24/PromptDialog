package com.jhj.prompt.fragment.options.lib

import android.view.GestureDetector
import android.view.MotionEvent

internal class LoopViewGestureListener(private val loopView: WheelView) : GestureDetector.SimpleOnGestureListener() {

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        loopView.scrollBy(velocityY)
        return true
    }
}
