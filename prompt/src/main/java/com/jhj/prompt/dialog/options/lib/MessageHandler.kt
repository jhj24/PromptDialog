package com.jhj.prompt.dialog.options.lib

import android.os.Handler
import android.os.Message

internal class MessageHandler(private val wheelView: WheelView) : Handler() {

    companion object {
        const val WHAT_INVALIDATE_LOOP_VIEW = 1000
        //
        const val WHAT_SMOOTH_SCROLL = 2000
        //item被选中
        const val WHAT_ITEM_SELECTED = 3000
    }


    override fun handleMessage(msg: Message) {
        when (msg.what) {
            WHAT_INVALIDATE_LOOP_VIEW -> wheelView.invalidate()

            WHAT_SMOOTH_SCROLL -> wheelView.smoothScroll(WheelView.ACTION.FLING)

            WHAT_ITEM_SELECTED -> wheelView.onItemSelected()
        }
    }


}
