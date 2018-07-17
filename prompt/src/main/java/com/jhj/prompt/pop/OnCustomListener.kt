package com.jhj.prompt.pop

import android.view.View
import android.widget.PopupWindow

/**
 * Created by jhj on 18-7-17.
 */
interface OnCustomListener {
    fun onLayout(view: View, popupWindow: PopupWindow?)
}