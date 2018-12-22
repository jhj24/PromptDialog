package com.jhj.prompt.dialog.alert

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jhj.prompt.R
import com.jhj.prompt.base.BaseBuilder
import com.jhj.prompt.base.BaseDialogFragment
import com.jhj.prompt.base.Constants
import kotlinx.android.synthetic.main.layout_alert_dialog.view.*
import kotlinx.android.synthetic.main.layout_alert_dialog_body.view.*
import kotlinx.android.synthetic.main.layout_alert_dialog_button.view.*
import kotlinx.android.synthetic.main.layout_alert_dialog_item.view.*
import java.io.Serializable

/**
 * 提示框
 * Created by jhj on 2018-3-14 0014.
 */
class AlertFragment : BaseDialogFragment() {

    companion object {
        private const val TEXT_SIZE_TITLE = 22f
        private const val TEXT_SIZE_MSG = 15f
        private const val TEXT_SIZE_BUTTON = 18f
        private const val TEXT_COLOR_TITLE = Color.BLACK
        private const val TEXT_COLOR_MSG = Color.BLACK
        private const val TEXT_COLOR_SUBMIT = Color.RED
        private const val TEXT_COLOR_CANCEL = Color.BLUE
        private const val TEXT_COLOR_ITEM = Color.BLUE
        private const val TEXT_GRAVITY_TITLE = Gravity.CENTER
        private const val TEXT_GRAVITY_MSG = Gravity.CENTER
    }

    private var title: String? = null
    private var isButtonSeparate: Boolean = false//内容与按钮是否分离
    private var isCustomLayoutShow = false
    private lateinit var inflater: LayoutInflater


    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.inflater = inflater
        this.isButtonSeparate = arguments?.getBoolean(Constants.DIALOG_STYLE) ?: false
        val view = inflater.inflate(R.layout.layout_alert_dialog, container)
        val backgroundResource = arguments?.getInt(Constants.BACKGROUND_RESOURCE, R.drawable.bg_dialog_corner)
                ?: R.drawable.bg_dialog_corner
        view.layout_alert_dialog.setBackgroundResource(backgroundResource)
        view.layout_button_separate.setBackgroundResource(backgroundResource)

        //标题 Title
        title = arguments?.getString(Constants.TITLE)
        if (title.isNullOrBlank()) {
            view.tv_alert_title.visibility = View.GONE
        } else {
            view.tv_alert_title.text = title
            view.tv_alert_title.setTextColor(arguments?.getInt(Constants.TITLE_COLOR, TEXT_COLOR_TITLE)
                    ?: TEXT_COLOR_TITLE)
            view.tv_alert_title.textSize = arguments?.getFloat(Constants.TITLE_SIZE, TEXT_SIZE_TITLE)
                    ?: TEXT_SIZE_TITLE
            view.tv_alert_title.gravity = arguments?.getInt(Constants.TITLE_GRAVITY, Gravity.CENTER)
                    ?: TEXT_GRAVITY_TITLE
        }

        //内容 Message
        val msg = arguments?.getString(Constants.MESSAGE)
        if (msg.isNullOrBlank()) {
            view.tv_alert_msg.visibility = View.GONE
        } else {
            view.tv_alert_msg.text = msg
            view.tv_alert_msg.setTextColor(arguments?.getInt(Constants.MESSAGE_COLOR, TEXT_COLOR_MSG)
                    ?: TEXT_COLOR_MSG)
            view.tv_alert_msg.textSize = arguments?.getFloat(Constants.MESSAGE_SIZE, TEXT_SIZE_MSG)
                    ?: TEXT_SIZE_MSG
            view.tv_alert_msg.gravity = arguments?.getInt(Constants.MESSAGE_GRAVITY, Gravity.CENTER)
                    ?: TEXT_GRAVITY_MSG
        }


