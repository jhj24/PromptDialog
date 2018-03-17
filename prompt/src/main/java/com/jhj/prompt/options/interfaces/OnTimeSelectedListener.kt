package com.jhj.prompt.options.interfaces

import android.view.View
import java.util.*

/**
 * 时间选择器回调接口
 * Created by jhj on 2018-3-12 0012.
 */
interface OnTimeSelectedListener {
    fun onTimeSelect(date: Date, v: View)
}