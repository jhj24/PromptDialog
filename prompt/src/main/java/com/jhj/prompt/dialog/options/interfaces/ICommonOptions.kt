package com.jhj.prompt.dialog.options.interfaces


/**
 * 通用选择器方法
 * Created by jhj on 2018-3-12 0012.
 */
interface ICommonOptions<T> : IBaseOptions<ICommonOptions<T>> {


    fun setLabels(label1: String?, label2: String?, label3: String?): T //单位

    fun setSelectOptions(option1: Int): T //默认选中项
    fun setSelectOptions(option1: Int, option2: Int): T
    fun setSelectOptions(option1: Int, option2: Int, option3: Int): T

    fun setSubmitListener(listener: OnOptionsSelectedListener): T
    fun setCancelListener(listener: OnOptionsSelectedListener): T
}