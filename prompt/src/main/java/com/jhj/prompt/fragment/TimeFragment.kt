package com.jhj.prompt.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.prompt.R
import com.jhj.prompt.fragment.base.BaseBuilder
import com.jhj.prompt.fragment.base.BaseDialogFragment
import com.jhj.prompt.fragment.base.Constants
import com.jhj.prompt.fragment.options.interfaces.OnTimeSelectedListener
import com.jhj.prompt.fragment.options.utils.DividerType
import com.jhj.prompt.fragment.options.wheel.TimeWheel
import kotlinx.android.synthetic.main.layout_pickerview_time.view.*
import kotlinx.android.synthetic.main.layout_pickerview_topbar.view.*
import org.jetbrains.anko.backgroundResource
import java.text.ParseException
import java.util.*


/**
 * 时间选择器选择器
 * Created by jhj on 2018-3-6 0006.
 */

class TimeFragment : BaseDialogFragment() {


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
    }


    private lateinit var wheel: TimeWheel

    private var titleText: String = ""
    private var titleColor: Int = TITLE_TEXT_COLOR
    private var titleSize: Float = TITLE_TEXT_SIZE
    private var submitText: String? = "确定"
    private var submitColor: Int = BUTTON_COLOR
    private var cancelColor: Int = BUTTON_COLOR
    private var cancelText: String? = "取消"
    private var buttonSize: Float = BUTTON_SIZE
    private var topBarBackground: Int = TOP_BAR_BACKGROUND
    private var cancelListener: OnTimeSelectedListener? = null
    private var submitListener: OnTimeSelectedListener? = null
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
    private var displayStyle: BooleanArray? = booleanArrayOf()
    private var xOffset: Int = X_OFFSET
    private var spacingRatio: Float = LINE_SPACEING_MULTIPLIER
    private var extraHeight: Int = EXTRA_HEIGHT
    private var dateMillis: Long = Calendar.getInstance().time.time
    private var startDateMillis: Long = -1
    private var endDateMillis: Long = -1
    private var isLunarCalendar: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LayoutInflater.from(activity).inflate(R.layout.layout_pickerview_time, null)
        wheel = TimeWheel(view)
        mGravity = Gravity.BOTTOM
    }


    override fun initParams(bundle: Bundle?) {
        super.initParams(bundle)
        bundle?.let {
            titleText = it.getString(Constants.TITLE, "")
            titleColor = it.getInt(Constants.TITLE_COLOR, TITLE_TEXT_COLOR)
            titleSize = it.getFloat(Constants.TITLE_SIZE, TITLE_TEXT_SIZE)
            submitText = it.getString(Constants.SUBMIT_TEXT, "确定")
            submitColor = it.getInt(Constants.SUBMIT_TEXT_COLOR, BUTTON_COLOR)
            cancelColor = it.getInt(Constants.CANCEL_TEXT_COLOR, BUTTON_COLOR)
            cancelText = it.getString(Constants.CANCEL_TEXT, "取消")
            buttonSize = it.getFloat(Constants.BUTTON_SIZE, BUTTON_SIZE)
            topBarBackground = it.getInt(Constants.TOPBAR_BACKGROUND_RESOURCE, TOP_BAR_BACKGROUND)
            cancelListener = it.getSerializable(Constants.LISTENER_CANCEL_CLICK) as? OnTimeSelectedListener
            submitListener = it.getSerializable(Constants.LISTENER_SUBMIT_CLICK) as? OnTimeSelectedListener
            backgroundResource = it.getInt(Constants.OPTIONS_BACKGROUND_RESOURCE, BACKGROUND_RESOURCE)
            optionsTextSize = it.getFloat(Constants.OPTIONS_TEXT_SIZE, OPTION_TEXT_SIZE)
            onlyCenterLabel = it.getBoolean(Constants.ONLY_CENTER_LABEL, false)
            colorOut = it.getInt(Constants.TEXT_COLOR_OUT, OPTION_TEXT_COLOR_OUT)
            colorCenter = it.getInt(Constants.TEXT_COLOR_CENTER, OPTION_TEXT_COLOR_CENTER)
            dividerColor = it.getInt(Constants.DIVIDER_COLOR, DIVIDER_COLOR)
            dividerType = it.getSerializable(Constants.DIVIDER_TYPE)as? DividerType
            itemNum = it.getInt(Constants.ITEM_NUM, ITEM_NUM)
            optionsLabels = it.getStringArray(Constants.OPTIONS_LABELS)
            isCyclic = it.getBoolean(Constants.IS_CYCLIC, true)
            displayStyle = it.getBooleanArray(Constants.DISPLAY_STYLE)
            xOffset = it.getInt(Constants.X_OFFSET, X_OFFSET)
            spacingRatio = it.getFloat(Constants.SPACING_RATIO, LINE_SPACEING_MULTIPLIER)
            extraHeight = it.getInt(Constants.EXTRA_HEIGHT, EXTRA_HEIGHT)
            dateMillis = it.getLong(Constants.DATE_MILLS, Calendar.getInstance().time.time)
            startDateMillis = it.getLong(Constants.START_DATE_MILLS, -1)
            endDateMillis = it.getLong(Constants.END_DATE_MILLS, -1)
            isLunarCalendar = it.getBoolean(Constants.LUNAR_CALENDAR, false)
        }
    }


    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setButtonStyle(wheel.getView())
        setOptionsStyle()
        setLunar()
        return wheel.getView()
    }


    private fun setButtonStyle(view: View) {
        view.layout_picker.backgroundResource = backgroundResource
        view.rv_topbar.backgroundResource = topBarBackground
        view.tv_option_title.text = titleText
        view.tv_option_title.setTextColor(titleColor)
        view.tv_option_title.textSize = titleSize

        view.btn_option_submit.let { btnSubmit ->
            btnSubmit.text = submitText
            btnSubmit.textSize = buttonSize
            btnSubmit.setTextColor(ContextCompat.getColorStateList(requireContext(),submitColor))
            btnSubmit.setOnClickListener {
                    val date = TimeWheel.dateFormat.parse(wheel.time)
                    submitListener?.onTimeSelect(date, wheel.getView())
                    dismiss()
            }
        }

        view.btn_option_cancel.let { btnCancel ->
            btnCancel.text = cancelText
            btnCancel.textSize = buttonSize
            btnCancel.setTextColor(ContextCompat.getColorStateList(requireContext(),cancelColor))
            btnCancel.setOnClickListener {
                    val date = TimeWheel.dateFormat.parse(wheel.time)
                    cancelListener?.onTimeSelect(date, wheel.getView())
                    dismiss()
            }

        }
    }


    private fun setOptionsStyle() {
        wheel.getView().timepicker?.setBackgroundResource(backgroundResource)
        wheel.setOptionsTextSize(optionsTextSize)
        wheel.setTextColorOut(colorOut)
        wheel.setTextColorCenter(colorCenter)
        wheel.setDividerColor(dividerColor)
        wheel.setItemNum(itemNum)
        wheel.setTextXOffset(xOffset)
        wheel.setLineSpacingMultiplier(spacingRatio)
        wheel.setMaxAddHeight(extraHeight)
        wheel.setDividerType(dividerType ?: DIVIDER_TYPE)
        wheel.setCyclic(isCyclic)
        wheel.isCenterLabel(onlyCenterLabel)

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
            if (dateMillis in startDateMillis..endDateMillis) {
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
            it.putLong(Constants.DATE_MILLS, TimeWheel.dateFormat.parse(wheel.time).time)
            it.putLong(Constants.START_DATE_MILLS, startDateMillis)
            it.putLong(Constants.END_DATE_MILLS, endDateMillis)
            it.putBoolean(Constants.LUNAR_CALENDAR, isLunarCalendar)
        }
    }


    class Builder(val context: Context) : BaseBuilder<Builder>(context) {

        private val mFragment = TimeFragment()

        override val fragment: TimeFragment
            get() = mFragment

        fun setTitle(title: String): Builder {
            arg.putString(Constants.TITLE, title)
            return this
        }

        fun setTitleSize(size: Float): Builder {
            arg.putFloat(Constants.TITLE_SIZE, size)
            return this
        }

        fun setTitleColor(color: Int): Builder {
            arg.putInt(Constants.TITLE_COLOR, color)
            return this
        }

        fun setCancel(cancel: String): Builder {
            arg.putString(Constants.CANCEL_TEXT, cancel)
            return this
        }

        fun setCancelColor(color: Int): Builder {
            arg.putInt(Constants.CANCEL_TEXT_COLOR, color)
            return this
        }

        fun setSubmit(submit: String): Builder {
            arg.putString(Constants.SUBMIT_TEXT, submit)
            return this
        }

        fun setSubmitColor(color: Int): Builder {
            arg.putInt(Constants.SUBMIT_TEXT_COLOR, color)
            return this
        }

        fun setButtonSize(size: Float): Builder {
            arg.putFloat(Constants.BUTTON_SIZE, size)
            return this
        }

        fun setTopBarBackgroundResource(resource: Int): Builder {
            arg.putInt(Constants.TOPBAR_BACKGROUND_RESOURCE, resource)
            return this
        }

        fun setOptionsBackgroundResource(resource: Int): Builder {
            arg.putInt(Constants.OPTIONS_BACKGROUND_RESOURCE, resource)
            return this
        }

        fun setOptionsSize(size: Float): Builder {
            arg.putFloat(Constants.OPTIONS_TEXT_SIZE, size)
            return this
        }

        fun setOnlyCenterLabel(centerLabel: Boolean): Builder {
            arg.putBoolean(Constants.ONLY_CENTER_LABEL, centerLabel)
            return this
        }

        fun setDividerColor(color: Int): Builder {
            arg.putInt(Constants.DIVIDER_COLOR, color)
            return this
        }

        fun setTextColorOut(color: Int): Builder {
            arg.putInt(Constants.TEXT_COLOR_OUT, color)
            return this
        }

        fun setTextColorCenter(color: Int): Builder {
            arg.putInt(Constants.TEXT_COLOR_CENTER, color)
            return this
        }

        fun setTextGravity(gravity: Int): Builder {
            arg.putInt(Constants.MESSAGE_GRAVITY, gravity)
            return this
        }

        fun setDividerType(type: DividerType): Builder {
            arg.putSerializable(Constants.DIVIDER_TYPE, type)
            return this
        }

        fun setItemNum(num: Int): Builder {
            arg.putInt(Constants.ITEM_NUM, num)
            return this
        }

        fun setLabels(label1: String?, label2: String?, label3: String?, label4: String?, label5: String?, label6: String?): Builder {
            arg.putStringArray(Constants.OPTIONS_LABELS, arrayOf(label1, label2, label3, label4, label5, label6))
            return this
        }

        fun setCyclic(cyclic: Boolean): Builder {
            arg.putBoolean(Constants.IS_CYCLIC, cyclic)
            return this
        }

        fun setDisplayStyle(booleanArray: BooleanArray): Builder {
            arg.putBooleanArray(Constants.DISPLAY_STYLE, booleanArray)
            return this
        }

        fun setTextXOffset(offset: Int): Builder {
            arg.putInt(Constants.X_OFFSET, offset)
            return this
        }

        fun setItemsSpacingRatio(spacingRatio: Float): Builder {
            arg.putFloat(Constants.SPACING_RATIO, spacingRatio)
            return this
        }

        fun setExtraHeight(height: Int): Builder {
            arg.putInt(Constants.EXTRA_HEIGHT, height)
            return this
        }

        fun setDate(date: Calendar): Builder {
            arg.putLong(Constants.DATE_MILLS, date.time.time)
            return this
        }

        fun setRangDate(startDate: Calendar, endDate: Calendar): Builder {
            arg.putLong(Constants.START_DATE_MILLS, startDate.time.time)
            arg.putLong(Constants.END_DATE_MILLS, endDate.time.time)
            return this
        }

        fun setLunarCalendar(lunarCalendar: Boolean): Builder {
            arg.putBoolean(Constants.LUNAR_CALENDAR, lunarCalendar)
            return this
        }

        fun setSubmitListener(listener: OnTimeSelectedListener): Builder {
            arg.putSerializable(Constants.LISTENER_SUBMIT_CLICK, listener)
            return this
        }

        fun setCancelListener(listener: OnTimeSelectedListener): Builder {
            arg.putSerializable(Constants.LISTENER_CANCEL_CLICK, listener)
            return this
        }
    }
}
