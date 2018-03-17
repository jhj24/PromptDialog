package com.jhj.prompt.options.wheel

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import com.jhj.prompt.R
import com.jhj.prompt.options.adapter.CommonWheelAdapter
import com.jhj.prompt.options.interfaces.OnItemSelectedListener
import com.jhj.prompt.options.lib.WheelView
import com.jhj.prompt.options.utils.DividerType
import kotlinx.android.synthetic.main.layout_pickerview_options.view.*

/**
 * 通用选择器
 */
class OptionsWheel<in T>(mContext: Context) {
    var view: View = LayoutInflater.from(mContext).inflate(R.layout.layout_pickerview_options, null)
    private var wvOptions1: WheelView = view.options1
    private var wvOptions2: WheelView = view.options2
    private var wvOptions3: WheelView = view.options3

    //联动数据列表1,2,3
    private var mOptions1Items: List<T>? = null
    private var mOptions2Items: List<List<T>>? = null
    private var mOptions3Items: List<List<List<T>>>? = null
    //不联动下的数据列表2,3
    private var nOptions2Items: List<T>? = null
    private var nOptions3Items: List<T>? = null
    private var linkage = true

    //文字的颜色和分割线的颜色
    private var textColorOut: Int = 0
    private var textColorCenter: Int = 0
    private var dividerColor: Int = 0

    // 条目间距倍数
    private var lineSpacingMultiplier = 1.6f


    /**
     * 返回当前选中的结果对应的位置数组 因为支持三级联动效果，分三个级别索引，0，1，2。
     * 在快速滑动未停止时，点击确定按钮，会进行判断，如果匹配数据越界，则设为0，防止index出错导致崩溃。
     *
     * @return 索引数组
     */
    val currentItems: IntArray
        get() {
            val currentItems = IntArray(3)
            currentItems[0] = wvOptions1.currentItem

            if (mOptions2Items != null && mOptions2Items?.size as Int > 0) {
                currentItems[1] = if (wvOptions2.currentItem > mOptions2Items?.get(currentItems[0])?.size as Int - 1) 0 else wvOptions2.currentItem
            } else {
                currentItems[1] = wvOptions2.currentItem
            }

            if (mOptions3Items != null && mOptions3Items?.size as Int > 0) {
                currentItems[2] = if (wvOptions3.currentItem > mOptions3Items?.get(currentItems[0])?.get(currentItems[1])?.size as Int - 1) 0 else wvOptions3.currentItem
            } else {
                currentItems[2] = wvOptions3.currentItem
            }

            return currentItems
        }


    fun setPicker(options1Items: List<T>,
                  options2Items: List<List<T>>?,
                  options3Items: List<List<List<T>>>?) {
        this.mOptions1Items = options1Items
        this.mOptions2Items = options2Items
        this.mOptions3Items = options3Items
        var len = CommonWheelAdapter.DEFAULT_LENGTH
        if (this.mOptions3Items == null)
            len = 8
        if (this.mOptions2Items == null)
            len = 12
        // 选项1
        wvOptions1.setAdapter(CommonWheelAdapter(mOptions1Items, len))// 设置显示数据
        wvOptions1.currentItem = 0// 初始化时显示的数据
        // 选项2
        if (mOptions2Items != null)
            wvOptions2.setAdapter(CommonWheelAdapter(mOptions2Items?.get(0)))// 设置显示数据
        wvOptions2.currentItem = wvOptions1.currentItem// 初始化时显示的数据
        // 选项3
        if (mOptions3Items != null)
            wvOptions3.setAdapter(CommonWheelAdapter(mOptions3Items?.get(0)?.get(0)))// 设置显示数据
        wvOptions3.currentItem = wvOptions3.currentItem
        wvOptions1.setIsOptions(true)
        wvOptions2.setIsOptions(true)
        wvOptions3.setIsOptions(true)

        if (this.mOptions2Items == null) {
            wvOptions2.visibility = View.GONE
        } else {
            wvOptions2.visibility = View.VISIBLE
        }
        if (this.mOptions3Items == null) {
            wvOptions3.visibility = View.GONE
        } else {
            wvOptions3.visibility = View.VISIBLE
        }

        val wheelListenerOptions2 = object : OnItemSelectedListener {
            override fun onItemSelected(index: Int) {
                var position = index
                if (mOptions3Items != null) {
                    var opt1Select = wvOptions1.currentItem
                    opt1Select = if (opt1Select >= mOptions3Items?.size as Int - 1) mOptions3Items?.size as Int - 1 else opt1Select
                    position = if (position >= mOptions2Items?.get(opt1Select)?.size ?: 0 - 1) mOptions2Items?.get(opt1Select)?.size
                            ?: 0-1 else position
                    var opt3 = wvOptions3.currentItem//上一个opt3的选中位置
                    //新opt3的位置，判断如果旧位置没有超过数据范围，则沿用旧位置，否则选中最后一项
                    opt3 = if (opt3 >= mOptions3Items?.get(opt1Select)?.get(position)?.size ?: 0 - 1) mOptions3Items?.get(opt1Select)?.get(position)?.size
                            ?: 0-1 else opt3

                    wvOptions3.setAdapter(CommonWheelAdapter(mOptions3Items?.get(wvOptions1.currentItem)?.get(position)))
                    wvOptions3.currentItem = opt3

                }
            }
        }

        // 联动监听器
        val wheelListenerOptions1 = object : OnItemSelectedListener {
            override fun onItemSelected(index: Int) {
                var opt2Select = 0
                if (mOptions2Items != null) {
                    opt2Select = wvOptions2.currentItem//上一个opt2的选中位置
                    //新opt2的位置，判断如果旧位置没有超过数据范围，则沿用旧位置，否则选中最后一项
                    opt2Select = if (opt2Select >= mOptions2Items?.get(index)?.size as Int - 1) mOptions2Items?.get(index)?.size as Int - 1 else opt2Select

                    wvOptions2.setAdapter(CommonWheelAdapter(mOptions2Items?.get(index)))
                    wvOptions2.currentItem = opt2Select
                }
                if (mOptions3Items != null) {
                    wheelListenerOptions2.onItemSelected(opt2Select)
                }
            }
        }


        // 添加联动监听
        if (options2Items != null && linkage)
            wvOptions1.setOnItemSelectedListener(wheelListenerOptions1)
        if (options3Items != null && linkage)
            wvOptions2.setOnItemSelectedListener(wheelListenerOptions2)
    }


