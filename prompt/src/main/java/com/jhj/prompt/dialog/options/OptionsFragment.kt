package com.jhj.prompt.dialog.options

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.prompt.R
import com.jhj.prompt.base.BaseDialogFragment
import com.jhj.prompt.base.Constants
import com.jhj.prompt.dialog.options.interfaces.ICommonOptions
import com.jhj.prompt.dialog.options.interfaces.OnOptionsSelectedListener
import com.jhj.prompt.dialog.options.utils.DividerType
import com.jhj.prompt.dialog.options.wheel.OptionsWheel
import com.jhj.prompt.listener.OnDialogShowOnBackListener
import kotlinx.android.synthetic.main.layout_pickerview_topbar.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor


/**
 * 自定义选择器
 * Created by jhj on 2018-3-6 0006.
 */

class OptionsFragment<T> : BaseDialogFragment() {

    private lateinit var wheel: OptionsWheel<T>

    private var titleText: String = ""
    private var titleColor: Int = -1
    private var titleSize: Float = -1f
    private var submitText: String? = "确定"
    private var submitColor: Int = -1
    private var cancelColor: Int = -1
    private var cancelText: String? = "取消"
    private var buttonSize: Float = -1f
    private var topBarBackground: Int = -1
    private var backgroundResource: Int = -1
    private var optionsTextSize: Float = -1f
    private var onlyCenterLabel: Boolean = false
    private var colorOut: Int = -1
    private var colorCenter: Int = -1
    private var dividerColor: Int = -1
    private var dividerType: DividerType? = DividerType.FILL
    private var itemNum: Int = -1
    private var optionsLabels: Array<out String>? = arrayOf()
    private var isCyclic: Boolean = true
    private var displayStyle: BooleanArray? = booleanArrayOf()
    private var xOffset: Int = -1
    private var spacingRatio: Float = -1f
    private var extraHeight: Int = -1
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
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let {
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


    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setTitleStyle(wheel.view)
        setButtonStyle(wheel.view)
        if (isLinked) {
            wheel.setPicker(optionsLinked1Items, optionsLinked2Items, optionsLinked3Items)
        } else {
            wheel.setNPicker(options1Items, options2Items, options3Items)
        }
        wheel.setCurrentItems(options1, options2, options3)
        return wheel.view
    }


    override fun initParams(bundle: Bundle) {
        super.initParams(bundle)
        titleText = bundle.getString(Constants.TITLE, "")
        titleColor = bundle.getInt(Constants.TITLE_COLOR, -1)
        titleSize = bundle.getFloat(Constants.TITLE_SIZE, -1f)
        submitText = bundle.getString(Constants.SUBMIT_TEXT, "确定")
        submitColor = bundle.getInt(Constants.SUBMIT_TEXT_COLOR, -1)
        cancelColor = bundle.getInt(Constants.CANCEL_TEXT_COLOR, -1)
        cancelText = bundle.getString(Constants.CANCEL_TEXT, "取消")
        buttonSize = bundle.getFloat(Constants.BUTTON_SIZE, -1f)
        topBarBackground = bundle.getInt(Constants.TOPBAR_BACKGROUND_RESOURCE, -1)
        cancelListener = bundle.getSerializable(Constants.LISTENER_CANCEL_CLICK) as? OnOptionsSelectedListener
        submitListener = bundle.getSerializable(Constants.LISTENER_SUBMIT_CLICK) as? OnOptionsSelectedListener
        backgroundResource = bundle.getInt(Constants.OPTIONS_BACKGROUND_RESOURCE, -1)
        optionsTextSize = bundle.getFloat(Constants.OPTIONS_TEXT_SIZE, -1f)
        onlyCenterLabel = bundle.getBoolean(Constants.ONLY_CENTER_LABEL, false)
        colorOut = bundle.getInt(Constants.TEXT_COLOR_OUT, -1)
        colorCenter = bundle.getInt(Constants.TEXT_COLOR_CENTER, -1)
        dividerColor = bundle.getInt(Constants.DIVIDER_COLOR, -1)
        dividerType = bundle.getSerializable(Constants.DIVIDER_TYPE)as? DividerType
        itemNum = bundle.getInt(Constants.ITEM_NUM, -1)
        optionsLabels = bundle.getStringArray(Constants.OPTIONS_LABELS)
        isCyclic = bundle.getBoolean(Constants.IS_CYCLIC, true)
        displayStyle = bundle.getBooleanArray(Constants.DISPLAY_STYLE)
        xOffset = bundle.getInt(Constants.X_OFFSET, -1)
        spacingRatio = bundle.getFloat(Constants.SPACING_RATIO, -1f)
        extraHeight = bundle.getInt(Constants.EXTRA_HEIGHT, -1)
        options1 = bundle.getInt(Constants.OPTIONS_SELECT_ONE, 0)
        options2 = bundle.getInt(Constants.OPTIONS_SELECT_TWO, 0)
        options3 = bundle.getInt(Constants.OPTIONS_SELECT_THREE, 0)
        isLinked = bundle.getBoolean(Constants.OPTIONS_IS_LINKED)
        if (isLinked) {
            optionsLinked1Items = bundle.getSerializable(Constants.OPTIONS_LINKED_ITEMS_ONE) as ArrayList<T>
            optionsLinked2Items = bundle.getSerializable(Constants.OPTIONS_LINKED_ITEMS_TWO) as? ArrayList<ArrayList<T>>
            optionsLinked3Items = bundle.getSerializable(Constants.OPTIONS_LINKED_ITEMS_THREE) as? ArrayList<ArrayList<ArrayList<T>>>
        } else {
            options1Items = bundle.getSerializable(Constants.OPTIONS_ITEMS_ONE) as ArrayList<T>
            options2Items = bundle.getSerializable(Constants.OPTIONS_ITEMS_TWO) as? ArrayList<T>
            options3Items = bundle.getSerializable(Constants.OPTIONS_ITEMS_THREE) as? ArrayList<T>
        }
    }

