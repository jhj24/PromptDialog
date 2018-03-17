package com.jhj.prompt.options.adapter


/**
 * 简单的适配器
 */
class CommonWheelAdapter<T> constructor(private val items: List<T>?, private val length: Int = DEFAULT_LENGTH) : WheelAdapter<T> {


    companion object {
        // 默认item长度
        const val DEFAULT_LENGTH = 4
    }

    override val itemsCount: Int
        get() = items?.size ?: 0

    override fun getItem(index: Int): T? {
        return if (index >= 0 && index < items?.size as Int) {
            items[index]
        } else null
    }

    override fun indexOf(o: T?): Int? {
        return items?.indexOf(o )
    }

}
