package com.jhj.prompt.fragment.options.interfaces

import android.view.View
import java.io.Serializable
import java.util.*

/**
 * 时间选择器回调接口
 * Created by jhj on 2018-3-12 0012.
 */
interface OnTimeSelectedListener : Serializable {
    fun onTimeSelect(date: Date, v: View)
}