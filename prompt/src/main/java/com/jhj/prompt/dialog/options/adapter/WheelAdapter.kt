package com.jhj.prompt.dialog.options.adapter

interface WheelAdapter<T> {
    /**
     * Item数量
     */
    val itemsCount: Int

    /**
     * 通过索引获取wheel item。
     */
    fun getItem(index: Int): T?

    /**
     * 获取最大item长度。 它用于确定车轮宽度。如果返回-1，则将使用默认轮宽。
     */
    fun indexOf(o: T?): Int?
}
