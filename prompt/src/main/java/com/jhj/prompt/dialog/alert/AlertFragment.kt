package com.jhj.prompt.dialog.alert

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jhj.prompt.R
import com.jhj.prompt.base.BaseDialogFragment
import com.jhj.prompt.base.Constants
import com.jhj.prompt.dialog.alert.constants.DialogStyle
import com.jhj.prompt.dialog.alert.constants.TextGravity
import com.jhj.prompt.dialog.alert.interfaces.IAlertDialog
import com.jhj.prompt.dialog.alert.interfaces.OnButtonClickedListener
import com.jhj.prompt.dialog.alert.interfaces.OnCustomListener
import com.jhj.prompt.dialog.alert.interfaces.OnItemClickListener
import com.jhj.prompt.listener.OnDialogShowOnBackListener
import kotlinx.android.synthetic.main.layout_alert_dialog.view.*
import kotlinx.android.synthetic.main.layout_alert_dialog_body.view.*
import kotlinx.android.synthetic.main.layout_alert_dialog_button.view.*
import kotlinx.android.synthetic.main.layout_alert_dialog_item.view.*
import org.jetbrains.anko.displayMetrics
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * 提示框
 * Created by jhj on 2018-3-14 0014.
 */
class AlertFragment : BaseDialogFragment() {

    companion object {
        private const val TEXT_SIZE_TITLE = 22f
        private const val TEXT_SIZE_MSG = 16f
        private const val TEXT_SIZE_BUTTON = 18f
        private const val TEXT_COLOR_TITLE = Color.BLACK
        private const val TEXT_COLOR_MSG = Color.BLACK
        private const val TEXT_COLOR_SUBMIT = Color.RED
        private const val TEXT_COLOR_CANCEL = Color.BLUE
        private val TEXT_GRAVITY_TITLE = TextGravity.CENTER
        private val TEXT_GRAVITY_MSG = TextGravity.CENTER
    }

    private var title: String? = null
    private var style = DialogStyle.DIALOG_CENTER
    private var isItemsShow: Boolean = false //是否有列表
    private var isButtonSeparate: Boolean = false//内容与按钮是否分离
    private var isCustomLayoutShow = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        style = arguments.getSerializable(Constants.DIALOG_STYLE) as? DialogStyle
                ?: DialogStyle.DIALOG_CENTER
        val animResource = arguments.getInt(Constants.ANIMATION, -1)

        //内容与按钮是否分割
        isButtonSeparate = (style == DialogStyle.DIALOG_BOTTOM_SEPARATE
                || style == DialogStyle.DIALOG_CENTER_SEPARATE
                || style == DialogStyle.DIALOG_TOP_SEPARATE)


        mAnim = if (animResource == -1) {//dialog动画
            when (style) {
                DialogStyle.DIALOG_BOTTOM, DialogStyle.DIALOG_BOTTOM_SEPARATE ->
                    R.style.anim_dialog_bottom
                DialogStyle.DIALOG_CENTER, DialogStyle.DIALOG_CENTER_SEPARATE ->
                    R.style.anim_dialog_center
                DialogStyle.DIALOG_TOP, DialogStyle.DIALOG_TOP_SEPARATE ->
                    R.style.anim_dialog_top
            }
        } else {
            animResource
        }

