package com.jhj.prompt.options.utils

import java.util.*

object ChinaDate {

    private val lunarInfo = longArrayOf(0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950,
            0x16554, 0x056a0, 0x09ad0, 0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540,
            0x0d6a0, 0x0ada2, 0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54,
            0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60,
            0x186e3, 0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0,
            0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573,
            0x052d0, 0x0a9a8, 0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260,
            0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250,
            0x0d558, 0x0b540, 0x0b5a0, 0x195a6, 0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50,
            0x06d40, 0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58,
            0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0,
            0x0abb7, 0x025d0, 0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0,
            0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6,
            0x0a4e0, 0x0d260, 0x0ea65, 0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0,
            0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0,
            0x0aa50, 0x1b255, 0x06d20, 0x0ada0)
    private val nStr1 = arrayOf("", "正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊")
    private val Gan = arrayOf("甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸")
    private val Zhi = arrayOf("子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥")

    /**
     * 传回农历
     *
     * @param y 年闰月的天数
     * @return 农历
     */
    fun leapDays(y: Int): Int {
        return if (leapMonth(y) != 0) {
            if (lunarInfo[y - 1900] and 0x10000 != 0L)
                30
            else
                29
        } else
            0
    }

    /**
     * 传回农历
     *
     * @param y 年闰哪个月 1-12 , 没闰传回 0
     * @return 农历
     */
    fun leapMonth(y: Int): Int {
        return (lunarInfo[y - 1900] and 0xf).toInt()
    }

    /**
     * 传回农历 y
     *
     * @param y y年m月的总天数
     * @param m y年m月的总天数
     * @return 农历
     */
    fun monthDays(y: Int, m: Int): Int {
        return if (lunarInfo[y - 1900] and (0x10000 shr m).toLong() == 0L)
            29
        else
            30
    }

    private fun getChinaDate(day: Int): String {
        var a = ""
        if (day == 10)
            return "初十"
        if (day == 20)
            return "二十"
        if (day == 30)
            return "三十"
        val two = day / 10
        if (two == 0)
            a = "初"
        if (two == 1)
            a = "十"
        if (two == 2)
            a = "廿"
        if (two == 3)
            a = "三"
        val one = day % 10
        when (one) {
            1 -> a += "一"
            2 -> a += "二"
            3 -> a += "三"
            4 -> a += "四"
            5 -> a += "五"
            6 -> a += "六"
            7 -> a += "七"
            8 -> a += "八"
            9 -> a += "九"
        }
        return a
    }



    /**
     * @param lunarYear 农历年份
     * @return String of Ganzhi: 甲子年
     * 甲乙丙丁戊己庚辛壬癸
     * 子丑寅卯辰巳无为申酉戌亥
     */
    private fun getLunarYearText(lunarYear: Int): String {
        return Gan[(lunarYear - 4) % 10] + Zhi[(lunarYear - 4) % 12] + "年"
    }


    fun getYears(startYear: Int, endYear: Int): ArrayList<String> {
        val years = ArrayList<String>()
        for (i in startYear until endYear) {
            years.add(String.format("%s(%d)", getLunarYearText(i), i))
        }
        return years
    }

    /**
     * 获取year年的所有月份
     *
     * @param year 年
     * @return 月份列表
     */
    fun getMonths(year: Int): ArrayList<String> {
        val baseMonths = ArrayList<String>()
        for (i in 1 until nStr1.size) {
            baseMonths.add(nStr1[i] + "月")
        }
        if (leapMonth(year) != 0) {
            baseMonths.add(leapMonth(year), "闰" + nStr1[leapMonth(year)] + "月")
        }
        return baseMonths
    }

    /**
     * 获取每月农历显示名称
     *
     * @param maxDay 天
     * @return 名称列表
     */
    fun getLunarDays(maxDay: Int): ArrayList<String> {
        val days = ArrayList<String>()
        for (i in 1..maxDay) {
            days.add(getChinaDate(i))
        }
        return days
    }

}