    //不联动情况下
    fun setNPicker(options1Items: List<T>,
                   options2Items: List<T>?,
                   options3Items: List<T>?) {
        this.mOptions1Items = options1Items
        this.nOptions2Items = options2Items
        this.nOptions3Items = options3Items
        var len = CommonWheelAdapter.DEFAULT_LENGTH
        if (this.nOptions3Items == null)
            len = 8
        if (this.nOptions2Items == null)
            len = 12
        // 选项1
        wvOptions1.setAdapter(CommonWheelAdapter(mOptions1Items, len))// 设置显示数据
        wvOptions1.currentItem = 0// 初始化时显示的数据
        // 选项2
        if (nOptions2Items != null)
            wvOptions2.setAdapter(CommonWheelAdapter(nOptions2Items))// 设置显示数据
        wvOptions2.currentItem = wvOptions1.currentItem// 初始化时显示的数据
        // 选项3
        if (nOptions3Items != null)
            wvOptions3.setAdapter(CommonWheelAdapter(nOptions3Items))// 设置显示数据
        wvOptions3.currentItem = wvOptions3.currentItem
        wvOptions1.setIsOptions(true)
        wvOptions2.setIsOptions(true)
        wvOptions3.setIsOptions(true)

        if (this.nOptions2Items == null) {
            wvOptions2.visibility = View.GONE
        } else {
            wvOptions2.visibility = View.VISIBLE
        }
        if (this.nOptions3Items == null) {
            wvOptions3.visibility = View.GONE
        } else {
            wvOptions3.visibility = View.VISIBLE
        }
    }


    fun setTextContentSize(textSize: Float) {
        wvOptions1.setTextSize(textSize)
        wvOptions2.setTextSize(textSize)
        wvOptions3.setTextSize(textSize)
    }

    private fun setTextColorOut() {
        wvOptions1.setTextColorOut(textColorOut)
        wvOptions2.setTextColorOut(textColorOut)
        wvOptions3.setTextColorOut(textColorOut)

    }

    private fun setTextColorCenter() {
        wvOptions1.setTextColorCenter(textColorCenter)
        wvOptions2.setTextColorCenter(textColorCenter)
        wvOptions3.setTextColorCenter(textColorCenter)

    }

    private fun setDividerColor() {
        wvOptions1.setDividerColor(dividerColor)
        wvOptions2.setDividerColor(dividerColor)
        wvOptions3.setDividerColor(dividerColor)
    }
    


    private fun setLineSpacingMultiplier() {
        wvOptions1.setLineSpacingMultiplier(lineSpacingMultiplier)
        wvOptions2.setLineSpacingMultiplier(lineSpacingMultiplier)
        wvOptions3.setLineSpacingMultiplier(lineSpacingMultiplier)

    }

