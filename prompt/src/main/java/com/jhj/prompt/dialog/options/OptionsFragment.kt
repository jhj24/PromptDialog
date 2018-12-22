package com.jhj.prompt.dialog.options

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.prompt.R
import com.jhj.prompt.base.BaseBuilder
import com.jhj.prompt.base.BaseDialogFragment
import com.jhj.prompt.base.Constants
import com.jhj.prompt.dialog.options.interfaces.OnOptionsSelectedListener
import com.jhj.prompt.dialog.options.utils.DividerType
import com.jhj.prompt.dialog.options.wheel.OptionsWheel
import kotlinx.android.synthetic.main.layout_pickerview_options.view.*
import kotlinx.android.synthetic.main.layout_pickerview_topbar.view.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.textColor


/**
 * 自定义选择器
 * Created by jhj on 2018-3-6 0006.
 */

class OptionsFragment<T> : BaseDialogFragment() {

    companion object {
        val TITLE_TEXT_SIZE = 21f
        val TITLE_TEXT_COLOR = Color.BLACK
        val BUTTON_COLOR = R.drawable.selector_pickerview_btn
        val BUTTON_SIZE = 18f
        val TOP_BAR_BACKGROUND = R.color.pickerview_bg_topbar
        val BACKGROUND_RESOURCE = R.drawable.bg_dialog_no_corner
        val OPTION_TEXT_SIZE = 20f
        val OPTION_TEXT_COLOR_CENTER = 0xFF2a2a2a.toInt()
        val OPTION_TEXT_COLOR_OUT = 0xFFa8a8a8.toInt()
        val DIVIDER_TYPE = DividerType.FILL
        val DIVIDER_COLOR = 0xFFd5d5d5.toInt()
        val ITEM_NUM = 11
        val LINE_SPACEING_MULTIPLIER = 1.6f
        val EXTRA_HEIGHT = 2;
        val X_OFFSET = 0
        val TEXT_GRAVITY = Gravity.CENTER
    }


    private lateinit var wheel: OptionsWheel<T>


    private var titleText: String = ""
    private var titleColor: Int = TITLE_TEXT_COLOR
    private var titleSize: Float = TITLE_TEXT_SIZE
    private var submitText: String? = "确定"
    private var submitColor: Int = BUTTON_COLOR
    private var cancelColor: Int = BUTTON_COLOR
    private var cancelText: String? = "取消"
    private var buttonSize: Float = BUTTON_SIZE
    private var topBarBackground: Int = TOP_BAR_BACKGROUND
    private var backgroundResource: Int = BACKGROUND_RESOURCE
    private var optionsTextSize: Float = OPTION_TEXT_SIZE
    private var onlyCenterLabel: Boolean = false
    private var colorOut: Int = OPTION_TEXT_COLOR_OUT
    private var colorCenter: Int = OPTION_TEXT_COLOR_CENTER
    private var dividerColor: Int = DIVIDER_COLOR
    private var dividerType: DividerType? = DIVIDER_TYPE
    private var itemNum: Int = ITEM_NUM
    private var optionsLabels: Array<out String>? = arrayOf()
    private var isCyclic: Boolean = true
    private var textGravity: Int = TEXT_GRAVITY
    private var displayStyle: BooleanArray? = booleanArrayOf()
    private var xOffset: Int = X_OFFSET
    private var spacingRatio: Float = LINE_SPACEING_MULTIPLIER
    private var extraHeight: Int = EXTRA_HEIGHT
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = LayoutInflater.from(activity).inflate(R.layout.layout_pickerview_options, null)
        wheel = OptionsWheel(view)
        mGravity = Gravity.BOTTOM
    }


    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

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
        wheel.setDividerType(dividerType ?: DIVIDER_TYPE)
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

        return wheel.view
    }


    override fun initParams(bundle: Bundle?) {
        super.initParams(bundle)
        bundle?.let {
            titleText = it.getString(Constants.TITLE, "")
            titleSize = it.getFloat(Constants.TITLE_SIZE, TITLE_TEXT_SIZE)
            titleColor = it.getInt(Constants.TITLE_COLOR, TITLE_TEXT_COLOR)
            submitText = it.getString(Constants.SUBMIT_TEXT, "确定")
            submitColor = it.getInt(Constants.SUBMIT_TEXT_COLOR, BUTTON_COLOR)
            cancelColor = it.getInt(Constants.CANCEL_TEXT_COLOR, BUTTON_COLOR)
            cancelText = it.getString(Constants.CANCEL_TEXT, "取消")
            buttonSize = it.getFloat(Constants.BUTTON_SIZE, BUTTON_SIZE)
            topBarBackground = it.getInt(Constants.TOPBAR_BACKGROUND_RESOURCE, TOP_BAR_BACKGROUND)
            backgroundResource = it.getInt(Constants.OPTIONS_BACKGROUND_RESOURCE, BACKGROUND_RESOURCE)
            cancelListener = it.getSerializable(Constants.LISTENER_CANCEL_CLICK) as? OnOptionsSelectedListener
            submitListener = it.getSerializable(Constants.LISTENER_SUBMIT_CLICK) as? OnOptionsSelectedListener
            optionsTextSize = it.getFloat(Constants.OPTIONS_TEXT_SIZE, OPTION_TEXT_SIZE)
            onlyCenterLabel = it.getBoolean(Constants.ONLY_CENTER_LABEL, false)
            colorOut = it.getInt(Constants.TEXT_COLOR_OUT, OPTION_TEXT_COLOR_OUT)
            colorCenter = it.getInt(Constants.TEXT_COLOR_CENTER, OPTION_TEXT_COLOR_CENTER)
            dividerColor = it.getInt(Constants.DIVIDER_COLOR, DIVIDER_COLOR)
            dividerType = it.getSerializable(Constants.DIVIDER_TYPE) as? DividerType
            itemNum = it.getInt(Constants.ITEM_NUM, ITEM_NUM)
            optionsLabels = it.getStringArray(Constants.OPTIONS_LABELS)
            isCyclic = it.getBoolean(Constants.IS_CYCLIC, true)
            displayStyle = it.getBooleanArray(Constants.DISPLAY_STYLE)
            xOffset = it.getInt(Constants.X_OFFSET, X_OFFSET)
            spacingRatio = it.getFloat(Constants.SPACING_RATIO, LINE_SPACEING_MULTIPLIER)
            extraHeight = it.getInt(Constants.EXTRA_HEIGHT, EXTRA_HEIGHT)
            textGravity = it.getInt(Constants.MESSAGE_GRAVITY, TEXT_GRAVITY)
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

    private fun setButtonStyle(view: View) {

        view.rv_topbar.backgroundResource = topBarBackground
        view.layout_picker.backgroundResource = backgroundResource
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


    class Builder<T>(val mContext: Context) : BaseBuilder<Builder<T>>() {

        private val fragment = OptionsFragment<T>()

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

        fun setOptionsSize(size: Float): Builder<T> {
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

        fun setTextGravity(gravity: Int): Builder<T> {
            arg.putInt(Constants.MESSAGE_GRAVITY, gravity)
            return this
        }

        fun setDividerType(type: DividerType): Builder<T> {
            arg.putSerializable(Constants.DIVIDER_TYPE, type)
            return this
        }

        fun setItemNum(num: Int): Builder<T> {
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

        fun isShow(): Boolean {
            return fragment.isShow() ?: false
        }

        fun show(): Builder<T> {
            fragment.arguments = arg
            fragment.show((mContext as FragmentActivity).supportFragmentManager)
            return this
        }

        fun dismiss() {
            fragment.dismiss()
        }
    }
}
