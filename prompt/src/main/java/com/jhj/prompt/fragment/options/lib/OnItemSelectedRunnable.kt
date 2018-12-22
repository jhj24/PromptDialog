package com.jhj.prompt.fragment.options.lib

internal class OnItemSelectedRunnable(private val loopView: WheelView) : Runnable {

    override fun run() {
        loopView.onItemSelectedListener?.onItemSelected(loopView.currentItem)
    }
}
