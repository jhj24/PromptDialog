package com.jhj.prompt.options.adapter



/**
 * 时间选择器的适配器.
 */
class TimeWheelAdapter @JvmOverloads constructor(
        private val minValue: Int = DEFAULT_MIN_VALUE,
        private val maxValue: Int = DEFAULT_MAX_VALUE) : WheelAdapter<Any> {

    companion object {
        //默认最大值
        private const val DEFAULT_MAX_VALUE = 9

        //默认最小值
        private const val DEFAULT_MIN_VALUE = 0
    }

    override val itemsCount: Int
        get() = maxValue - minValue + 1

    override fun getItem(index: Int): Any? {
        return if (index in 0..(itemsCount - 1)) {
            minValue + index
        } else 0
    }

    override fun indexOf(o: Any?): Int {
        return try {
            o as Int - minValue
        } catch (e: Exception) {
            -1
        }

    }
}