        mGravity = when (style) {
            DialogStyle.DIALOG_TOP, DialogStyle.DIALOG_TOP_SEPARATE -> Gravity.TOP
            DialogStyle.DIALOG_CENTER, DialogStyle.DIALOG_CENTER_SEPARATE -> Gravity.CENTER
            DialogStyle.DIALOG_BOTTOM, DialogStyle.DIALOG_BOTTOM_SEPARATE -> Gravity.BOTTOM
        }


    }

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_alert_dialog, container)
        val backgroundResource = arguments.getInt(Constants.BACKGROUND_RESOURCE, R.drawable.bg_dialog)
        view.layout_alert_dialog.setBackgroundResource(backgroundResource)
        view.layout_button_separate.setBackgroundResource(backgroundResource)
        setTitle(view)
        setMessage(view)
        setItems(view)
        setCustomLayout(view)
        initButtonView(view)
        setDialogPadding(view)
        return view
    }


    private fun setTitle(view: View) {
        title = arguments.getString(Constants.TITLE)
        val color = arguments.getInt(Constants.TITLE_COLOR, TEXT_COLOR_TITLE)
        val size = arguments.getFloat(Constants.TITLE_SIZE, TEXT_SIZE_TITLE)
        val gravity = arguments.getSerializable(Constants.TITLE_GRAVITY) as? TextGravity
        if (title.isNullOrBlank()) {
            view.tv_alert_title.visibility = View.GONE
        } else {
            view.tv_alert_title.text = title
            view.tv_alert_title.setTextColor(color)
            view.tv_alert_title.textSize = size
            view.tv_alert_title.gravity = gravity?.value ?: TEXT_GRAVITY_TITLE.value
        }
    }

    private fun setMessage(view: View) {
        val msg = arguments.getString(Constants.MESSAGE)
        val color = arguments.getInt(Constants.MESSAGE_COLOR, TEXT_COLOR_MSG)
        val size = arguments.getFloat(Constants.MESSAGE_SIZE, TEXT_SIZE_MSG)
        val gravity = arguments.getSerializable(Constants.MESSAGE_GRAVITY) as? TextGravity

        if (msg.isNullOrBlank()) {
            view.tv_alert_msg.visibility = View.GONE
        } else {
            view.tv_alert_msg.text = msg
            view.tv_alert_msg.setTextColor(color)
            view.tv_alert_msg.textSize = size
            view.tv_alert_msg.gravity = gravity?.value ?: TEXT_GRAVITY_MSG.value
        }
    }

    private fun setItems(view: View) {
        val commonList = arguments.getStringArrayList(Constants.ITEM_COMMON_LIST)
        val colorList = arguments.getStringArrayList(Constants.ITEM_COLOR_LIST)
        val itemsColor = arguments.getInt(Constants.ITEM_TEXT_COLOR, Color.BLACK)
        val items = commonList.orEmpty() + colorList.orEmpty()

        if (items.isEmpty()) {
            view.layout_items.visibility = View.GONE
        } else {
            isItemsShow = true
            view.tv_alert_msg.visibility = View.GONE//显示列表时，不显示msg布局
            var position: Int = -1
            view.layout_items.visibility = View.VISIBLE
            colorList?.forEach { item ->
                position++
                setItemStyle(view, item, itemsColor, position, items.size)
            }
            commonList?.forEach { item ->
                position++
                setItemStyle(view, item, Color.BLUE, position, items.size)
            }
        }
    }

    /**
     * 设置列表
     */
    private fun setItemStyle(view: View, text: String, textColor: Int, position: Int, size: Int) {
        val listener = arguments.getSerializable(Constants.LISTENER_ITEM_CLICK) as? OnItemClickListener
        val inflater = LayoutInflater.from(activity)
        val layout = inflater.inflate(R.layout.layout_alert_dialog_item, view.layout_items, false)
        val textView = layout.tv_item_text
        textView.tag = position
        textView.text = text
        textView.setTextColor(textColor)

        //没有标题并且是第一个时,没有顶部分割线
        if (position == 0 && title.isNullOrBlank()) {
            layout.tv_item_line.visibility = View.GONE
            textView.setBackgroundResource(R.drawable.bg_item_click_top_round)
        }

        //按钮与item分开，并且是最后一个时
        if (position == size && isButtonSeparate) {
            textView.setBackgroundResource(R.drawable.bg_item_click_bottom_round)
        }

        //item点击监听事件
        layout.setOnClickListener {
            listener?.onItemClick(textView, textView.tag as Int)
            dismiss()
        }

        view.layout_items.addView(layout)
    }


    private fun setCustomLayout(view: View) {
        val layoutRes = arguments.getInt(Constants.CUSTOM_LAYOUT, -1)
        val listener = arguments.getSerializable(Constants.CUSTOM_LISTENER) as? OnCustomListener
        if (layoutRes != -1) {
            isCustomLayoutShow = true
            view.tv_alert_msg.visibility = View.GONE
            view.layout_items.visibility = View.GONE
            val layout = LayoutInflater.from(activity).inflate(layoutRes, view.layout_view)
            view.layout_view.visibility = View.VISIBLE
            listener?.onLayout(layout)
        }
    }


    /**
     * 根据DialogStyle设置dialog显示位置以及加载的底部按钮样式
     */
    private fun initButtonView(view: View) {
        val inflater = LayoutInflater.from(activity)
        if (isButtonSeparate) { //按钮与内容是否分开
            val v = inflater.inflate(R.layout.layout_alert_dialog_button_separate, view.layout_button_separate, false)
            setAlertButtonStyles(v)
            view.layout_button_separate.addView(v)
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
        val submit = arguments.getString(Constants.SUBMIT_TEXT, "确定")
        val submitColor = arguments.getInt(Constants.SUBMIT_TEXT_COLOR, TEXT_COLOR_SUBMIT)
        val cancel = arguments.getString(Constants.CANCEL_TEXT, "取消")
        val cancelColor = arguments.getInt(Constants.CANCEL_TEXT_COLOR, TEXT_COLOR_CANCEL)
        val buttonSize = arguments.getFloat(Constants.BUTTON_SIZE, TEXT_SIZE_BUTTON)
        val submitListener = arguments.getSerializable(Constants.LISTENER_SUBMIT_CLICK) as? OnButtonClickedListener
        val cancelListener = arguments.getSerializable(Constants.LISTENER_CANCEL_CLICK) as? OnButtonClickedListener
        var submitButtonShow = false//是否有确定按钮
        var cancelButtonShow = false//是否有取消按钮
        //取消
        cancelListener?.let { listener ->
            cancelButtonShow = true
            view.btn_negative.text = cancel
            view.btn_negative.textSize = buttonSize
            view.btn_negative.setTextColor(cancelColor)
            view.btn_negative.onClick {
                listener.onClick(view.btn_negative)
                dismiss()
            }
        }

        submitListener?.let { listener ->
            submitButtonShow = true
            view.btn_positive.text = submit
            view.btn_positive.textSize = buttonSize
            view.btn_positive.setTextColor(submitColor)
            view.btn_positive.onClick {
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
                view.btn_negative.setBackgroundResource(R.drawable.bg_item_click_left_round)
                view.btn_positive.setBackgroundResource(R.drawable.bg_item_click_right_round)
            } else {
                view.btn_negative.setBackgroundResource(R.drawable.bg_item_click_left_bottom_round)
                view.btn_positive.setBackgroundResource(R.drawable.bg_item_click_right_bottom_round)
            }
        } else {//只显示一个按钮
            if (isButtonSeparate) {//按钮与内容分开
                view.btn_negative.setBackgroundResource(R.drawable.bg_item_click_all_round)
                view.btn_positive.setBackgroundResource(R.drawable.bg_item_click_all_round)
            } else {
                view.btn_negative.setBackgroundResource(R.drawable.bg_item_click_bottom_round)
                view.btn_positive.setBackgroundResource(R.drawable.bg_item_click_bottom_round)
            }
        }
    }

    private fun setDialogPadding(view: View) {
        val paddingTop = arguments.getInt(Constants.PADDING_TOP, -1)
        val paddingBottom = arguments.getInt(Constants.PADDING_BOTTOM, -1)
        val paddingHorizontal = arguments.getInt(Constants.PADDING_HORIZONTAL, -1)

        //没有设置padding时，默认边距
        if (paddingBottom == -1 && paddingTop == -1 && paddingHorizontal == -1) {
            val dm = DisplayMetrics()
            val density = activity.resources.displayMetrics.density
            dialog.window.windowManager.defaultDisplay.getMetrics(dm)
            when (style) {
                DialogStyle.DIALOG_TOP,
                DialogStyle.DIALOG_BOTTOM,
                DialogStyle.DIALOG_TOP_SEPARATE,
                DialogStyle.DIALOG_BOTTOM_SEPARATE -> {
                    attr?.width = dm.widthPixels - (density * 30).toInt()
                    attr?.height = LinearLayout.LayoutParams.WRAP_CONTENT
                    attr?.y = (density * 5).toInt()
                }

                DialogStyle.DIALOG_CENTER,
                DialogStyle.DIALOG_CENTER_SEPARATE -> {
                    attr?.width = dm.widthPixels - (density * 80).toInt()
                    attr?.height = LinearLayout.LayoutParams.WRAP_CONTENT
                }
            }
        }


        //内容与按钮分开的中间间距为 5dp
        if (isButtonSeparate) {
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(0, (activity.displayMetrics.density * 5).toInt(), 0, 0)
            view.layout_button_separate.layoutParams = params
        }

        //显示数组时，标题栏高度
        if (isItemsShow && !title.isNullOrBlank() && !isCustomLayoutShow) {
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.height = (activity.displayMetrics.density * 40).toInt()
            view.tv_alert_title.layoutParams = params
            view.tv_alert_title.textSize = 15f
            view.tv_alert_title.setTextColor(ContextCompat.getColor(activity, R.color.textColor_item_title))
        }
    }

    class Builder(val mContext: Context) : IAlertDialog<Builder> {

        private val arg = Bundle()
        private val fragment = AlertFragment()

        override fun setDialogStyle(style: DialogStyle): Builder {
            arg.putSerializable(Constants.DIALOG_STYLE, style)
            return this
        }

        override fun setBackgroundResource(resource: Int): Builder {
            arg.putInt(Constants.BACKGROUND_RESOURCE, resource)
            return this
        }

        override fun setTitle(title: String): Builder {
            arg.putString(Constants.TITLE, title)
            return this
        }

        override fun setTitleColor(titleColor: Int): Builder {
            arg.putInt(Constants.TITLE_COLOR, titleColor)
            return this
        }

        override fun setTitleSize(titleSize: Float): Builder {
            arg.putFloat(Constants.TITLE_SIZE, titleSize)
            return this
        }

        override fun setTitleGravity(titleGravity: TextGravity): Builder {
            arg.putSerializable(Constants.TITLE_GRAVITY, titleGravity)
            return this
        }

        override fun setMessage(message: String): Builder {
            arg.putString(Constants.MESSAGE, message)
            return this
        }

        override fun setMessageColor(messageColor: Int): Builder {
            arg.putInt(Constants.MESSAGE_COLOR, messageColor)
            return this
        }

        override fun setMessageSize(messageSize: Float): Builder {
            arg.putFloat(Constants.MESSAGE_SIZE, messageSize)
            return this
        }

        override fun setMessageGravity(messageGravity: TextGravity): Builder {
            arg.putSerializable(Constants.MESSAGE_GRAVITY, messageGravity)
            return this
        }

        override fun setItems(items: ArrayList<String>): Builder {
            arg.putStringArrayList(Constants.ITEM_COMMON_LIST, items as ArrayList<String>?)
            return this
        }

        override fun setItems(items: ArrayList<String>, textColor: Int): Builder {
            arg.putStringArrayList(Constants.ITEM_COLOR_LIST, items)
            arg.putInt(Constants.ITEM_TEXT_COLOR, textColor)
            return this
        }

        override fun setLayoutRes(resource: Int): Builder {
            arg.putInt(Constants.CUSTOM_LAYOUT, resource)
            return this
        }

        override fun setLayoutRes(resource: Int, listener: OnCustomListener): Builder {
            arg.putInt(Constants.CUSTOM_LAYOUT, resource)
            arg.putSerializable(Constants.CUSTOM_LISTENER, listener)
            return this
        }

        override fun setSubmitListener(listener: OnButtonClickedListener): Builder {
            arg.putSerializable(Constants.LISTENER_SUBMIT_CLICK, listener)
            return this
        }

        override fun setSubmitListener(text: String?, listener: OnButtonClickedListener): Builder {
            arg.putSerializable(Constants.LISTENER_SUBMIT_CLICK, listener)
            arg.putString(Constants.SUBMIT_TEXT, text)
            return this
        }

        override fun setSubmitListener(text: String?, textColor: Int, listener: OnButtonClickedListener): Builder {
            arg.putSerializable(Constants.LISTENER_SUBMIT_CLICK, listener)
            arg.putString(Constants.SUBMIT_TEXT, text)
            arg.putInt(Constants.SUBMIT_TEXT_COLOR, textColor)
            return this
        }

        override fun setCancelListener(listener: OnButtonClickedListener): Builder {
            arg.putSerializable(Constants.LISTENER_CANCEL_CLICK, listener)
            return this
        }

        override fun setCancelListener(text: String?, listener: OnButtonClickedListener): Builder {
            arg.putSerializable(Constants.LISTENER_CANCEL_CLICK, listener)
            arg.putString(Constants.CANCEL_TEXT, text)
            return this
        }

        override fun setCancelListener(text: String?, textColor: Int, listener: OnButtonClickedListener): Builder {
            arg.putSerializable(Constants.LISTENER_CANCEL_CLICK, listener)
            arg.putString(Constants.CANCEL_TEXT, text)
            arg.putInt(Constants.CANCEL_TEXT_COLOR, textColor)
            return this
        }

        override fun setButtonTextSize(size: Float): Builder {
            arg.putFloat(Constants.BUTTON_SIZE, size)
            return this
        }

        override fun setItemClickedListener(listener: OnItemClickListener): Builder {
            arg.putSerializable(Constants.LISTENER_ITEM_CLICK, listener)
            return this
        }

        override fun setCanceledOnTouchOutSide(cancel: Boolean): Builder {
            arg.putBoolean(Constants.OUT_SIDE_CANCEL, cancel)
            return this
        }


        override fun setPaddingTop(top: Int): Builder {
            arg.putInt(Constants.PADDING_TOP, top)
            return this
        }

        override fun setPaddingBottom(bottom: Int): Builder {
            arg.putInt(Constants.PADDING_BOTTOM, bottom)
            return this
        }

        override fun setPaddingHorizontal(horizontal: Int): Builder {
            arg.putInt(Constants.PADDING_HORIZONTAL, horizontal)
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