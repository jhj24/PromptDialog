package com.jhj.prompt.dialog.options

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.prompt.*
import com.jhj.prompt.base.BaseDialogFragment
import com.jhj.prompt.base.Constants
import com.jhj.prompt.listener.OnDialogShowOnBackListener
import com.jhj.prompt.dialog.options.interfaces.ITimeOptions
import com.jhj.prompt.dialog.options.interfaces.OnTimeSelectedListener
import com.jhj.prompt.dialog.options.utils.DividerType
import com.jhj.prompt.dialog.options.wheel.TimeWheel
import com.jhj.prompt.util.notMinusOne
import kotlinx.android.synthetic.main.layout_pickerview_time.view.*
import kotlinx.android.synthetic.main.layout_pickerview_topbar.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor
import java.text.ParseException
import java.util.*


/**
 * 时间选择器选择器
 * Created by jhj on 2018-3-6 0006.
 */

class TimeFragment : BaseDialogFragment() {

    private lateinit var wheel: TimeWheel

    private var titleText: String = ""
    private var titleColor: Int = -1
    private var titleSize: Float = -1f
    private var submitText: String? = "确定"
    private var submitColor: Int = -1
    private var cancelColor: Int = -1
    private var cancelText: String? = "取消"
    private var buttonSize: Float = -1f
    private var topBarBackground: Int = -1
    private var cancelListener: OnTimeSelectedListener? = null
    private var submitListener: OnTimeSelectedListener? = null
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
    private var dateMillis: Long = -1
    private var startDateMillis: Long = -1
    private var endDateMillis: Long = -1
    private var isLunarCalendar: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LayoutInflater.from(activity).inflate(R.layout.layout_pickerview_time, null)
        wheel = TimeWheel(view)
    }


    override fun initParams(bundle: Bundle) {

        titleText = bundle.getString(Constants.TITLE, "")
        titleColor = bundle.getInt(Constants.TITLE_COLOR, -1)
        titleSize = bundle.getFloat(Constants.TITLE_SIZE, -1f)
        submitText = bundle.getString(Constants.SUBMIT_TEXT, "确定")
        submitColor = bundle.getInt(Constants.SUBMIT_TEXT_COLOR, -1)
        cancelColor = bundle.getInt(Constants.CANCEL_TEXT_COLOR, -1)
        cancelText = bundle.getString(Constants.CANCEL_TEXT, "取消")
        buttonSize = bundle.getFloat(Constants.BUTTON_SIZE, -1f)
        topBarBackground = bundle.getInt(Constants.TOPBAR_BACKGROUND_RESOURCE, -1)
        cancelListener = bundle.getSerializable(Constants.LISTENER_CANCEL_CLICK) as? OnTimeSelectedListener
        submitListener = bundle.getSerializable(Constants.LISTENER_SUBMIT_CLICK) as? OnTimeSelectedListener
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
        dateMillis = bundle.getLong(Constants.DATE_MILLS, Calendar.getInstance().time.time)
        startDateMillis = bundle.getLong(Constants.START_DATE_MILLS, -1)
        endDateMillis = bundle.getLong(Constants.END_DATE_MILLS, -1)
        isLunarCalendar = bundle.getBoolean(Constants.LUNAR_CALENDAR, false)
    }


    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setTitleStyle(wheel.getView())
        setButtonStyle(wheel.getView())
        setOptionsStyle()
        setLunar()
        return wheel.getView()
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
            it.putLong(Constants.DATE_MILLS, TimeWheel.dateFormat.parse(wheel.time).time)
            it.putLong(Constants.START_DATE_MILLS, startDateMillis)
            it.putLong(Constants.END_DATE_MILLS, endDateMillis)
            it.putBoolean(Constants.LUNAR_CALENDAR, isLunarCalendar)
        }
    }


    private fun setButtonStyle(view: View) {
        view.btnSubmit.let { submit ->
            submit.text = submitText
            if (buttonSize != -1f)
                submit.textSize = buttonSize
            if (submitColor != -1)
                submit.textColor = submitColor
            submit.onClick {
                submitListener?.let {
                    val date = TimeWheel.dateFormat.parse(wheel.time)
                    it.onTimeSelect(date, wheel.getView())
                    dismiss()
                }
            }
        }

        view.btnCancel.let { cancel ->
            cancel.text = cancelText
            if (buttonSize != -1f)
                cancel.textSize = buttonSize
            if (cancelColor != -1)
                cancel.textColor = cancelColor
            cancel.onClick {
                cancelListener?.let {
                    val date = TimeWheel.dateFormat.parse(wheel.time)
                    it.onTimeSelect(date, wheel.getView())
                    dismiss()
                }
            }

        }

        if (topBarBackground != -1)
            view.rv_topbar.setBackgroundResource(topBarBackground)

    }

    private fun setTitleStyle(view: View) {
        view.tvTitle.let { title ->
            title.text = titleText
            if (titleColor != -1)
                title.setTextColor(titleColor)
            if (titleSize != -1f)
                title.textSize = titleSize
        }
    }

    private fun setOptionsStyle() {
        backgroundResource.notMinusOne { wheel.getView().timepicker?.setBackgroundResource(it) }
        optionsTextSize.notMinusOne { wheel.setOptionsTextSize(it) }
        colorOut.notMinusOne { wheel.setTextColorOut(it) }
        colorCenter.notMinusOne { wheel.setTextColorCenter(it) }
        dividerColor.notMinusOne { wheel.setDividerColor(it) }
        itemNum.notMinusOne { wheel.setItemNum(it) }
        xOffset.notMinusOne { wheel.setTextXOffset(it) }
        spacingRatio.notMinusOne { wheel.setLineSpacingMultiplier(it) }
        extraHeight.notMinusOne { wheel.setMaxAddHeight(it) }
        wheel.setDividerType(dividerType ?: DividerType.FILL)
        wheel.setCyclic(isCyclic)
        if (onlyCenterLabel) {
            wheel.isCenterLabel(onlyCenterLabel)
        }
        optionsLabels?.let {
            if (it.size == 6) {
                wheel.setLabels(it[0], it[1], it[2], it[3], it[4], it[5])
            }
        }
        displayStyle?.let {
            if (it.size == 6) {
                wheel.setDisplayStyle(it)
            }
        }

        if (startDateMillis > 0f && endDateMillis > 0f && endDateMillis > startDateMillis) {
            val startCalendar = Calendar.getInstance()
            val endCalendar = Calendar.getInstance()
            startCalendar.timeInMillis = startDateMillis
            endCalendar.timeInMillis = endDateMillis
            wheel.setRangDate(startCalendar, endCalendar)
            if (dateMillis !in startDateMillis..endDateMillis) {
                dateMillis = startDateMillis
            }
        }
        setTime(dateMillis)
    }


    private fun setLunar() {
        if (isLunarCalendar) {
            try {
                val calendar = Calendar.getInstance()
                calendar.time = TimeWheel.dateFormat.parse(wheel.time)
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val hours = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)
                val seconds = calendar.get(Calendar.SECOND)
                wheel.setLunarCalendar(true)
                wheel.setPicker(year, month, day, hours, minute, seconds)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
    }

    private fun setTime(mills: Long) {
        val date = Calendar.getInstance()
        date.timeInMillis = mills
        val year = date.get(Calendar.YEAR)
        val month = date.get(Calendar.MONTH)
        val day = date.get(Calendar.DAY_OF_MONTH)
        val hours = date.get(Calendar.HOUR_OF_DAY)
        val minute = date.get(Calendar.MINUTE)
        val seconds = date.get(Calendar.SECOND)
        wheel.setPicker(year, month, day, hours, minute, seconds)
    }

    class Builder(val mContext: Context) : ITimeOptions<Builder> {
        private val fragment = TimeFragment()
        private val arg = Bundle()

        override fun setTitle(title: String): Builder {
            arg.putString(Constants.TITLE, title)
            return this
        }

        override fun setTitleSize(size: Float): Builder {
            arg.putFloat(Constants.TITLE_SIZE, size)
            return this
        }

        override fun setTitleColor(color: Int): Builder {
            arg.putInt(Constants.TITLE_COLOR, color)
            return this
        }

        override fun setCancel(cancel: String): Builder {
            arg.putString(Constants.CANCEL_TEXT, cancel)
            return this
        }

        override fun setCancelColor(color: Int): Builder {
            arg.putInt(Constants.CANCEL_TEXT_COLOR, color)
            return this
        }

        override fun setSubmit(submit: String): Builder {
            arg.putString(Constants.SUBMIT_TEXT, submit)
            return this
        }

        override fun setSubmitColor(color: Int): Builder {
            arg.putInt(Constants.SUBMIT_TEXT_COLOR, color)
            return this
        }

        override fun setButtonSize(size: Float): Builder {
            arg.putFloat(Constants.BUTTON_SIZE, size)
            return this
        }

        override fun setTopBarBackgroundResource(resource: Int): Builder {
            arg.putInt(Constants.TOPBAR_BACKGROUND_RESOURCE, resource)
            return this
        }

        override fun setOptionsBackgroundResource(resource: Int): Builder {
            arg.putInt(Constants.OPTIONS_BACKGROUND_RESOURCE, resource)
            return this
        }

        override fun setOptionsSize(size: Float): Builder {
            arg.putFloat(Constants.OPTIONS_TEXT_SIZE, size)
            return this
        }

        override fun setOnlyCenterLabel(centerLabel: Boolean): ITimeOptions<Builder> {
            arg.putBoolean(Constants.ONLY_CENTER_LABEL, centerLabel)
            return this
        }

        override fun setDividerColor(color: Int): Builder {
            arg.putInt(Constants.DIVIDER_COLOR, color)
            return this
        }

        override fun setTextColorOut(color: Int): Builder {
            arg.putInt(Constants.TEXT_COLOR_OUT, color)
            return this
        }

        override fun setTextColorCenter(color: Int): Builder {
            arg.putInt(Constants.TEXT_COLOR_CENTER, color)
            return this
        }

        override fun setTextGravity(gravity: Int): Builder {
            arg.putInt(Constants.MESSAGE_GRAVITY, gravity)
            return this
        }

        override fun setDividerType(type: DividerType): Builder {
            arg.putSerializable(Constants.DIVIDER_TYPE, type)
            return this
        }

        override fun setItemNum(num: Int): Builder {
            arg.putInt(Constants.ITEM_NUM, num)
            return this
        }

        override fun setLabels(label1: String?, label2: String?, label3: String?, label4: String?, label5: String?, label6: String?): Builder {
            arg.putStringArray(Constants.OPTIONS_LABELS, arrayOf(label1, label2, label3, label4, label5, label6))
            return this
        }

        override fun setCyclic(cyclic: Boolean): Builder {
            arg.putBoolean(Constants.IS_CYCLIC, cyclic)
            return this
        }

        override fun setDisplayStyle(booleanArray: BooleanArray): Builder {
            arg.putBooleanArray(Constants.DISPLAY_STYLE, booleanArray)
            return this
        }

        override fun setTextXOffset(offset: Int): Builder {
            arg.putInt(Constants.X_OFFSET, offset)
            return this
        }

        override fun setItemsSpacingRatio(spacingRatio: Float): Builder {
            arg.putFloat(Constants.SPACING_RATIO, spacingRatio)
            return this
        }

        override fun setExtraHeight(height: Int): Builder {
            arg.putInt(Constants.EXTRA_HEIGHT, height)
            return this
        }

        override fun setOutSideCancelable(cancelable: Boolean): Builder {
            arg.putBoolean(Constants.OUT_SIDE_CANCEL, cancelable)
            return this
        }

        override fun setGravity(gravity: Int): Builder {
            arg.putInt(Constants.DIALOG_GRAVITY, gravity)
            return this
        }

        override fun setPaddingHorizontal(padding: Int): Builder {
            arg.putInt(Constants.PADDING_HORIZONTAL, padding)
            return this
        }

        override fun setPaddingBottom(bottom: Int): Builder {
            arg.putInt(Constants.PADDING_BOTTOM, bottom)
            return this
        }

        override fun setPaddingTop(top: Int): Builder {
            arg.putInt(Constants.PADDING_TOP, top)
            return this
        }

        override fun setDimAmount(dimAmount: Float): Builder {
            arg.putFloat(Constants.DIM_AMOUNT, dimAmount)
            return this
        }

        override fun setAnimation(resource: Int): Builder {
            arg.putInt(Constants.ANIMATION, resource)
            return this
        }

        override fun setDate(date: Calendar): Builder {
            arg.putLong(Constants.DATE_MILLS, date.time.time)
            return this
        }

        override fun setRangDate(startDate: Calendar, endDate: Calendar): Builder {
            arg.putLong(Constants.START_DATE_MILLS, startDate.time.time)
            arg.putLong(Constants.END_DATE_MILLS, endDate.time.time)
            return this
        }

        override fun setLunarCalendar(lunarCalendar: Boolean): Builder {
            arg.putBoolean(Constants.LUNAR_CALENDAR, lunarCalendar)
            return this
        }

        override fun setSubmitListener(listener: OnTimeSelectedListener): Builder {
            arg.putSerializable(Constants.LISTENER_SUBMIT_CLICK, listener)
            return this
        }

        override fun setCancelListener(listener: OnTimeSelectedListener): Builder {
            arg.putSerializable(Constants.LISTENER_CANCEL_CLICK, listener)
            return this
        }

        override fun setDialogShowOnBackListener(listener: OnDialogShowOnBackListener): Builder {
            arg.putSerializable(Constants.DIALOG_ON_BACK_LISTENER, listener)
            return this
        }

        override fun show(): Builder {
            fragment.arguments = arg
            fragment.show((mContext as Activity).fragmentManager)
            return this
        }

        override fun dismiss() {
            fragment.dismiss()
        }
    }
}