    fun setMaxAddHeight(height: Int) {
        if (height < 0) {
            throw IllegalArgumentException("height cannot less than 0")
        }
        wvOptions1.setMaxAddHeight(height)
        wvOptions2.setMaxAddHeight(height)
        wvOptions3.setMaxAddHeight(height)
    }

    fun setItemNum(num: Int) {
        if (num < 0) {
            throw IllegalArgumentException("Item num cannot less than 0")
        }
        wvOptions1.setItemNum(num)
        wvOptions2.setItemNum(num)
        wvOptions3.setItemNum(num)
    }

    /**
     * 设置选项的单位
     *
     * @param label1 单位
     * @param label2 单位
     * @param label3 单位
     */
    fun setLabels(label1: String?, label2: String?, label3: String?) {
        if (label1 != null)
            wvOptions1.setLabel(label1)
        if (label2 != null)
            wvOptions2.setLabel(label2)
        if (label3 != null)
            wvOptions3.setLabel(label3)
    }

    /**
     * 设置x轴偏移量
     */
    fun setTextXOffset(offset: Int) {
        wvOptions1.setTextXOffset(offset)
        wvOptions2.setTextXOffset(offset)
        wvOptions3.setTextXOffset(offset)
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic 是否循环
     */
    fun setCyclic(cyclic: Boolean) {
        wvOptions1.setCyclic(cyclic)
        wvOptions2.setCyclic(cyclic)
        wvOptions3.setCyclic(cyclic)
    }

    /**
     * 设置字体样式
     *
     * @param font 系统提供的几种样式
     */
    fun setTypeface(font: Typeface) {
        wvOptions1.setTypeface(font)
        wvOptions2.setTypeface(font)
        wvOptions3.setTypeface(font)
    }


    fun setCurrentItems(option1: Int, option2: Int, option3: Int) {
        if (linkage) {
            itemSelected(option1, option2, option3)
        }
        wvOptions1.currentItem = option1
        wvOptions2.currentItem = option2
        wvOptions3.currentItem = option3
    }

    private fun itemSelected(opt1Select: Int, opt2Select: Int, opt3Select: Int) {
        if (mOptions2Items != null) {
            wvOptions2.setAdapter(CommonWheelAdapter(mOptions2Items?.get(opt1Select)))
            wvOptions2.currentItem = opt2Select
        }
        if (mOptions3Items != null) {
            wvOptions3.setAdapter(CommonWheelAdapter(mOptions3Items?.get(opt1Select)?.get(opt2Select)))
            wvOptions3.currentItem = opt3Select
        }
    }

    /**
     * 设置间距倍数,但是只能在1.2-2.0f之间
     *
     * @param lineSpacingMultiplier
     */
    fun setLineSpacingMultiplier(lineSpacingMultiplier: Float) {
        this.lineSpacingMultiplier = lineSpacingMultiplier
        setLineSpacingMultiplier()
    }


    /**
     * 设置分割线的颜色
     *
     * @param dividerColor
     */
    fun setDividerColor(dividerColor: Int) {
        this.dividerColor = dividerColor
        setDividerColor()
    }

    /**
     * 设置分割线的类型
     *
     * @param dividerType
     */
    fun setDividerType(dividerType: DividerType) {
        wvOptions1.setDividerType(dividerType)
        wvOptions2.setDividerType(dividerType)
        wvOptions3.setDividerType(dividerType)
    }

    /**
     * 设置分割线之间的文字的颜色
     *
     * @param textColorCenter
     */
    fun setTextColorCenter(textColorCenter: Int) {
        this.textColorCenter = textColorCenter
        setTextColorCenter()
    }

    /**
     * 设置分割线以外文字的颜色
     *
     * @param textColorOut
     */
    fun setTextColorOut(textColorOut: Int) {
        this.textColorOut = textColorOut
        setTextColorOut()
    }

    /**
     * Label 是否只显示中间选中项的
     *
     * @param isCenterLabel
     */

    fun isCenterLabel(isCenterLabel: Boolean?) {
        wvOptions1.isCenterLabel(isCenterLabel)
        wvOptions2.isCenterLabel(isCenterLabel)
        wvOptions3.isCenterLabel(isCenterLabel)
    }

    fun setTextGravity(gravity: Int) {
        wvOptions1.setGravity(gravity)
        wvOptions2.setGravity(gravity)
        wvOptions3.setGravity(gravity)
    }


}
