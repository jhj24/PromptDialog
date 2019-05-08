package com.jhj.prompt.fragment.options.interfaces

import com.jhj.prompt.fragment.OptionsFragment
import java.io.Serializable

/**
 * 通用选择器回调接口
 * Created by jhj on 2018-3-12 0012.
 */
interface OnOptionsSelectedListener<T> : Serializable {
    fun onOptionsSelect(options: OptionsFragment<T>, options1: Int?, options2: Int?, options3: Int?)
}