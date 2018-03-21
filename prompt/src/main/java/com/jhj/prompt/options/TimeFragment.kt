package com.jhj.prompt.options

import android.app.Activity
import android.app.DialogFragment
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.prompt.BaseDialogFragment
import com.jhj.prompt.Constants
import com.jhj.prompt.OnDialogCancelListener
import com.jhj.prompt.R
import com.jhj.prompt.options.interfaces.ITimeOptions
import com.jhj.prompt.options.interfaces.OnTimeSelectedListener
import com.jhj.prompt.options.utils.DividerType
import com.jhj.prompt.options.wheel.TimeWheel
import com.jhj.prompt.progress.PercentFragment
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


    var wheel: TimeWheel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_style)
        savedInstanceState?.let {
            if (it.getBoolean(Constants.ACTIVITY_DESTROY))
                dismiss()
        }
    }

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        wheel?.getView()?.let {
            setTitleStyle(it)
            setButtonStyle(it)
        }
        return wheel?.getView()
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(Constants.ACTIVITY_DESTROY, true)
    }


    private fun setButtonStyle(view: View) {
        val cancelColor = arguments.getInt(Constants.CANCEL_TEXT_COLOR, -1)
        val submitColor = arguments.getInt(Constants.SUBMIT_TEXT_COLOR, -1)
        val size = arguments.getFloat(Constants.BUTTON_SIZE, -1f)
        val background = arguments.getInt(Constants.TOPBAR_BACKGROUND_RESOURCE, -1)

        view.btnSubmit.let { submit ->
            submit.text = arguments.getString(Constants.SUBMIT_TEXT, "确定")
            if (size != -1f)
                submit.textSize = size
            if (submitColor != -1)
                submit.textColor = submitColor
        }
        view.btnCancel.let { cancel ->
            cancel.text = arguments.getString(Constants.CANCEL_TEXT, "取消")
            if (size != -1f)
                cancel.textSize = size
            if (cancelColor != -1)
                cancel.textColor = cancelColor
        }
        if (background != -1) {
            view.rv_topbar.setBackgroundResource(background)
        }
    }

    private fun setTitleStyle(view: View) {
        val color = arguments.getInt(Constants.TITLE_COLOR, -1)
        val size = arguments.getFloat(Constants.TITLE_SIZE, -1f)
        view.tvTitle.let { title ->
            title.text = arguments.getString(Constants.TITLE, "")
            if (color != -1) {
                title.setTextColor(color)
            }
            if (size != -1f) {
                title.textSize = size
            }
        }
    }


    class Builder(val mContext: Context) : ITimeOptions<Builder> {
        private val fragment = TimeFragment()
        private val arg = Bundle()
        private val wheel: TimeWheel = TimeWheel(mContext)
        private var startDate: Calendar? = null//开始时间
        private var endDate: Calendar? = null//终止时间
        private var date: Calendar = Calendar.getInstance()

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
            wheel.getView().timepicker.setBackgroundResource(resource)
            return this
        }

        override fun setOptionsSize(size: Float): Builder {
            wheel.setContentTextSize(size)
            return this
        }

        override fun isOptionsLabel(centerLabel: Boolean): Builder {
            wheel.isCenterLabel(centerLabel)
            return this
        }

        override fun setDividerColor(color: Int): Builder {
            wheel.setDividerColor(color)
            return this
        }

        override fun setTextColorOut(color: Int): Builder {
            wheel.setTextColorOut(color)
            return this
        }

        override fun setTextColorCenter(color: Int): Builder {
            wheel.setTextColorCenter(color)
            return this
        }

        override fun setTextGravity(gravity: Int): Builder {
            wheel.setTextGravity(gravity)
            return this
        }

        override fun setDividerType(type: DividerType): Builder {
            wheel.setDividerType(type)
            return this
        }

        override fun setItemSize(num: Int): Builder {
            wheel.setItemNum(num)
            return this
        }

        override fun setLabels(label1: String?, label2: String?, label3: String?, label4: String?, label5: String?, label6: String?): Builder {
            wheel.setLabels(label1, label2, label3, label4, label5, label6)
            return this
        }

        override fun setCyclic(cyclic: Boolean): Builder {
            wheel.setCyclic(cyclic)
            return this
        }

        override fun setDisplayStyle(booleanArray: BooleanArray): Builder {
            wheel.setDisplayStyle(booleanArray)
            return this
        }

        override fun setTextXOffset(offset: Int): Builder {
            wheel.setTextXOffset(offset)
            return this
        }

        override fun setItemsSpacingRatio(spacingRatio: Float): Builder {
            wheel.setLineSpacingMultiplier(spacingRatio)
            return this
        }

        override fun setMaxAddHeight(height: Int): Builder {
            wheel.setMaxAddHeight(height)
            return this
        }

        override fun setOutSideCancelable(cancelable: Boolean): Builder {
            arg.putBoolean(Constants.OUT_SIDE_CANCEL, cancelable)
            return this
        }

        override fun setTypeface(font: Typeface): Builder {
            wheel.setTypeface(font)
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
            this.date = date
            setTime()
            return this
        }

        override fun setRangDate(startDate: Calendar, endDate: Calendar): Builder {
            this.startDate = startDate
            this.endDate = endDate
            return this
        }

        override fun setLunarCalendar(lunarCalendar: Boolean): Builder {
            try {
                val calendar = Calendar.getInstance()
                calendar.time = TimeWheel.dateFormat.parse(wheel.time)
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val hours = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)
                val seconds = calendar.get(Calendar.SECOND)

                wheel.setLunarCalendar(lunarCalendar)
                wheel.setPicker(year, month, day, hours, minute, seconds)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return this
        }

        override fun setSubmitListener(listener: OnTimeSelectedListener): Builder {
            wheel.getView().btnSubmit?.onClick {
                try {
                    val date = TimeWheel.dateFormat.parse(wheel.time)
                    listener.onTimeSelect(date, wheel.getView())
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                fragment.dismiss()
            }
            return this
        }

        override fun setCancelListener(listener: OnTimeSelectedListener): Builder {
            wheel.getView().btnCancel?.onClick {
                try {
                    val date = TimeWheel.dateFormat.parse(wheel.time)
                    listener.onTimeSelect(date, wheel.getView())
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                fragment.dismiss()
            }
            return this
        }

        override fun setOnDialogCancelListener(listener: OnDialogCancelListener): Builder {
            arg.putSerializable(Constants.DIALOG_CANCEL_LISTENER, listener)
            return this
        }

        override fun show(): Builder {
            fragment.wheel = wheel
            fragment.arguments = arg
            if (startDate != null && endDate != null) {
                if (startDate?.timeInMillis as Long <= endDate?.timeInMillis as Long) {
                    wheel.setRangDate(startDate as Calendar, endDate as Calendar)
                    date = startDate as Calendar
                }
            }
            setTime()
            fragment.show((mContext as Activity).fragmentManager)
            return this
        }

        override fun dismiss() {
            fragment.dismiss()
        }

        /**
         * 设置选中时间,默认选中当前时间
         */
        private fun setTime() {
            val year = date.get(Calendar.YEAR)
            val month = date.get(Calendar.MONTH)
            val day = date.get(Calendar.DAY_OF_MONTH)
            val hours = date.get(Calendar.HOUR_OF_DAY)
            val minute = date.get(Calendar.MINUTE)
            val seconds = date.get(Calendar.SECOND)
            wheel.setPicker(year, month, day, hours, minute, seconds)
        }
    }
}