    private fun setButtonStyle(view: View) {
        cancelListener?.let {
            if (buttonSize != -1f)
                view.btnCancel.textSize = buttonSize
            if (cancelColor != -1)
                view.btnCancel.textColor = cancelColor
            view.btnCancel.text = cancelText
            view.btnCancel.onClick { view ->
                val array = wheel.currentItems
                it.onOptionsSelect(array[0], array[1], array[2])
                dismiss()
            }

        }

        submitListener?.let {
            if (buttonSize != -1f)
                view.btnSubmit.textSize = buttonSize
            if (submitColor != -1)
                view.btnSubmit.textColor = submitColor
            view.btnSubmit.text = submitText
            view.btnSubmit.onClick { view ->
                val array = wheel.currentItems
                it.onOptionsSelect(array[0], array[1], array[2])
                dismiss()
            }
        }
        if (topBarBackground != -1) {
            view.rv_topbar.setBackgroundResource(topBarBackground)
        }
    }

    private fun setTitleStyle(view: View) {
        view.tvTitle.let { title ->
            title.text = titleText
            if (titleColor != -1) {
                title.setTextColor(titleColor)
            }
            if (titleSize != -1f) {
                title.textSize = titleSize
            }
        }
    }


    class Builder<T>(val mContext: Context) : ICommonOptions<Builder<T>> {

        private val fragment = OptionsFragment<T>()
        private val arg = Bundle()

        override fun setTitle(title: String): Builder<T> {
            arg.putString(Constants.TITLE, title)
            return this
        }

        override fun setTitleSize(size: Float): Builder<T> {
            arg.putFloat(Constants.TITLE_SIZE, size)
            return this
        }

        override fun setTitleColor(color: Int): Builder<T> {
            arg.putInt(Constants.TITLE_COLOR, color)
            return this
        }

        override fun setCancel(cancel: String): Builder<T> {
            arg.putString(Constants.CANCEL_TEXT, cancel)
            return this
        }

        override fun setCancelColor(color: Int): Builder<T> {
            arg.putInt(Constants.CANCEL_TEXT_COLOR, color)
            return this
        }

        override fun setSubmit(submit: String): Builder<T> {
            arg.putString(Constants.SUBMIT_TEXT, submit)
            return this
        }

        override fun setSubmitColor(color: Int): Builder<T> {
            arg.putInt(Constants.SUBMIT_TEXT_COLOR, color)
            return this
        }

        override fun setButtonSize(size: Float): Builder<T> {
            arg.putFloat(Constants.BUTTON_SIZE, size)
            return this
        }


        override fun setTopBarBackgroundResource(resource: Int): Builder<T> {
            arg.putInt(Constants.TOPBAR_BACKGROUND_RESOURCE, resource)
            return this
        }

        override fun setOptionsBackgroundResource(resource: Int): Builder<T> {
            arg.putInt(Constants.OPTIONS_BACKGROUND_RESOURCE, resource)
            return this
        }

        override fun setOptionsSize(size: Float): Builder<T> {
            arg.putFloat(Constants.OPTIONS_TEXT_SIZE, size)
            return this
        }

