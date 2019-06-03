package com.jhj.prompt.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.DrawableRes
import android.support.annotation.IntRange
import android.support.annotation.LayoutRes
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import com.jhj.prompt.R
import com.jhj.prompt.fragment.base.BaseBuilder
import com.jhj.prompt.fragment.base.BaseDialogFragment
import com.jhj.prompt.fragment.base.Constants
import kotlinx.android.synthetic.main.layout_alert_dialog.view.*
import kotlinx.android.synthetic.main.layout_alert_dialog_body.view.*
import kotlinx.android.synthetic.main.layout_alert_dialog_button.view.*
import kotlinx.android.synthetic.main.layout_alert_dialog_item_selected.view.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.support.v4.dimen
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.textColor
import java.io.Serializable

/**
 * 提示框
 * Created by jhj on 2018-3-14 0014.
 */
class AlertFragment : BaseDialogFragment<AlertFragment>() {


    private var title: String? = null
    var submitButtonShow = false//是否有确定按钮
    var cancelButtonShow = false//是否有取消按钮
    private var isButtonSeparate: Boolean = false//内容与按钮是否分离
    private var selectedStatedArray = intArrayOf()
    private lateinit var selectedDataList: ArrayList<String>
    private var selectedMaxNum = -1
    private var selectedMinNum = 1
    private var isHasItemCommon: Boolean = false
    private var isHasItemSelected: Boolean = false
    private var isHasCustomLayout: Boolean = false
    private var listItemSize = 7

    override val layoutRes: Int
        get() = R.layout.layout_alert_dialog


