package com.jhj.prompt.dialog.options.wheel

import android.view.View
import com.jhj.prompt.dialog.options.adapter.CommonWheelAdapter
import com.jhj.prompt.dialog.options.adapter.TimeWheelAdapter
import com.jhj.prompt.dialog.options.interfaces.OnItemSelectedListener
import com.jhj.prompt.dialog.options.lib.WheelView
import com.jhj.prompt.dialog.options.utils.ChinaDate
import com.jhj.prompt.dialog.options.utils.DividerType
import com.jhj.prompt.dialog.options.utils.LunarCalendar
import kotlinx.android.synthetic.main.layout_pickerview_time.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间选择器
 * Created by jhj on 2018-3-4 0004.
 */
class TimeWheel(private val view: View) {

    companion object {
        var dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        private const val DEFAULT_START_YEAR = 1999
        private const val DEFAULT_END_YEAR = 2099
        private const val DEFAULT_START_MONTH = 1
        private const val DEFAULT_END_MONTH = 12
        private const val DEFAULT_START_DAY = 1
        private const val DEFAULT_END_DAY = 31
    }


    private var wvYear: WheelView = view.year
    private var wvMonth: WheelView = view.month
    private var wvDay: WheelView = view.day
    private var wvHours: WheelView = view.hour
    private var wvMinutes: WheelView = view.min
    private var wvSeconds: WheelView = view.second

    private var startYear = DEFAULT_START_YEAR
    private var endYear = DEFAULT_END_YEAR
    private var startMonth = DEFAULT_START_MONTH
    private var endMonth = DEFAULT_END_MONTH
    private var startDay = DEFAULT_START_DAY
    private var endDay = DEFAULT_END_DAY //表示31天的
    private var currentYear: Int = 0
    private val listBig = arrayListOf("1", "3", "5", "7", "8", "10", "12")
    private val listLittle = arrayListOf("4", "6", "9", "11")
    private var isLunarCalendar = false

    fun getView(): View {
        return view
    }

    fun setLunarCalendar(lunar: Boolean) {
        this.isLunarCalendar = lunar
    }

    //如果是农历 返回对应的公历时间
    val time: String
        get() {
            if (isLunarCalendar) {
                return lunarTime
            }
            val sb = StringBuffer()
            if (currentYear == startYear) {
                if (wvMonth.currentItem + startMonth == startMonth) {
                    sb.append(wvYear.currentItem + startYear).append("-")
                            .append(wvMonth.currentItem + startMonth).append("-")
                            .append(wvDay.currentItem + startDay).append(" ")
                            .append(wvHours.currentItem).append(":")
                            .append(wvMinutes.currentItem).append(":")
                            .append(wvSeconds.currentItem)
                } else {
                    sb.append(wvYear.currentItem + startYear).append("-")
                            .append(wvMonth.currentItem + startMonth).append("-")
                            .append(wvDay.currentItem + 1).append(" ")
                            .append(wvHours.currentItem).append(":")
                            .append(wvMinutes.currentItem).append(":")
                            .append(wvSeconds.currentItem)
                }


            } else {
                sb.append(wvYear.currentItem + startYear).append("-")
                        .append(wvMonth.currentItem + 1).append("-")
                        .append(wvDay.currentItem + 1).append(" ")
                        .append(wvHours.currentItem).append(":")
                        .append(wvMinutes.currentItem).append(":")
                        .append(wvSeconds.currentItem)
            }

            return sb.toString()
        }


    /**
     * 农历返回对应的公历时间
     *
     * @return
     */
    private val lunarTime: String
        get() {
            val sb = StringBuffer()
            val year = wvYear.currentItem + startYear
            val month: Int
            var isLeapMonth = false
            if (ChinaDate.leapMonth(year) == 0) {
                month = wvMonth.currentItem + 1
            } else {
                when {
                    wvMonth.currentItem + 1 - ChinaDate.leapMonth(year) <= 0 -> month = wvMonth.currentItem + 1
                    wvMonth.currentItem + 1 - ChinaDate.leapMonth(year) == 1 -> {
                        month = wvMonth.currentItem
                        isLeapMonth = true
                    }
                    else -> month = wvMonth.currentItem
                }
            }
            val day = wvDay.currentItem + 1
            val solar = LunarCalendar.lunarToSolar(year, month, day, isLeapMonth)

            sb.append(solar[0]).append("-")
                    .append(solar[1]).append("-")
                    .append(solar[2]).append(" ")
                    .append(wvHours.currentItem).append(":")
                    .append(wvMinutes.currentItem).append(":")
                    .append(wvSeconds.currentItem)
            return sb.toString()
        }