        setItemsView(view)
        setCustomLayout(view)
        setButtonView(view)
        return view
    }


    private fun setItemsView(view: View) {
        val commonList = arguments?.getStringArrayList(Constants.ITEM_COMMON_LIST)
        val colorList = arguments?.getStringArrayList(Constants.ITEM_COLOR_LIST)
        val itemsColor = arguments?.getInt(Constants.ITEM_TEXT_COLOR, Color.BLACK) ?: Color.BLACK
        val items = commonList.orEmpty() + colorList.orEmpty()

        if (items.isEmpty()) {
            view.layout_items.visibility = View.GONE
        } else {
            view.tv_alert_msg.visibility = View.GONE//显示列表时，不显示msg布局
            var position: Int = -1
            view.layout_items.visibility = View.VISIBLE
            colorList?.forEach { item ->
                position++
                setItemStyle(view, item, itemsColor, position, items.size)
            }
            commonList?.forEach { item ->
                position++
                setItemStyle(view, item, TEXT_COLOR_ITEM, position, items.size)
            }


            //显示数组时，标题栏高度
            if (!title.isNullOrBlank() && !isCustomLayoutShow) {
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.height = (density * 40).toInt()
                view.tv_alert_title.layoutParams = params
                view.tv_alert_title.textSize = TEXT_SIZE_MSG
                activity?.let {
                    view.tv_alert_title.setTextColor(ContextCompat.getColor(it, R.color.textColor_item_title))
                }
            }

        }
    }

    /**
     * 设置列表
     */
    private fun setItemStyle(view: View, text: String, textColor: Int, position: Int, size: Int) {
        val listener = arguments?.getSerializable(Constants.LISTENER_ITEM_CLICK) as? OnItemClickListener
        val layout = inflater.inflate(R.layout.layout_alert_dialog_item, view.layout_items, false)
        val textView = layout.tv_item_text
        textView.tag = position
        textView.text = text
        textView.setTextColor(textColor)

        //没有标题并且是第一个时,没有顶部分割线
        if (position == 0 && title.isNullOrBlank()) {
            layout.tv_item_line.visibility = View.GONE
            textView.setBackgroundResource(R.drawable.clicked_item_top_round)
        }

        //按钮与item分开，并且是最后一个时
        if (position == size && isButtonSeparate) {
            textView.setBackgroundResource(R.drawable.clicked_item_bottom_round)
        }

        //item点击监听事件
        layout.setOnClickListener {
            listener?.onItemClick(textView, textView.tag as Int)
            dismiss()
        }

        view.layout_items.addView(layout)
    }


    private fun setCustomLayout(view: View) {
        val layoutRes = arguments?.getInt(Constants.CUSTOM_LAYOUT, -1) ?: -1
        val listener = arguments?.getParcelable(Constants.CUSTOM_LISTENER) as? OnCustomListener
        if (layoutRes != -1) {
            isCustomLayoutShow = true
            view.tv_alert_msg.visibility = View.GONE
            view.layout_items.visibility = View.GONE
            val layout = inflater.inflate(layoutRes, view.layout_view)
            view.layout_view.visibility = View.VISIBLE
            listener?.onLayout(layout,this)
        }
    }


    /**
     * 根据DialogStyleEnum设置dialog显示位置以及加载的底部按钮样式
     */
    private fun setButtonView(view: View) {
        if (isButtonSeparate) { //按钮与内容是否分开
            val v = inflater.inflate(R.layout.layout_alert_dialog_button_separate, view.layout_button_separate, false)
            setAlertButtonStyles(v)
            view.layout_button_separate.addView(v)

            //内容与按钮分开的中间间距为 5dp
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(0, (5 * density).toInt(), 0, 0)
            view.layout_button_separate.layoutParams = params

        } else {
            val v = inflater.inflate(R.layout.layout_alert_dialog_button, view.layout_button, false)
            setAlertButtonStyles(v)
            view.layout_button.addView(v)
        }
    }

    /**
     * 根据底部按钮个数以及按钮与内容是否分开设置显示样式
     */
    private fun setAlertButtonStyles(view: View) {
        val submit = arguments?.getString(Constants.SUBMIT_TEXT, "确定")
        val submitColor = arguments?.getInt(Constants.SUBMIT_TEXT_COLOR, TEXT_COLOR_SUBMIT)
                ?: TEXT_COLOR_SUBMIT
        val cancel = arguments?.getString(Constants.CANCEL_TEXT, "取消")
        val cancelColor = arguments?.getInt(Constants.CANCEL_TEXT_COLOR, TEXT_COLOR_CANCEL)
                ?: TEXT_COLOR_CANCEL
        val buttonSize = arguments?.getFloat(Constants.BUTTON_SIZE, TEXT_SIZE_BUTTON)
                ?: TEXT_SIZE_BUTTON
        val submitListener = arguments?.getSerializable(Constants.LISTENER_SUBMIT_CLICK) as? OnButtonClickedListener
        val cancelListener = arguments?.getSerializable(Constants.LISTENER_CANCEL_CLICK) as? OnButtonClickedListener
        var submitButtonShow = false//是否有确定按钮
        var cancelButtonShow = false//是否有取消按钮
        //取消
        cancelListener?.let { listener ->
            cancelButtonShow = true
            view.btn_negative.text = cancel
            view.btn_negative.textSize = buttonSize
            view.btn_negative.setTextColor(ContextCompat.getColorStateList(requireContext(), cancelColor))
            view.btn_negative.setOnClickListener {
                listener.onClick(view.btn_negative)
                dismiss()
            }
        }

        submitListener?.let { listener ->
            submitButtonShow = true
            view.btn_positive.text = submit
            view.btn_positive.textSize = buttonSize
            view.btn_positive.setTextColor(ContextCompat.getColorStateList(requireContext(), submitColor))
            view.btn_positive.setOnClickListener {
                listener.onClick(view.btn_positive)
                dismiss()
            }
        }

        //设置显示按钮个数
        if (cancelButtonShow || submitButtonShow) {
            view.layout_alert_button.visibility = View.VISIBLE
            view.above_button_line.visibility = View.VISIBLE
            if (cancelButtonShow && submitButtonShow) {
                view.middle_button_line.visibility = View.VISIBLE
                view.btn_positive.visibility = View.VISIBLE
                view.btn_negative.visibility = View.VISIBLE
            } else if (cancelButtonShow) {
                view.btn_negative.visibility = View.VISIBLE
            } else {
                view.btn_positive.visibility = View.VISIBLE
            }
        }


        //根据按钮的个数以及按钮和内容是否分开，设置点击时的按钮背景色
        if (cancelButtonShow && submitButtonShow) {//两个按钮都显示
            if (isButtonSeparate) {//按钮与内容分开
                view.btn_negative.setBackgroundResource(R.drawable.clicked_item_left_round)
                view.btn_positive.setBackgroundResource(R.drawable.clicked_item_right_round)
            } else {
                view.btn_negative.setBackgroundResource(R.drawable.clicked_item_left_bottom_round)
                view.btn_positive.setBackgroundResource(R.drawable.clicked_item_right_bottom_round)
            }
        } else {//只显示一个按钮
            if (isButtonSeparate) {//按钮与内容分开
                view.btn_negative.setBackgroundResource(R.drawable.clicked_item_all_round)
                view.btn_positive.setBackgroundResource(R.drawable.clicked_item_all_round)
            } else {
                view.btn_negative.setBackgroundResource(R.drawable.clicked_item_bottom_round)
                view.btn_positive.setBackgroundResource(R.drawable.clicked_item_bottom_round)
            }
        }

    }


    class Builder(val mContext: Context) : BaseBuilder<Builder>() {

        private val fragment = AlertFragment()

        fun setDialogBottomSeparate(isSeparate: Boolean): Builder {
            arg.putBoolean(Constants.DIALOG_STYLE, isSeparate)
            return this
        }

        fun setBackgroundResource(@DrawableRes resource: Int): Builder {
            arg.putInt(Constants.BACKGROUND_RESOURCE, resource)
            return this
        }

        fun setTitle(title: String): Builder {
            arg.putString(Constants.TITLE, title)
            return this
        }

        fun setTitleColor(@ColorRes titleColor: Int): Builder {
            arg.putInt(Constants.TITLE_COLOR, titleColor)
            return this
        }

        fun setTitleSize(titleSize: Float): Builder {
            arg.putFloat(Constants.TITLE_SIZE, titleSize)
            return this
        }

        fun setTitleGravity(titleGravity: Int): Builder {
            arg.putSerializable(Constants.TITLE_GRAVITY, titleGravity)
            return this
        }

        fun setMessage(message: String): Builder {
            arg.putString(Constants.MESSAGE, message)
            return this
        }

        fun setMessageColor(@ColorRes messageColor: Int): Builder {
            arg.putInt(Constants.MESSAGE_COLOR, messageColor)
            return this
        }

        fun setMessageSize(messageSize: Float): Builder {
            arg.putFloat(Constants.MESSAGE_SIZE, messageSize)
            return this
        }

        fun setMessageGravity(messageGravity: Int): Builder {
            arg.putSerializable(Constants.MESSAGE_GRAVITY, messageGravity)
            return this
        }

        fun setItems(items: ArrayList<String>): Builder {
            arg.putStringArrayList(Constants.ITEM_COMMON_LIST, items as ArrayList<String>?)
            return this
        }

        fun setItems(items: ArrayList<String>, @ColorRes textColor: Int): Builder {
            arg.putStringArrayList(Constants.ITEM_COLOR_LIST, items)
            arg.putInt(Constants.ITEM_TEXT_COLOR, textColor)
            return this
        }

        fun setLayoutRes(@LayoutRes resource: Int): Builder {
            arg.putInt(Constants.CUSTOM_LAYOUT, resource)
            return this
        }

        fun setLayoutRes(@LayoutRes resource: Int, listener: OnCustomListener): Builder {
            arg.putInt(Constants.CUSTOM_LAYOUT, resource)
            arg.putParcelable(Constants.CUSTOM_LISTENER, listener)
            return this
        }

        fun setSubmitListener(listener: OnButtonClickedListener): Builder {
            arg.putSerializable(Constants.LISTENER_SUBMIT_CLICK, listener)
            return this
        }

        fun setSubmitListener(text: String?, listener: OnButtonClickedListener): Builder {
            arg.putSerializable(Constants.LISTENER_SUBMIT_CLICK, listener)
            arg.putString(Constants.SUBMIT_TEXT, text)
            return this
        }

        fun setSubmitListener(text: String?, @DrawableRes textColor: Int, listener: OnButtonClickedListener): Builder {
            arg.putSerializable(Constants.LISTENER_SUBMIT_CLICK, listener)
            arg.putString(Constants.SUBMIT_TEXT, text)
            arg.putInt(Constants.SUBMIT_TEXT_COLOR, textColor)
            return this
        }

        fun setCancelListener(listener: OnButtonClickedListener): Builder {
            arg.putSerializable(Constants.LISTENER_CANCEL_CLICK, listener)
            return this
        }

        fun setCancelListener(text: String?, listener: OnButtonClickedListener): Builder {
            arg.putSerializable(Constants.LISTENER_CANCEL_CLICK, listener)
            arg.putString(Constants.CANCEL_TEXT, text)
            return this
        }

        fun setCancelListener(text: String?, @DrawableRes textColor: Int, listener: OnButtonClickedListener): Builder {
            arg.putSerializable(Constants.LISTENER_CANCEL_CLICK, listener)
            arg.putString(Constants.CANCEL_TEXT, text)
            arg.putInt(Constants.CANCEL_TEXT_COLOR, textColor)
            return this
        }

        fun setButtonTextSize(size: Float): Builder {
            arg.putFloat(Constants.BUTTON_SIZE, size)
            return this
        }

        fun setItemClickedListener(listener: OnItemClickListener): Builder {
            arg.putSerializable(Constants.LISTENER_ITEM_CLICK, listener)
            return this
        }


        fun isShow(): Boolean {
            return fragment.isShow() ?: false
        }

        fun show(): Builder {
            fragment.arguments = arg
            fragment.show((mContext as FragmentActivity).supportFragmentManager)
            return this
        }

        fun dismiss() {
            fragment.dismiss()
        }
    }

    interface OnButtonClickedListener : Serializable {
        fun onClick(view: View?)
    }

    interface OnItemClickListener : Serializable {
        fun onItemClick(view: View, position: Int)
    }

    interface OnCustomListener : Parcelable {

        fun onLayout(view: View, alertFragment: AlertFragment)

        override fun writeToParcel(dest: Parcel?, flags: Int) {
        }

        override fun describeContents(): Int {
            return 0
        }
    }
}