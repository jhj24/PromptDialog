package com.jhj.prompt.options

import android.app.Activity
import android.app.DialogFragment
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhj.prompt.BaseDialogFragment
import com.jhj.prompt.Constants
import com.jhj.prompt.R
import com.jhj.prompt.options.interfaces.ICommonOptions
import com.jhj.prompt.options.interfaces.OnOptionsSelectedListener
import com.jhj.prompt.options.utils.DividerType
import com.jhj.prompt.options.wheel.OptionsWheel
import kotlinx.android.synthetic.main.layout_pickerview_options.view.*
import kotlinx.android.synthetic.main.layout_pickerview_topbar.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor


/**
 * 自定义选择器
 * Created by jhj on 2018-3-6 0006.
 */

class OptionsFragment<T> : BaseDialogFragment() {

    private var wheel: OptionsWheel<T>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGravity = Gravity.BOTTOM
        mAnim = R.style.anim_dialog_bottom
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_style)
        savedInstanceState?.let {
            if (it.getBoolean(Constants.ACTIVITY_DESTROY))
                dismiss()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(Constants.ACTIVITY_DESTROY, true)
    }


    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        wheel?.view?.let {
            setTitleStyle(it)
            setButtonStyle(it)
        }
        return wheel?.view
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


    class Builder<T>(val mContext: Context) : ICommonOptions<Builder<T>> {

        private val fragment = OptionsFragment<T>()
        private val arg = Bundle()
        private var option1 = 0
        private var option2 = 0
        private var option3 = 0
        private val wheel: OptionsWheel<T> = OptionsWheel(mContext)

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
            wheel.view.optionspicker.setBackgroundResource(resource)
            return this
        }

        override fun setOptionsSize(size: Float): Builder<T> {
            wheel.setTextContentSize(size)
            return this
        }

        override fun isOptionsLabel(centerLabel: Boolean): Builder<T> {
            wheel.isCenterLabel(centerLabel)
            return this
        }

        override fun setTextGravity(gravity: Int): Builder<T> {
            wheel.setTextGravity(gravity)
            return this
        }

        override fun setDividerColor(color: Int): Builder<T> {
            wheel.setDividerColor(color)
            return this
        }

        override fun setTextColorOut(color: Int): Builder<T> {
            wheel.setTextColorOut(color)
            return this
        }

        override fun setTextColorCenter(color: Int): Builder<T> {
            wheel.setTextColorCenter(color)
            return this
        }

        override fun setDividerType(type: DividerType): Builder<T> {
            wheel.setDividerType(type)
            return this
        }

        override fun setItemSize(num: Int): Builder<T> {
            wheel.setItemNum(num)
            return this
        }

        override fun setLabels(label1: String?, label2: String?, label3: String?): Builder<T> {
            wheel.setLabels(label1, label2, label3)
            return this
        }

        override fun setCyclic(cyclic: Boolean): Builder<T> {
            wheel.setCyclic(cyclic)
            return this
        }

        override fun setSelectOptions(option1: Int): Builder<T> {
            this.option1 = option1
            setCurrentItems()
            return this
        }

        override fun setSelectOptions(option1: Int, option2: Int): Builder<T> {
            this.option1 = option1
            this.option2 = option2
            setCurrentItems()
            return this
        }

        override fun setSelectOptions(option1: Int, option2: Int, option3: Int): Builder<T> {
            this.option1 = option1
            this.option2 = option2
            this.option3 = option3
            setCurrentItems()
            return this
        }

        override fun setTextXOffset(offset: Int): Builder<T> {
            wheel.setTextXOffset(offset)
            return this
        }

        override fun setItemsSpacingRatio(spacingRatio: Float): Builder<T> {
            wheel.setLineSpacingMultiplier(spacingRatio)
            return this
        }


        override fun setMaxAddHeight(height: Int): Builder<T> {
            wheel.setMaxAddHeight(height)
            return this
        }

        override fun setOutSideCancelable(cancelable: Boolean): Builder<T> {
            arg.putBoolean(Constants.OUT_SIDE_CANCEL, cancelable)
            return this
        }

        override fun setTypeface(font: Typeface): Builder<T> {
            wheel.setTypeface(font)
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


        override fun setSubmitListener(listener: OnOptionsSelectedListener): Builder<T> {
            wheel.view.btnSubmit?.onClick {
                val items = wheel.currentItems
                listener.onOptionsSelect(items[0], items[1], items[2])
                fragment.dismiss()
            }
            return this
        }

        override fun setCancelListener(listener: OnOptionsSelectedListener): Builder<T> {
            wheel.view.btnSubmit?.onClick {
                val items = wheel.currentItems
                listener.onOptionsSelect(items[0], items[1], items[2])
                fragment.dismiss()
            }
            return this
        }

        fun setPicker(optionsItems: ArrayList<T>): Builder<T> {
            setPicker(optionsItems, null, null)
            return this
        }

        fun setPicker(options1Items: ArrayList<T>, options2Items: ArrayList<ArrayList<T>>): Builder<T> {
            setPicker(options1Items, options2Items, null)
            return this
        }

        fun setPicker(options1Items: ArrayList<T>,
                      options2Items: ArrayList<ArrayList<T>>?,
                      options3Items: ArrayList<ArrayList<ArrayList<T>>>?): Builder<T> {

            wheel.setPicker(options1Items, options2Items, options3Items)
            setCurrentItems()
            return this
        }

        //不联动情况下调用
        fun setNPicker(options1Items: ArrayList<T>,
                       options2Items: ArrayList<T>?,
                       options3Items: ArrayList<T>?): Builder<T> {

            wheel.setNPicker(options1Items, options2Items, options3Items)
            setCurrentItems()
            return this
        }


        private fun setCurrentItems() {
            wheel.setCurrentItems(option1, option2, option3)
        }

        override fun show(): Builder<T> {
            fragment.wheel = wheel
            fragment.arguments = arg
            fragment.show((mContext as Activity).fragmentManager)
            return this
        }

        override fun dismiss() {
            fragment.dismiss()
        }
    }
}