    fun setPicker(year: Int, month: Int, day: Int, h: Int, m: Int, s: Int) {
        if (isLunarCalendar) {
            val lunar = LunarCalendar.solarToLunar(year, month + 1, day)
            setLunar(lunar[0], lunar[1] - 1, lunar[2], h, m, s)
        } else {
            setSolar(year, month, day, h, m, s)
        }
    }

    /**
     * 设置农历
     *
     * @param year
     * @param month
     * @param day
     * @param h
     * @param m
     * @param s
     */
    private fun setLunar(year: Int, month: Int, day: Int, h: Int, m: Int, s: Int) {
        // 年
        view.year.let {
            it.setAdapter(CommonWheelAdapter(ChinaDate.getYears(startYear, endYear)))// 设置"年"的显示数据
            it.year.setLabel("")// 添加文字
            it.year.currentItem = year - startYear// 初始化时显示的数据
        }
        //月
        view.month.let {
            it.setAdapter(CommonWheelAdapter(ChinaDate.getMonths(year)))
            it.setLabel("")
            it.currentItem = month
        }

        // 日
        view.day.let {
            // 判断大小月及是否闰年,用来确定"日"的数据
            if (ChinaDate.leapMonth(year) == 0) {
                it.setAdapter(CommonWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year, month))))
            } else {
                it.setAdapter(CommonWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year))))
            }
            it.setLabel("")
            it.currentItem = day - 1
        }
        //时
        view.hour.let {
            it.setAdapter(TimeWheelAdapter(0, 23))
            it.currentItem = h
        }
        //分
        view.min.let {
            it.setAdapter(TimeWheelAdapter(0, 59))
            it.currentItem = m
        }

        //秒
        view.second.let {
            it.setAdapter(TimeWheelAdapter(0, 59))
            it.currentItem = s
        }


        // 添加"年"监听
        val wheelListenerYear = object : OnItemSelectedListener {
            override fun onItemSelected(index: Int) {
                val yearNum = index + startYear
                // 判断是不是闰年,来确定月和日的选择
                wvMonth.setAdapter(CommonWheelAdapter(ChinaDate.getMonths(yearNum)))
                if (ChinaDate.leapMonth(yearNum) != 0 && wvMonth.currentItem > ChinaDate.leapMonth(yearNum) - 1) {
                    wvMonth.currentItem = wvMonth.currentItem + 1
                } else {
                    wvMonth.currentItem = wvMonth.currentItem
                }

                val maxItem = if (ChinaDate.leapMonth(yearNum) != 0 && wvMonth.currentItem > ChinaDate.leapMonth(yearNum) - 1) {
                    if (wvMonth.currentItem == ChinaDate.leapMonth(yearNum) + 1) {
                        wvDay.setAdapter(CommonWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(yearNum))))
                        ChinaDate.leapDays(yearNum)
                    } else {
                        wvDay.setAdapter(CommonWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(yearNum, wvMonth.currentItem))))
                        ChinaDate.monthDays(yearNum, wvMonth.currentItem)
                    }
                } else {
                    wvDay.setAdapter(CommonWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(yearNum, wvMonth.currentItem + 1))))
                    ChinaDate.monthDays(yearNum, wvMonth.currentItem + 1)
                }

                if (wvDay.currentItem > maxItem - 1) {
                    wvDay.currentItem = maxItem - 1
                }
            }
        }
        // 添加"月"监听
        val wheelListenerMonth = object : OnItemSelectedListener {
            override fun onItemSelected(index: Int) {
                val yearNum = wvYear.currentItem + startYear
                val maxItem: Int
                maxItem = if (ChinaDate.leapMonth(yearNum) != 0 && index > ChinaDate.leapMonth(yearNum) - 1) {
                    if (wvMonth.currentItem == ChinaDate.leapMonth(yearNum) + 1) {
                        wvDay.setAdapter(CommonWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(yearNum))))
                        ChinaDate.leapDays(yearNum)
                    } else {
                        wvDay.setAdapter(CommonWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(yearNum, index))))
                        ChinaDate.monthDays(yearNum, index)
                    }
                } else {
                    wvDay.setAdapter(CommonWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(yearNum, index + 1))))
                    ChinaDate.monthDays(yearNum, index + 1)
                }

                if (wvDay.currentItem > maxItem - 1) {
                    wvDay.currentItem = maxItem - 1
                }
            }
        }
        wvYear.setOnItemSelectedListener(wheelListenerYear)
        wvMonth.setOnItemSelectedListener(wheelListenerMonth)

    }

    /**
     * 设置公历
     *
     * @param year
     * @param month
     * @param day
     * @param h
     * @param m
     * @param s
     */
    private fun setSolar(year: Int, month: Int, day: Int, h: Int, m: Int, s: Int) {

        currentYear = year
        // 年
        wvYear.setAdapter(TimeWheelAdapter(startYear, endYear))// 设置"年"的显示数据
        wvYear.currentItem = year - startYear// 初始化时显示的数据
        // 月
        when {
            startYear == endYear -> {//开始年等于终止年
                wvMonth.setAdapter(TimeWheelAdapter(startMonth, endMonth))
                wvMonth.currentItem = month + 1 - startMonth
            }
            year == startYear -> {
                //起始日期的月份控制
                wvMonth.setAdapter(TimeWheelAdapter(startMonth, 12))
                wvMonth.currentItem = month + 1 - startMonth
            }
            year == endYear -> {
                //终止日期的月份控制
                wvMonth.setAdapter(TimeWheelAdapter(1, endMonth))
                wvMonth.currentItem = month
            }
            else -> {
                wvMonth.setAdapter(TimeWheelAdapter(1, 12))
                wvMonth.currentItem = month
            }
        }

        // 日


        if (startYear == endYear && startMonth == endMonth) {
            if (listBig.contains((month + 1).toString())) {
                if (endDay > 31) {
                    endDay = 31
                }
                wvDay.setAdapter(TimeWheelAdapter(startDay, endDay))
            } else if (listLittle.contains((month + 1).toString())) {
                if (endDay > 30) {
                    endDay = 30
                }
                wvDay.setAdapter(TimeWheelAdapter(startDay, endDay))
            } else {
                // 闰年
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    if (endDay > 29) {
                        endDay = 29
                    }
                    wvDay.setAdapter(TimeWheelAdapter(startDay, endDay))
                } else {
                    if (endDay > 28) {
                        endDay = 28
                    }
                    wvDay.setAdapter(TimeWheelAdapter(startDay, endDay))
                }
            }
            wvDay.currentItem = day - startDay
        } else if (year == startYear && month + 1 == startMonth) {
            // 起始日期的天数控制
            if (listBig.contains((month + 1).toString())) {

                wvDay.setAdapter(TimeWheelAdapter(startDay, 31))
            } else if (listLittle.contains((month + 1).toString())) {

                wvDay.setAdapter(TimeWheelAdapter(startDay, 30))
            } else {
                // 闰年
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {

                    wvDay.setAdapter(TimeWheelAdapter(startDay, 29))
                } else {

                    wvDay.setAdapter(TimeWheelAdapter(startDay, 28))
                }
            }
            wvDay.currentItem = day - startDay
        } else if (year == endYear && month + 1 == endMonth) {
            // 终止日期的天数控制
            if (listBig.contains((month + 1).toString())) {
                if (endDay > 31) {
                    endDay = 31
                }
                wvDay.setAdapter(TimeWheelAdapter(1, endDay))
            } else if (listLittle.contains((month + 1).toString())) {
                if (endDay > 30) {
                    endDay = 30
                }
                wvDay.setAdapter(TimeWheelAdapter(1, endDay))
            } else {
                // 闰年
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    if (endDay > 29) {
                        endDay = 29
                    }
                    wvDay.setAdapter(TimeWheelAdapter(1, endDay))
                } else {
                    if (endDay > 28) {
                        endDay = 28
                    }
                    wvDay.setAdapter(TimeWheelAdapter(1, endDay))
                }
            }
            wvDay.currentItem = day - 1
        } else {
            // 判断大小月及是否闰年,用来确定"日"的数据
            if (listBig.contains((month + 1).toString())) {
                wvDay.setAdapter(TimeWheelAdapter(1, 31))
            } else if (listLittle.contains((month + 1).toString())) {
                wvDay.setAdapter(TimeWheelAdapter(1, 30))
            } else {
                // 闰年
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    wvDay.setAdapter(TimeWheelAdapter(1, 29))
                } else {
                    wvDay.setAdapter(TimeWheelAdapter(1, 28))
                }
            }
            wvDay.currentItem = day - 1
        }


        //时

        wvHours.setAdapter(TimeWheelAdapter(0, 23))
        wvHours.currentItem = h
        //分

        wvMinutes.setAdapter(TimeWheelAdapter(0, 59))
        wvMinutes.currentItem = m
        //秒

        wvSeconds.setAdapter(TimeWheelAdapter(0, 59))
        wvSeconds.currentItem = s

        // 添加"年"监听
        val wheelListenerYear = object : OnItemSelectedListener {
            override fun onItemSelected(index: Int) {
                val yearNum = index + startYear
                currentYear = yearNum
                var currentMonthItem = wvMonth.currentItem//记录上一次的item位置
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (startYear == endYear) {
                    //重新设置月份
                    wvMonth.setAdapter(TimeWheelAdapter(startMonth, endMonth))

                    if (currentMonthItem > wvMonth.getAdapter()?.itemsCount as Int - 1) {
                        currentMonthItem = wvMonth.getAdapter()?.itemsCount as Int - 1
                        wvMonth.currentItem = currentMonthItem
                    }

                    val monthNum = currentMonthItem + startMonth

                    when {
                        startMonth == endMonth -> //重新设置日
                            setReDay(yearNum, monthNum, startDay, endDay)
                        monthNum == startMonth -> //重新设置日
                            setReDay(yearNum, monthNum, startDay, 31)
                        monthNum == endMonth -> setReDay(yearNum, monthNum, 1, endDay)
                        else -> //重新设置日
                            setReDay(yearNum, monthNum, 1, 31)
                    }
                } else if (yearNum == startYear) {//等于开始的年
                    //重新设置月份
                    wvMonth.setAdapter(TimeWheelAdapter(startMonth, 12))

                    if (currentMonthItem > wvMonth.getAdapter()?.itemsCount as Int - 1) {
                        currentMonthItem = wvMonth.getAdapter()?.itemsCount as Int - 1
                        wvMonth.currentItem = currentMonthItem
                    }

                    val nMonth = currentMonthItem + startMonth
                    if (nMonth == startMonth) {
                        //重新设置日
                        setReDay(yearNum, nMonth, startDay, 31)
                    } else {
                        //重新设置日
                        setReDay(yearNum, nMonth, 1, 31)
                    }

                } else if (yearNum == endYear) {
                    //重新设置月份
                    wvMonth.setAdapter(TimeWheelAdapter(1, endMonth))
                    if (currentMonthItem > wvMonth.getAdapter()?.itemsCount as Int - 1) {
                        currentMonthItem = wvMonth.getAdapter()?.itemsCount as Int - 1
                        wvMonth.currentItem = currentMonthItem
                    }
                    val monthNum = currentMonthItem + 1

                    if (monthNum == endMonth) {
                        //重新设置日
                        setReDay(yearNum, monthNum, 1, endDay)
                    } else {
                        //重新设置日
                        setReDay(yearNum, monthNum, 1, 31)
                    }

                } else {
                    //重新设置月份
                    wvMonth.setAdapter(TimeWheelAdapter(1, 12))
                    //重新设置日
                    setReDay(yearNum, wvMonth.currentItem + 1, 1, 31)

                }
            }
        }
        // 添加"月"监听
        val wheelListenerMonth = object : OnItemSelectedListener {
            override fun onItemSelected(index: Int) {
                var monthNum = index + 1

                if (startYear == endYear) {
                    monthNum = monthNum + startMonth - 1
                    when {
                        startMonth == endMonth -> //重新设置日
                            setReDay(currentYear, monthNum, startDay, endDay)
                        startMonth == monthNum -> //重新设置日
                            setReDay(currentYear, monthNum, startDay, 31)
                        endMonth == monthNum -> setReDay(currentYear, monthNum, 1, endDay)
                        else -> setReDay(currentYear, monthNum, 1, 31)
                    }
                } else if (currentYear == startYear) {
                    monthNum = monthNum + startMonth - 1
                    if (monthNum == startMonth) {
                        //重新设置日
                        setReDay(currentYear, monthNum, startDay, 31)
                    } else {
                        //重新设置日
                        setReDay(currentYear, monthNum, 1, 31)
                    }

                } else if (currentYear == endYear) {
                    if (monthNum == endMonth) {
                        //重新设置日
                        setReDay(currentYear, wvMonth.currentItem + 1, 1, endDay)
                    } else {
                        setReDay(currentYear, wvMonth.currentItem + 1, 1, 31)
                    }

                } else {
                    //重新设置日
                    setReDay(currentYear, monthNum, 1, 31)

                }
            }
        }
        wvYear.setOnItemSelectedListener(wheelListenerYear)
        wvMonth.setOnItemSelectedListener(wheelListenerMonth)
    }


    private fun setReDay(yearNum: Int, monthNum: Int, startD: Int, endD: Int) {
        var endDay = endD
        var currentItem = wvDay.currentItem

        if (listBig.contains(monthNum.toString())) {
            if (endDay > 31) {
                endDay = 31
            }
            wvDay.setAdapter(TimeWheelAdapter(startD, endDay))
        } else if (listLittle.contains(monthNum.toString())) {
            if (endDay > 30) {
                endDay = 30
            }
            wvDay.setAdapter(TimeWheelAdapter(startD, endDay))
        } else {
            if (yearNum % 4 == 0 && yearNum % 100 != 0 || yearNum % 400 == 0) {
                if (endDay > 29) {
                    endDay = 29
                }
                wvDay.setAdapter(TimeWheelAdapter(startD, endDay))
            } else {
                if (endDay > 28) {
                    endDay = 28
                }
                wvDay.setAdapter(TimeWheelAdapter(startD, endDay))
            }
        }

        if (currentItem > wvDay.getAdapter()?.itemsCount as Int - 1) {
            currentItem = wvDay.getAdapter()?.itemsCount as Int - 1
            wvDay.currentItem = currentItem
        }
    }


    fun setLabels(label_year: String?, label_month: String?, label_day: String?, label_hours: String?, label_mins: String?, label_seconds: String?) {
        if (isLunarCalendar) {
            return
        }
        wvYear.setLabel(label_year ?: "年")
        wvMonth.setLabel(label_month ?: "月")
        wvDay.setLabel(label_day ?: "日")
        wvHours.setLabel(label_hours ?: "时")
        wvMinutes.setLabel(label_mins ?: "分")
        wvSeconds.setLabel(label_seconds ?: "秒")
    }


    fun setOptionsTextSize(textSize: Float) {
        wvDay.setTextSize(textSize)
        wvMonth.setTextSize(textSize)
        wvYear.setTextSize(textSize)
        wvHours.setTextSize(textSize)
        wvMinutes.setTextSize(textSize)
        wvSeconds.setTextSize(textSize)
    }


    fun setRangDate(startDate: Calendar, endDate: Calendar) {
        this.startYear = startDate.get(Calendar.YEAR)
        this.endYear = endDate.get(Calendar.YEAR)
        this.startMonth = startDate.get(Calendar.MONTH) + 1
        this.endMonth = endDate.get(Calendar.MONTH) + 1
        this.startDay = startDate.get(Calendar.DAY_OF_MONTH)
        this.endDay = endDate.get(Calendar.DAY_OF_MONTH)
    }

    fun setItemNum(num: Int) {
        if (num < 0) {
            throw IllegalArgumentException("Item num cannot less than 0")
        }
        wvYear.setItemNum(num)
        wvMonth.setItemNum(num)
        wvDay.setItemNum(num)
        wvHours.setItemNum(num)
        wvMinutes.setItemNum(num)
        wvSeconds.setItemNum(num)
    }

    fun setTextXOffset(offset: Int) {
        wvDay.setTextXOffset(offset)
        wvMonth.setTextXOffset(offset)
        wvYear.setTextXOffset(offset)
        wvHours.setTextXOffset(offset)
        wvMinutes.setTextXOffset(offset)
        wvSeconds.setTextXOffset(offset)
    }

    fun setCyclic(cyclic: Boolean) {
        wvYear.setCyclic(cyclic)
        wvMonth.setCyclic(cyclic)
        wvDay.setCyclic(cyclic)
        wvHours.setCyclic(cyclic)
        wvMinutes.setCyclic(cyclic)
        wvSeconds.setCyclic(cyclic)
    }

    fun isCenterLabel(isCenterLabel: Boolean?) {
        wvDay.isCenterLabel(isCenterLabel)
        wvMonth.isCenterLabel(isCenterLabel)
        wvYear.isCenterLabel(isCenterLabel)
        wvHours.isCenterLabel(isCenterLabel)
        wvMinutes.isCenterLabel(isCenterLabel)
        wvSeconds.isCenterLabel(isCenterLabel)
    }


    fun setTextColorOut(textColorOut: Int) {
        wvDay.setTextColorOut(textColorOut)
        wvMonth.setTextColorOut(textColorOut)
        wvYear.setTextColorOut(textColorOut)
        wvHours.setTextColorOut(textColorOut)
        wvMinutes.setTextColorOut(textColorOut)
        wvSeconds.setTextColorOut(textColorOut)
    }

    fun setTextColorCenter(textColorCenter: Int) {
        wvDay.setTextColorCenter(textColorCenter)
        wvMonth.setTextColorCenter(textColorCenter)
        wvYear.setTextColorCenter(textColorCenter)
        wvHours.setTextColorCenter(textColorCenter)
        wvMinutes.setTextColorCenter(textColorCenter)
        wvSeconds.setTextColorCenter(textColorCenter)
    }

    fun setDividerColor(dividerColor: Int) {
        wvDay.setDividerColor(dividerColor)
        wvMonth.setDividerColor(dividerColor)
        wvYear.setDividerColor(dividerColor)
        wvHours.setDividerColor(dividerColor)
        wvMinutes.setDividerColor(dividerColor)
        wvSeconds.setDividerColor(dividerColor)
    }

    fun setDividerType(dividerType: DividerType) {
        wvDay.setDividerType(dividerType)
        wvMonth.setDividerType(dividerType)
        wvYear.setDividerType(dividerType)
        wvHours.setDividerType(dividerType)
        wvMinutes.setDividerType(dividerType)
        wvSeconds.setDividerType(dividerType)

    }

    fun setMaxAddHeight(height: Int) {
        if (height < 0) {
            throw IllegalArgumentException("height cannot less than 0")
        }
        wvDay.setMaxAddHeight(height)
        wvMonth.setMaxAddHeight(height)
        wvYear.setMaxAddHeight(height)
        wvHours.setMaxAddHeight(height)
        wvMinutes.setMaxAddHeight(height)
        wvSeconds.setMaxAddHeight(height)
    }

    fun setLineSpacingMultiplier(lineSpacingMultiplier: Float) {
        wvDay.setLineSpacingMultiplier(lineSpacingMultiplier)
        wvMonth.setLineSpacingMultiplier(lineSpacingMultiplier)
        wvYear.setLineSpacingMultiplier(lineSpacingMultiplier)
        wvHours.setLineSpacingMultiplier(lineSpacingMultiplier)
        wvMinutes.setLineSpacingMultiplier(lineSpacingMultiplier)
        wvSeconds.setLineSpacingMultiplier(lineSpacingMultiplier)
    }


    fun setTextGravity(gravity: Int) {
        wvDay.setGravity(gravity)
        wvMonth.setGravity(gravity)
        wvYear.setGravity(gravity)
        wvHours.setGravity(gravity)
        wvMinutes.setGravity(gravity)
        wvSeconds.setGravity(gravity)
    }

    fun setDisplayStyle(type: BooleanArray) {
        if (type.size != 6) {
            throw RuntimeException("type[] length is not 6")
        }
        wvYear.visibility = if (type[0]) View.VISIBLE else View.GONE
        wvMonth.visibility = if (type[1]) View.VISIBLE else View.GONE
        wvDay.visibility = if (type[2]) View.VISIBLE else View.GONE
        wvHours.visibility = if (type[3]) View.VISIBLE else View.GONE
        wvMinutes.visibility = if (type[4]) View.VISIBLE else View.GONE
        wvSeconds.visibility = if (type[5]) View.VISIBLE else View.GONE
    }
}