    override fun setAttributes(window: Window) {
        super.setAttributes(window)

        if ((isHasItemCommon || isHasItemSelected) && !isHasCustomLayout) { //显示Item
            val size = selectedDataList.size
            if (size > listItemSize) {
                val attr = window.attributes
                val buttonHeight = dimen(R.dimen.height_alert_button)
                attr.height = if (attr.gravity == Gravity.BOTTOM && marginBottom == -1) {
                    buttonHeight + dimen(R.dimen.margin_button_separate) + dimen(R.dimen.height_alert_item) * 8 + dimen(R.dimen.height_bottom_item_title)
                } else {
                    buttonHeight + dimen(R.dimen.height_alert_item) * 6 + dimen(R.dimen.height_bottom_item_title)
                }
                window.attributes = attr
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.isButtonSeparate = arguments?.getBoolean(Constants.DIALOG_STYLE) ?: false
        val backgroundResource = arguments?.getInt(Constants.BACKGROUND_RESOURCE, config.alertBackgroundResource)
                ?: config.alertBackgroundResource
        val selectedItemList = arguments?.getIntegerArrayList(Constants.ITEM_SELECTED_LIST)
        selectedItemList?.let {
            selectedStatedArray.forEachIndexed { index, i ->
                if (it.contains(index)) {
                    selectedStatedArray[index] = 1
                }
            }
        }


        view.layout_alert_dialog.setBackgroundResource(backgroundResource)
        view.layout_button_separate.setBackgroundResource(backgroundResource)

        //标题 Title
        title = arguments?.getString(Constants.TITLE)
        if (title.isNullOrBlank()) {
            view.tv_alert_title.visibility = View.GONE
        } else if ((isHasItemCommon || isHasItemSelected) && !isHasCustomLayout) {
            //显示数组时，标题栏高度
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.height = dimen(R.dimen.height_bottom_item_title)
            view.tv_alert_title.layoutParams = params
            view.tv_alert_title.textSize = config.alertTextSizeItemTitle
            view.tv_alert_title.setTextColor(arguments?.getInt(Constants.TITLE_COLOR, config.alertTextColorItemTitle)
                    ?: config.alertTextColorItemTitle)
        } else {
            view.tv_alert_title.setTextColor(arguments?.getInt(Constants.TITLE_COLOR, config.alertTextColorTitle)
                    ?: config.alertTextColorTitle)
            view.tv_alert_title.textSize = arguments?.getFloat(Constants.TITLE_SIZE, config.alertTextSizeTitle)
                    ?: config.alertTextSizeTitle
        }
        view.tv_alert_title.text = title
        view.tv_alert_title.gravity = arguments?.getInt(Constants.TITLE_GRAVITY, config.alertTextGravityTitle)
                ?: config.alertTextGravityTitle

        //内容 Message
        val msg = arguments?.getString(Constants.MESSAGE)
        if (msg.isNullOrBlank()) {
            view.tv_alert_msg.visibility = View.GONE
        } else {
            view.tv_alert_msg.text = msg
            view.tv_alert_msg.setTextColor(arguments?.getInt(Constants.MESSAGE_COLOR, config.alertTextColorMessage)
                    ?: config.alertTextColorMessage)
            view.tv_alert_msg.textSize = arguments?.getFloat(Constants.MESSAGE_SIZE, config.alertTextSizeMessage)
                    ?: config.alertTextSizeMessage
            view.tv_alert_msg.gravity = arguments?.getInt(Constants.MESSAGE_GRAVITY, config.alertTextGravityMessage)
                    ?: config.alertTextGravityMessage
        }
        setButtonView(view)
        if (isHasCustomLayout) {
            setCustomLayout(view)
            view.tv_alert_msg.visibility = View.GONE
            view.layout_items.visibility = View.GONE
            view.layout_view.visibility = View.VISIBLE
        } else if (isHasItemSelected) {
            setSelectItem(view)
            view.tv_alert_msg.visibility = View.GONE//显示列表时，不显示msg布局
            view.layout_items.visibility = View.VISIBLE
        } else if (isHasItemCommon) {
            setCommonItems(view)
            view.tv_alert_msg.visibility = View.GONE//显示列表时，不显示msg布局
            view.layout_items.visibility = View.VISIBLE
        }
    }


    override fun initParams(bundle: Bundle?) {
        super.initParams(bundle)
        selectedDataList = bundle?.getStringArrayList(Constants.ITEM_SELECTED_DATA_LIST)
                ?: arrayListOf<String>()
        selectedStatedArray = bundle?.getIntArray("array")
                ?: IntArray(selectedDataList.size)
        selectedMaxNum = bundle?.getInt(Constants.ITEM_SELECTED_MAX, selectedDataList.size)
                ?: selectedDataList.size
        selectedMinNum = bundle?.getInt(Constants.ITEM_SELECTED_MIN, 1)
                ?: 1
        isHasItemCommon = arguments?.getBoolean(Constants.is_HAS_ITEM_LAYOUT, false)
                ?: false
        isHasItemSelected = arguments?.getBoolean(Constants.IS_HAS_ITEM_SELECTED, false)
                ?: false
        isHasCustomLayout = arguments?.getBoolean(Constants.IS_HAS_CUSTOM_LAYOUT, false)
                ?: false
        listItemSize = arguments?.getInt(Constants.SHOW_MAX_ITEM_SIZE, 7)
                ?: 7
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(Constants.ITEM_SELECTED_DATA_LIST, selectedDataList)
        outState.putIntArray("array", selectedStatedArray)
        outState.putInt(Constants.ITEM_SELECTED_MIN, selectedMinNum)
        outState.putInt(Constants.ITEM_SELECTED_MAX, selectedMaxNum)
    }


    private fun setCommonItems(view: View) {
        val commonList = arguments?.getStringArrayList(Constants.ITEM_COMMON_LIST)
        val colorList = arguments?.getStringArrayList(Constants.ITEM_COLOR_LIST)
        val itemsColor = arguments?.getInt(Constants.ITEM_TEXT_COLOR, config.alertTextColorItem)
                ?: config.alertTextColorItem
        val items = commonList.orEmpty() + colorList.orEmpty()
        var position: Int = -1
        colorList?.forEach { item ->
            position++
            setItemStyle(view, item, itemsColor, position, items.size)
        }
        commonList?.forEach { item ->
            position++
            setItemStyle(view, item, config.alertTextColorItem, position, items.size)
        }

    }


    private fun setSelectItem(view: View) {
        val imageSelectedRes = arguments?.getInt(Constants.ITEM_SELECTED_IMAGE, config.alertItemImageSelected)
                ?: config.alertItemImageSelected
        val imageUnselectedRes = arguments?.getInt(Constants.ITEM_UNSELECTED_IMAGE, config.alertItemImageUnselected)
                ?: config.alertItemImageUnselected
        val itemTextSize = arguments?.getFloat(Constants.ITEM_TEXT_SIZE, config.alertTextSizeItem)
                ?: config.alertTextSizeItem
        val itemTextColor = arguments?.getInt(Constants.ITEM_TEXT_COLOR, config.alertTextColorItem)
                ?: config.alertTextColorItem



        selectedDataList.forEachIndexed { index, text ->
            val itemView = inflater.inflate(R.layout.layout_alert_dialog_item_selected, view.layout_items, false)
            itemView.tv_item_text.text = text
            itemView.tv_item_text.textSize = itemTextSize
            itemView.tv_item_text.textColor = itemTextColor

            //没有标题并且是第一个时,没有顶部分割线
            if (index == 0 && title.isNullOrBlank()) {
                itemView.tv_item_line.visibility = View.GONE
                itemView.layout_alert_item.setBackgroundResource(R.drawable.clicked_item_top_round)
            }

            //按钮与item分开，并且是最后一个时
            if (index == selectedDataList.size - 1 && (isButtonSeparate || (!submitButtonShow && !cancelButtonShow))) {
                itemView.layout_alert_item.setBackgroundResource(R.drawable.clicked_item_bottom_round)
            }

            if (selectedStatedArray[index] == 1) {
                itemView.iv_alert_selected.imageResource = imageSelectedRes
            } else {
                itemView.iv_alert_selected.imageResource = imageUnselectedRes
            }

            itemView.setOnClickListener {

                val size = selectedStatedArray.filter { it == 1 }.size

                if (size >= selectedMaxNum && selectedStatedArray[index] == 0) {
                    toast("最多选择${selectedMaxNum}个")
                    return@setOnClickListener
                }

                if (selectedStatedArray[index] == 0) {
                    selectedStatedArray[index] = 1
                } else {
                    selectedStatedArray[index] = 0
                }

                if (selectedStatedArray[index] == 1) {
                    itemView.iv_alert_selected.imageResource = imageSelectedRes
                } else {
                    itemView.iv_alert_selected.imageResource = imageUnselectedRes
                }
            }

            view.layout_items.addView(itemView)
        }

    }


    /**
     * 设置列表
     */
    private fun setItemStyle(view: View, text: String, textColor: Int, position: Int, size: Int) {
        val listener = arguments?.getSerializable(Constants.LISTENER_ITEM_CLICK) as? OnItemClickListener
        val itemTextSize = arguments?.getFloat(Constants.ITEM_TEXT_SIZE, config.alertTextSizeItem)
                ?: config.alertTextSizeItem
        val itemView = inflater.inflate(R.layout.layout_alert_dialog_item, view.layout_items, false)
        val textView = itemView.tv_item_text
        textView.tag = position
        textView.text = text
        textView.setTextColor(textColor)
        textView.textSize = itemTextSize

        //没有标题并且是第一个时,没有顶部分割线
        if (position == 0 && title.isNullOrBlank()) {
            itemView.tv_item_line.visibility = View.GONE
            textView.setBackgroundResource(R.drawable.clicked_item_top_round)
        }

        //按钮与item分开，并且是最后一个时
        if (position == size - 1 && (isButtonSeparate || (!submitButtonShow && !cancelButtonShow))) {
            textView.setBackgroundResource(R.drawable.clicked_item_bottom_round)
        }

        //item点击监听事件
        itemView.setOnClickListener {
            listener?.onItemClick(this, textView, textView.tag as Int)
            dismiss()
        }

        view.layout_items.addView(itemView)
    }


    private fun setCustomLayout(view: View) {
        val layoutRes = arguments?.getInt(Constants.CUSTOM_LAYOUT, -1) ?: -1
        val listener = arguments?.getParcelable(Constants.CUSTOM_LISTENER) as? OnCustomListener
        if (layoutRes != -1) {
            val layout = inflater.inflate(layoutRes, view.layout_view)
            listener?.onLayout(this, layout)
        }
    }


    /**
     * 根据DialogStyleEnum设置dialog显示位置以及加载的底部按钮样式
     */
    private fun setButtonView(view: View) {
        if (isButtonSeparate) { //按钮与内容是否分开
            val bottomView = inflater.inflate(R.layout.layout_alert_dialog_button_separate, view.layout_button_separate, false)
            setAlertButtonStyles(bottomView)
            view.layout_button_separate.addView(bottomView)

            //内容与按钮分开的中间间距为 5dp
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(0, dimen(R.dimen.margin_button_separate), 0, 0)
            view.layout_button_separate.layoutParams = params

        } else {
            val bottomView = inflater.inflate(R.layout.layout_alert_dialog_button, view.layout_button, false)
            setAlertButtonStyles(bottomView)
            view.layout_button.addView(bottomView)
        }
    }

    /**
     * 根据底部按钮个数以及按钮与内容是否分开设置显示样式
     */
    private fun setAlertButtonStyles(view: View) {
        val submit = arguments?.getString(Constants.SUBMIT_TEXT, "确定")
        val cancel = arguments?.getString(Constants.CANCEL_TEXT, "取消")
        val submitColor = arguments?.getInt(Constants.SUBMIT_TEXT_COLOR, config.alertTextColorSubmit)
                ?: config.alertTextColorSubmit
        val cancelColor = arguments?.getInt(Constants.CANCEL_TEXT_COLOR, config.alertTextColorCancel)
                ?: config.alertTextColorCancel
        val buttonSize = arguments?.getFloat(Constants.BUTTON_SIZE, config.alertTextSizeButton)
                ?: config.alertTextSizeButton
        val submitListener = arguments?.getSerializable(Constants.LISTENER_SUBMIT_CLICK) as? OnButtonClickedListener
        val cancelListener = arguments?.getSerializable(Constants.LISTENER_CANCEL_CLICK) as? OnButtonClickedListener
        val selectedListener = arguments?.getSerializable(Constants.LISTENER_SELECTED_CLICK) as? OnItemSelectedListener

        // 取消
        cancelListener?.let { listener ->
            cancelButtonShow = true
            view.btn_negative.text = cancel
            view.btn_negative.textSize = buttonSize
            view.btn_negative.setTextColor(cancelColor)
            view.btn_negative.setOnClickListener {
                listener.onClick(this, view.btn_negative)
                dismiss()
            }
        }

        // 确定
        if (selectedListener != null || submitListener != null) {
            submitButtonShow = true
            view.btn_positive.text = submit
            view.btn_positive.textSize = buttonSize
            view.btn_positive.setTextColor(submitColor)
            view.btn_positive.setOnClickListener {
                if (selectedListener != null) {
                    val intList = arrayListOf<Int>()
                    selectedStatedArray.forEachIndexed { index, i ->
                        if (i == 1) {
                            intList.add(index)
                        }
                    }
                    if (intList.size < selectedMinNum) {
                        toast("最少选择${selectedMinNum}个")
                        return@setOnClickListener
                    }
                    selectedListener.onSelected(this, intList)
                } else {
                    submitListener?.onClick(this, view.btn_positive)
                }
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


    class Builder(val context: Context) : BaseBuilder<AlertFragment, Builder>(context) {

        private val mFragment = AlertFragment()

        override val fragment: AlertFragment
            get() = mFragment

        fun setDialogBottomSeparate(isSeparate: Boolean): Builder {
            arg.putBoolean(Constants.DIALOG_STYLE, isSeparate)
            return this
        }

        fun setBackgroundResource(resource: Int): Builder {
            arg.putInt(Constants.BACKGROUND_RESOURCE, resource)
            return this
        }

        fun setTitle(title: String): Builder {
            arg.putString(Constants.TITLE, title)
            return this
        }

        fun setTitleColor(titleColor: Int): Builder {
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

        fun setMessageColor(messageColor: Int): Builder {
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

        fun setDataList(items: ArrayList<String>): Builder {
            arg.putBoolean(Constants.is_HAS_ITEM_LAYOUT, true)
            arg.putStringArrayList(Constants.ITEM_COMMON_LIST, items as ArrayList<String>?)
            return this
        }

        fun setDataList(items: ArrayList<String>, textColor: Int): Builder {
            arg.putBoolean(Constants.is_HAS_ITEM_LAYOUT, true)
            arg.putStringArrayList(Constants.ITEM_COLOR_LIST, items)
            arg.putInt(Constants.ITEM_TEXT_COLOR, textColor)
            return this
        }

        fun setSelectedDataList(items: ArrayList<String>): Builder {
            arg.putStringArrayList(Constants.ITEM_SELECTED_DATA_LIST, items)
            arg.putBoolean(Constants.IS_HAS_ITEM_SELECTED, true)
            return this
        }

        fun setSelectedItem(list: ArrayList<Int>): Builder {
            arg.putIntegerArrayList(Constants.ITEM_SELECTED_LIST, list)
            return this
        }

        fun setListItemTextSize(textSize: Float): Builder {
            arg.putFloat(Constants.ITEM_TEXT_SIZE, textSize)
            return this
        }

        fun setSelectedItemMax(@IntRange(from = 0) maxNum: Int): Builder {
            arg.putInt(Constants.ITEM_SELECTED_MAX, maxNum)
            return this
        }

        fun setSelectedItemMin(@IntRange(from = 0) minNum: Int): Builder {
            arg.putInt(Constants.ITEM_SELECTED_MIN, minNum)
            return this
        }

        fun setItemImageSelected(@DrawableRes imageRes: Int): Builder {
            arg.putInt(Constants.ITEM_SELECTED_IMAGE, imageRes)
            return this
        }

        fun setItemImageUnSelected(@DrawableRes imageRes: Int): Builder {
            arg.putInt(Constants.ITEM_UNSELECTED_IMAGE, imageRes)
            return this
        }

        fun setCustomLayoutRes(@LayoutRes resource: Int): Builder {
            arg.putBoolean(Constants.IS_HAS_CUSTOM_LAYOUT, true)
            arg.putInt(Constants.CUSTOM_LAYOUT, resource)
            return this
        }

        fun setCustomLayoutRes(@LayoutRes resource: Int, listener: OnCustomListener): Builder {
            arg.putBoolean(Constants.IS_HAS_CUSTOM_LAYOUT, true)
            arg.putInt(Constants.CUSTOM_LAYOUT, resource)
            arg.putParcelable(Constants.CUSTOM_LISTENER, listener)
            return this
        }

        fun setListSelectedListener(listener: OnItemSelectedListener): Builder {
            setListSelectedListener(null, listener)
            return this
        }

        fun setShowMaxItemSize(itemSize: Int): Builder {
            arg.putInt(Constants.SHOW_MAX_ITEM_SIZE, itemSize)
            return this
        }


        fun setListSelectedListener(text: String?, listener: OnItemSelectedListener): Builder {
            setListSelectedListener(text, Color.RED, listener)
            return this
        }

        fun setListSelectedListener(text: String?, textColor: Int, listener: OnItemSelectedListener): Builder {
            arg.putString(Constants.SUBMIT_TEXT, text)
            arg.putInt(Constants.SUBMIT_TEXT_COLOR, textColor)
            arg.putSerializable(Constants.LISTENER_SELECTED_CLICK, listener)
            return this
        }

        fun setSubmitListener(listener: OnButtonClickedListener): Builder {
            setSubmitListener(null, listener)
            return this
        }

        fun setSubmitListener(text: String?, listener: OnButtonClickedListener): Builder {
            setSubmitListener(text, Color.RED, listener)
            return this
        }

        fun setSubmitListener(text: String?, textColor: Int, listener: OnButtonClickedListener): Builder {
            arg.putSerializable(Constants.LISTENER_SUBMIT_CLICK, listener)
            arg.putString(Constants.SUBMIT_TEXT, text)
            arg.putInt(Constants.SUBMIT_TEXT_COLOR, textColor)
            return this
        }

        fun setCancelListener(listener: OnButtonClickedListener): Builder {
            setCancelListener(null, listener)
            return this
        }

        fun setCancelListener(text: String?, listener: OnButtonClickedListener): Builder {
            setCancelListener(text, Color.BLUE, listener)
            return this
        }

        fun setCancelListener(text: String?, textColor: Int, listener: OnButtonClickedListener): Builder {
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

    }

    interface OnItemSelectedListener : Serializable {
        fun onSelected(alert: AlertFragment, selectedList: List<Int>)
    }

    interface OnButtonClickedListener : Serializable {
        fun onClick(alert: AlertFragment, view: View?)
    }

    interface OnItemClickListener : Serializable {
        fun onItemClick(alert: AlertFragment, view: View, position: Int)
    }

    interface OnCustomListener : Parcelable {

        fun onLayout(alert: AlertFragment, view: View)

        override fun writeToParcel(dest: Parcel?, flags: Int) {
        }

        override fun describeContents(): Int {
            return 0
        }
    }
}