        override fun setOnlyCenterLabel(centerLabel: Boolean): Builder<T> {
            arg.putBoolean(Constants.ONLY_CENTER_LABEL, centerLabel)
            return this
        }

        override fun setDividerColor(color: Int): Builder<T> {
            arg.putInt(Constants.DIVIDER_COLOR, color)
            return this
        }

        override fun setTextColorOut(color: Int): Builder<T> {
            arg.putInt(Constants.TEXT_COLOR_OUT, color)
            return this
        }

        override fun setTextColorCenter(color: Int): Builder<T> {
            arg.putInt(Constants.TEXT_COLOR_CENTER, color)
            return this
        }

        override fun setTextGravity(gravity: Int): Builder<T> {
            arg.putInt(Constants.MESSAGE_GRAVITY, gravity)
            return this
        }

        override fun setDividerType(type: DividerType): Builder<T> {
            arg.putSerializable(Constants.DIVIDER_TYPE, type)
            return this
        }

        override fun setItemNum(num: Int): Builder<T> {
            arg.putInt(Constants.ITEM_NUM, num)
            return this
        }

        override fun setLabels(label1: String?, label2: String?, label3: String?): Builder<T> {
            arg.putStringArray(Constants.OPTIONS_LABELS, arrayOf(label1, label2, label3))
            return this
        }

        override fun setCyclic(cyclic: Boolean): Builder<T> {
            arg.putBoolean(Constants.IS_CYCLIC, cyclic)
            return this
        }

        override fun setTextXOffset(offset: Int): Builder<T> {
            arg.putInt(Constants.X_OFFSET, offset)
            return this
        }

        override fun setItemsSpacingRatio(spacingRatio: Float): Builder<T> {
            arg.putFloat(Constants.SPACING_RATIO, spacingRatio)
            return this
        }

        override fun setExtraHeight(height: Int): Builder<T> {
            arg.putInt(Constants.EXTRA_HEIGHT, height)
            return this
        }

        override fun setOutSideCancelable(cancelable: Boolean): Builder<T> {
            arg.putBoolean(Constants.OUT_SIDE_CANCEL, cancelable)
            return this
        }

        override fun setGravity(gravity: Int): Builder<T> {
            arg.putInt(Constants.DIALOG_GRAVITY, gravity)
            return this
        }

        override fun setPaddingHorizontal(padding: Int): Builder<T> {
            arg.putInt(Constants.PADDING_HORIZONTAL, padding)
            return this
        }

        override fun setPaddingBottom(bottom: Int): Builder<T> {
            arg.putInt(Constants.PADDING_BOTTOM, bottom)
            return this
        }

        override fun setPaddingTop(top: Int): Builder<T> {
            arg.putInt(Constants.PADDING_TOP, top)
            return this
        }

        override fun setDimAmount(dimAmount: Float): Builder<T> {
            arg.putFloat(Constants.DIM_AMOUNT, dimAmount)
            return this
        }

        override fun setAnimation(resource: Int): Builder<T> {
            arg.putInt(Constants.ANIMATION, resource)
            return this
        }

        override fun setSelectOptions(option1: Int): Builder<T> {
            setSelectOptions(option1, 0)
            return this
        }

        override fun setSelectOptions(option1: Int, option2: Int): Builder<T> {
            setSelectOptions(option1, option2, 0)
            return this
        }

        override fun setSelectOptions(option1: Int, option2: Int, option3: Int): Builder<T> {
            arg.putInt(Constants.OPTIONS_SELECT_ONE, option1)
            arg.putInt(Constants.OPTIONS_SELECT_TWO, option2)
            arg.putInt(Constants.OPTIONS_SELECT_THREE, option3)
            return this
        }

        override fun setSubmitListener(listener: OnOptionsSelectedListener): Builder<T> {
            arg.putSerializable(Constants.LISTENER_SUBMIT_CLICK, listener)
            return this
        }

        override fun setCancelListener(listener: OnOptionsSelectedListener): Builder<T> {
            arg.putSerializable(Constants.LISTENER_CANCEL_CLICK, listener)
            return this
        }

        override fun setDialogShowOnBackListener(listener: OnDialogShowOnBackListener): Builder<T> {
            arg.putSerializable(Constants.DIALOG_ON_BACK_LISTENER, listener)
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

        override fun isShow(): Boolean {
            return fragment.isShow() ?: false
        }

        override fun show(): Builder<T> {
            fragment.arguments = arg
            fragment.show((mContext as Activity).fragmentManager)
            return this
        }

        override fun dismiss() {
            fragment.dismiss()
        }
    }
}
