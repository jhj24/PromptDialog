package com.jhj.prompt.options.interfaces

import android.view.View

import java.io.Serializable

/**
 * 自定义控件回调接口
 */

interface OnCustomListener : Serializable {
    fun customLayout(v: View)
}
