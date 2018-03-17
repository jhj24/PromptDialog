package com.jhj.prompt.options.lib

internal class OnItemSelectedRunnable(private val loopView: WheelView) : Runnable {

    override fun run() {
        loopView.onItemSelectedListener?.onItemSelected(loopView.currentItem)
    }
}
