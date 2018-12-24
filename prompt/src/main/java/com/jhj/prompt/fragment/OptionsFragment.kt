package com.jhj.prompt.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import com.jhj.prompt.R
import com.jhj.prompt.fragment.base.BaseBuilder
import com.jhj.prompt.fragment.base.BaseDialogFragment
import com.jhj.prompt.fragment.base.Constants
import com.jhj.prompt.fragment.base.PromptConfig
import com.jhj.prompt.fragment.options.interfaces.OnOptionsSelectedListener
import com.jhj.prompt.fragment.options.utils.DividerType
import com.jhj.prompt.fragment.options.wheel.OptionsWheel
import kotlinx.android.synthetic.main.layout_pickerview_options.view.*
import kotlinx.android.synthetic.main.layout_pickerview_topbar.view.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.textColor


/**
 * 自定义选择器
 * Created by jhj on 2018-3-6 0006.
 */

class OptionsFragment<T> : BaseDialogFragment() {

    private lateinit var wheel: OptionsWheel<T>

    private var titleText: String = ""
    private var titleSize: Float = PromptConfig.PICKER_TEXT_SIZE_TITLE
    private var titleColor: Int = PromptConfig.PICKER_TEXT_COLOR_TITLE
    private var submitText: String? = "确定"
    private var submitColor: Int = PromptConfig.PICKER_TEXT_COLOR_BUTTON
    private var cancelColor: Int = PromptConfig.PICKER_TEXT_COLOR_BUTTON
    private var cancelText: String? = "取消"
    private var buttonSize: Float = PromptConfig.PICKER_TEXT_SIZE_BOTTON
    private var topBarBackground: Int = PromptConfig.PICKER_TOP_BAR_BACKGROUND
    private var backgroundResource: Int = PromptConfig.PICKER_OPTIONS_BACKGROUND
    private var optionsTextSize: Float = PromptConfig.PICKER_TEXT_SIZE_CENTER
    private var onlyCenterLabel: Boolean = false
    private var colorOut: Int = PromptConfig.PICKER_TEXT_COLOR_OUT
    private var colorCenter: Int = PromptConfig.PICKER_TEXT_COLOR_CENTER
    private var dividerColor: Int = PromptConfig.PICKER_DIVIDER_COLOR
    private var dividerType: DividerType? = PromptConfig.PICKER_DIVIDER_TYPE
    private var itemNum: Int = PromptConfig.PICKER_SHOW_ITEM_NUM
    private var optionsLabels: Array<out String>? = arrayOf()
    private var isCyclic: Boolean = true
    private var textGravity: Int = PromptConfig.PICKER_OPTIONS_TEXT_GRAVITY
    private var displayStyle: BooleanArray? = booleanArrayOf()
    private var xOffset: Int = PromptConfig.PICKER_X_OFFSET
    private var spacingRatio: Float = PromptConfig.PICKER_LINE_SPACEING_RATIO
    private var extraHeight: Int = PromptConfig.PICKER_EXTRA_HEIGHT
    private var cancelListener: OnOptionsSelectedListener? = null
    private var submitListener: OnOptionsSelectedListener? = null

    private var options1 = 0
    private var options2 = 0
    private var options3 = 0
    private var isLinked = false
    private var options1Items: ArrayList<T> = arrayListOf()
    private var options2Items: ArrayList<T>? = null
    private var options3Items: ArrayList<T>? = null
    private var optionsLinked1Items: ArrayList<T> = arrayListOf()
    private var optionsLinked2Items: ArrayList<ArrayList<T>>? = null
    private var optionsLinked3Items: ArrayList<ArrayList<ArrayList<T>>>? = null


    override val layoutRes: Int
        get() = R.layout.layout_pickerview_options

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGravity = Gravity.BOTTOM
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wheel = OptionsWheel(view)

