package com.jhj.prompt.dialog.alert.interfaces

import android.view.View
import java.io.Serializable

/**
 * 自定义View
 * Created by jhj on 2018-3-6 0006.
 */
interface OnCustomListener : Serializable {

    fun onLayout(view: View)
}