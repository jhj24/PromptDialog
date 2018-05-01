package com.jhj.prompt.dialog.options.interfaces

import java.io.Serializable

/**
 * 通用选择器回调接口
 * Created by jhj on 2018-3-12 0012.
 */
interface OnOptionsSelectedListener : Serializable {
    fun onOptionsSelect(options1: Int?, options2: Int?, options3: Int?)
}