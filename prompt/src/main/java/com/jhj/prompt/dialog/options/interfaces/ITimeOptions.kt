package com.jhj.prompt.dialog.options.interfaces

import java.util.*

/**
 * 时间选择器
 * Created by jhj on 2018-3-12 0012.
 */
interface ITimeOptions<T> : IBaseOptions<ITimeOptions<T>> {

    fun setDisplayStyle(booleanArray: BooleanArray): T
    fun setLabels(label1: String?, label2: String?, label3: String?, label4: String?, label5: String?, label6: String?): T
    fun setSubmitListener(listener: OnTimeSelectedListener): T
    fun setCancelListener(listener: OnTimeSelectedListener): T

    fun setDate(date: Calendar): T
    fun setRangDate(startDate: Calendar, endDate: Calendar): T
    fun setLunarCalendar(lunarCalendar: Boolean): T//是否显示农历


}