        setButtonStyle(wheel.view)
        if (isLinked) {
            wheel.setPicker(optionsLinked1Items, optionsLinked2Items, optionsLinked3Items)
        } else {
            wheel.setNPicker(options1Items, options2Items, options3Items)
        }
        wheel.setCurrentItems(options1, options2, options3)
        wheel.setTextContentSize(optionsTextSize)
        wheel.setCyclic(isCyclic)
        wheel.setDividerColor(dividerColor)
        wheel.setDividerType(dividerType ?: config.pickerDividerType)
        wheel.setItemNum(itemNum)
        wheel.setLabels(optionsLabels?.get(0), optionsLabels?.get(1), optionsLabels?.get(2))
        wheel.setLineSpacingMultiplier(spacingRatio)
        wheel.setMaxAddHeight(extraHeight)
        wheel.setTextXOffset(xOffset)
        wheel.setTextColorCenter(colorCenter)
        wheel.setTextColorOut(colorOut)
        wheel.isCenterLabel(onlyCenterLabel)
        wheel.setTextGravity(textGravity)
        wheel.setTextXOffset(xOffset)
    }


    override fun initParams(bundle: Bundle?) {
        super.initParams(bundle)
        bundle?.let {
            titleText = it.getString(Constants.TITLE, "")
            titleSize = it.getFloat(Constants.TITLE_SIZE, config.pickerTextSizeTitle)
            titleColor = it.getInt(Constants.TITLE_COLOR, config.pickerTextColorTitle)
            submitText = it.getString(Constants.SUBMIT_TEXT, "确定")
            submitColor = it.getInt(Constants.SUBMIT_TEXT_COLOR, config.pickerTextColorButton)
            cancelColor = it.getInt(Constants.CANCEL_TEXT_COLOR, config.pickerTextColorButton)
            cancelText = it.getString(Constants.CANCEL_TEXT, "取消")
            buttonSize = it.getFloat(Constants.BUTTON_SIZE, config.pickerTextSizeButton)
            topBarBackground = it.getInt(Constants.TOPBAR_BACKGROUND_RESOURCE, config.pickerTopBarBackground)
            backgroundResource = it.getInt(Constants.OPTIONS_BACKGROUND_RESOURCE, config.pickerOptionsBackground)
            cancelListener = it.getSerializable(Constants.LISTENER_CANCEL_CLICK) as? OnOptionsSelectedListener
            submitListener = it.getSerializable(Constants.LISTENER_SUBMIT_CLICK) as? OnOptionsSelectedListener
            optionsTextSize = it.getFloat(Constants.OPTIONS_TEXT_SIZE, config.pickerTextSizeCenter)
            onlyCenterLabel = it.getBoolean(Constants.ONLY_CENTER_LABEL, false)
            colorOut = it.getInt(Constants.TEXT_COLOR_OUT, config.pickerTextColorOUT)
            colorCenter = it.getInt(Constants.TEXT_COLOR_CENTER, config.pickerTextColorCenter)
            dividerColor = it.getInt(Constants.DIVIDER_COLOR, config.pickerDividerColor)
            dividerType = it.getSerializable(Constants.DIVIDER_TYPE) as? DividerType
            itemNum = it.getInt(Constants.ITEM_NUM, config.pickerItemNum)
            optionsLabels = it.getStringArray(Constants.OPTIONS_LABELS)
            isCyclic = it.getBoolean(Constants.IS_CYCLIC, true)
            displayStyle = it.getBooleanArray(Constants.DISPLAY_STYLE)
            xOffset = it.getInt(Constants.X_OFFSET, config.pickerXOffset)
            spacingRatio = it.getFloat(Constants.SPACING_RATIO, config.pickerLineSpacingRatio)
            extraHeight = it.getInt(Constants.EXTRA_HEIGHT, config.pickerExtraHeight)
            textGravity = it.getInt(Constants.MESSAGE_GRAVITY, config.pickerOptionsTextGravity)
            options1 = it.getInt(Constants.OPTIONS_SELECT_ONE, 0)
            options2 = it.getInt(Constants.OPTIONS_SELECT_TWO, 0)
            options3 = it.getInt(Constants.OPTIONS_SELECT_THREE, 0)
            isLinked = it.getBoolean(Constants.OPTIONS_IS_LINKED)
            if (isLinked) {
                optionsLinked1Items = it.getSerializable(Constants.OPTIONS_LINKED_ITEMS_ONE) as ArrayList<T>
                optionsLinked2Items = it.getSerializable(Constants.OPTIONS_LINKED_ITEMS_TWO) as? ArrayList<ArrayList<T>>
                optionsLinked3Items = it.getSerializable(Constants.OPTIONS_LINKED_ITEMS_THREE) as? ArrayList<ArrayList<ArrayList<T>>>
            } else {
                options1Items = it.getSerializable(Constants.OPTIONS_ITEMS_ONE) as ArrayList<T>
                options2Items = it.getSerializable(Constants.OPTIONS_ITEMS_TWO) as? ArrayList<T>
                options3Items = it.getSerializable(Constants.OPTIONS_ITEMS_THREE) as? ArrayList<T>
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun setButtonStyle(view: View) {
        view.rv_topbar.backgroundResource = topBarBackground
        view.optionspicker.backgroundResource = backgroundResource
        view.tv_option_title.text = titleText
        view.tv_option_title.textColor = titleColor
        view.tv_option_title.textSize = titleSize

        view.btn_option_cancel.let { btnCancel ->
            btnCancel.text = cancelText
            btnCancel.textSize = buttonSize
            btnCancel.setTextColor(ContextCompat.getColorStateList(requireContext(), cancelColor))
            btnCancel.setOnClickListener {
                val array = wheel.currentItems
                cancelListener?.onOptionsSelect(array[0], array[1], array[2])
                dismiss()
            }
        }

        view.btn_option_submit.let { btnSubmit ->
            btnSubmit.text = submitText
            btnSubmit.textSize = buttonSize
            btnSubmit.setTextColor(ContextCompat.getColorStateList(requireContext(), submitColor))
            btnSubmit.setOnClickListener {
                val array = wheel.currentItems
                submitListener?.onOptionsSelect(array[0],array[1],array[2])
                dismiss()
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.let {
            it.putString(Constants.TITLE, titleText)
            it.putInt(Constants.TITLE_COLOR, titleColor)
            it.putFloat(Constants.TITLE_SIZE, titleSize)
            it.putString(Constants.SUBMIT_TEXT, submitText)
            it.putInt(Constants.SUBMIT_TEXT_COLOR, submitColor)
            it.putString(Constants.CANCEL_TEXT, cancelText)
            it.putInt(Constants.CANCEL_TEXT_COLOR, cancelColor)
            it.putFloat(Constants.BUTTON_SIZE, buttonSize)
            it.putInt(Constants.TOPBAR_BACKGROUND_RESOURCE, topBarBackground)
            it.putSerializable(Constants.LISTENER_SUBMIT_CLICK, submitListener)
            it.putSerializable(Constants.LISTENER_CANCEL_CLICK, cancelListener)
            it.putInt(Constants.OPTIONS_BACKGROUND_RESOURCE, backgroundResource)
            it.putFloat(Constants.OPTIONS_TEXT_SIZE, optionsTextSize)
            it.putBoolean(Constants.ONLY_CENTER_LABEL, onlyCenterLabel)
            it.putInt(Constants.TEXT_COLOR_OUT, colorOut)
            it.putInt(Constants.TEXT_COLOR_CENTER, colorCenter)
            it.putInt(Constants.DIVIDER_COLOR, dividerColor)
            it.putSerializable(Constants.DIVIDER_TYPE, dividerType)
            it.putInt(Constants.ITEM_NUM, itemNum)
            it.putStringArray(Constants.OPTIONS_LABELS, optionsLabels)
            it.putBoolean(Constants.IS_CYCLIC, isCyclic)
            it.putBooleanArray(Constants.DISPLAY_STYLE, displayStyle)
            it.putInt(Constants.X_OFFSET, xOffset)
            it.putFloat(Constants.SPACING_RATIO, spacingRatio)
            it.putInt(Constants.EXTRA_HEIGHT, extraHeight)
            it.putInt(Constants.OPTIONS_SELECT_ONE, wheel.currentItems[0])
            it.putInt(Constants.OPTIONS_SELECT_TWO, wheel.currentItems[1])
            it.putInt(Constants.OPTIONS_SELECT_THREE, wheel.currentItems[2])
            it.putBoolean(Constants.OPTIONS_IS_LINKED, isLinked)
            if (isLinked) {
                it.putSerializable(Constants.OPTIONS_LINKED_ITEMS_ONE, optionsLinked1Items)
                it.putSerializable(Constants.OPTIONS_LINKED_ITEMS_TWO, optionsLinked2Items)
                it.putSerializable(Constants.OPTIONS_LINKED_ITEMS_THREE, optionsLinked3Items)
            } else {
                it.putSerializable(Constants.OPTIONS_ITEMS_ONE, options1Items)
                it.putSerializable(Constants.OPTIONS_ITEMS_TWO, options2Items)
                it.putSerializable(Constants.OPTIONS_ITEMS_THREE, options3Items)
            }
        }
    }


    class Builder<T>(val context: Context) : BaseBuilder<Builder<T>>(context) {

        private val mFragment = OptionsFragment<T>()

        override val fragment: OptionsFragment<T>
            get() = mFragment

        fun setTitle(title: String): Builder<T> {
            arg.putString(Constants.TITLE, title)
            return this
        }

        fun setTitleSize(size: Float): Builder<T> {
            arg.putFloat(Constants.TITLE_SIZE, size)
            return this
        }

        fun setTitleColor(@ColorRes color: Int): Builder<T> {
            arg.putInt(Constants.TITLE_COLOR, color)
            return this
        }

        fun setCancel(cancel: String): Builder<T> {
            arg.putString(Constants.CANCEL_TEXT, cancel)
            return this
        }

        fun setCancelColor(@DrawableRes color: Int): Builder<T> {
            arg.putInt(Constants.CANCEL_TEXT_COLOR, color)
            return this
        }

        fun setSubmit(submit: String): Builder<T> {
            arg.putString(Constants.SUBMIT_TEXT, submit)
            return this
        }

        fun setSubmitColor(@DrawableRes color: Int): Builder<T> {
            arg.putInt(Constants.SUBMIT_TEXT_COLOR, color)
            return this
        }

        fun setButtonSize(size: Float): Builder<T> {
            arg.putFloat(Constants.BUTTON_SIZE, size)
            return this
        }


        fun setTopBarBackgroundResource(@DrawableRes resource: Int): Builder<T> {
            arg.putInt(Constants.TOPBAR_BACKGROUND_RESOURCE, resource)
            return this
        }

        fun setOptionsBackgroundResource(@DrawableRes resource: Int): Builder<T> {
            arg.putInt(Constants.OPTIONS_BACKGROUND_RESOURCE, resource)
            return this
        }

        fun setOptionsTextSize(size: Float): Builder<T> {
            arg.putFloat(Constants.OPTIONS_TEXT_SIZE, size)
            return this
        }

        fun setOnlyCenterLabel(centerLabel: Boolean): Builder<T> {
            arg.putBoolean(Constants.ONLY_CENTER_LABEL, centerLabel)
            return this
        }

        fun setDividerColor(@ColorRes color: Int): Builder<T> {
            arg.putInt(Constants.DIVIDER_COLOR, color)
            return this
        }

        fun setTextColorOut(color: Int): Builder<T> {
            arg.putInt(Constants.TEXT_COLOR_OUT, color)
            return this
        }

        fun setTextColorCenter(color: Int): Builder<T> {
            arg.putInt(Constants.TEXT_COLOR_CENTER, color)
            return this
        }

        fun setOptionsTextGravity(gravity: Int): Builder<T> {
            arg.putInt(Constants.MESSAGE_GRAVITY, gravity)
            return this
        }

        fun setDividerType(type: DividerType): Builder<T> {
            arg.putSerializable(Constants.DIVIDER_TYPE, type)
            return this
        }

        fun setShowItemNum(num: Int): Builder<T> {
            arg.putInt(Constants.ITEM_NUM, num)
            return this
        }

        fun setLabels(label1: String?, label2: String?, label3: String?): Builder<T> {
            arg.putStringArray(Constants.OPTIONS_LABELS, arrayOf(label1, label2, label3))
            return this
        }

        fun setCyclic(cyclic: Boolean): Builder<T> {
            arg.putBoolean(Constants.IS_CYCLIC, cyclic)
            return this
        }

        fun setTextXOffset(offset: Int): Builder<T> {
            arg.putInt(Constants.X_OFFSET, offset)
            return this
        }

        fun setItemsSpacingRatio(spacingRatio: Float): Builder<T> {
            arg.putFloat(Constants.SPACING_RATIO, spacingRatio)
            return this
        }

        fun setExtraHeight(height: Int): Builder<T> {
            arg.putInt(Constants.EXTRA_HEIGHT, height)
            return this
        }


        fun setSelectOptions(option1: Int): Builder<T> {
            setSelectOptions(option1, 0)
            return this
        }

        fun setSelectOptions(option1: Int, option2: Int): Builder<T> {
            setSelectOptions(option1, option2, 0)
            return this
        }

        fun setSelectOptions(option1: Int, option2: Int, option3: Int): Builder<T> {
            arg.putInt(Constants.OPTIONS_SELECT_ONE, option1)
            arg.putInt(Constants.OPTIONS_SELECT_TWO, option2)
            arg.putInt(Constants.OPTIONS_SELECT_THREE, option3)
            return this
        }

        fun setSubmitListener(listener: OnOptionsSelectedListener): Builder<T> {
            arg.putSerializable(Constants.LISTENER_SUBMIT_CLICK, listener)
            return this
        }

        fun setCancelListener(listener: OnOptionsSelectedListener): Builder<T> {
            arg.putSerializable(Constants.LISTENER_CANCEL_CLICK, listener)
            return this
        }


        fun setPicker(optionsItems: ArrayList<T>): Builder<T> {
            setPicker(optionsItems, null)
            return this
        }

        fun setPicker(options1Items: ArrayList<T>, options2Items: ArrayList<T>?): Builder<T> {
            setPicker(options1Items, options2Items, null)
            return this
        }

        fun setPicker(options1Items: ArrayList<T>, options2Items: ArrayList<T>?, options3Items: ArrayList<T>?): Builder<T> {
            arg.putSerializable(Constants.OPTIONS_ITEMS_ONE, options1Items)
            arg.putSerializable(Constants.OPTIONS_ITEMS_TWO, options2Items)
            arg.putSerializable(Constants.OPTIONS_ITEMS_THREE, options3Items)
            arg.putBoolean(Constants.OPTIONS_IS_LINKED, false)
            return this
        }

        fun setLinkedPicker(optionsItems: ArrayList<T>): Builder<T> {
            setLinkedPicker(optionsItems, null)
            return this
        }

        fun setLinkedPicker(options1Items: ArrayList<T>, options2Items: ArrayList<ArrayList<T>>?): Builder<T> {
            setLinkedPicker(options1Items, options2Items, null)
            return this
        }

        fun setLinkedPicker(options1Items: ArrayList<T>, options2Items: ArrayList<ArrayList<T>>?, options3Items: ArrayList<ArrayList<ArrayList<T>>>?): Builder<T> {
            arg.putSerializable(Constants.OPTIONS_LINKED_ITEMS_ONE, options1Items)
            arg.putSerializable(Constants.OPTIONS_LINKED_ITEMS_TWO, options2Items)
            arg.putSerializable(Constants.OPTIONS_LINKED_ITEMS_THREE, options3Items)
            arg.putBoolean(Constants.OPTIONS_IS_LINKED, true)
            return this
        }
    }
}
