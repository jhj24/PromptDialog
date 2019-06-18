package com.jhj.prompt.fragment.base

/**
 * 常量
 * Created by jhj on 2018-3-14 0014.
 */
object Constants {

    //============dialog属性==============
    const val DIM_AMOUNT = "dim_amount" // 设置透明度
    const val ANIMATION = "animation" //设置动画
    const val DIALOG_GRAVITY = "dialog_gravity" //设置位置
    const val OUT_SIDE_CANCEL = "out_side_cancel" // 点击dialog外部是否消失
    const val MARGIN_HORIZONTAL = "margin_horizontal" //水平边距
    const val MARGIN_BOTTOM = "margin_bottom" //底部边距
    const val MARGIN_TOP = "margin_top" //头部边距
    const val DIALOG_ON_BACK_LISTENER: String = "dialog_cancel_listener" //dialog弹出后点击返回按钮监听
    const val DIALOG_ON_DISMISS_LISTENER: String = "dialog_cancel_listener" //dialog弹出后点击返回按钮监听

    //标题
    const val TITLE = "title"
    const val TITLE_COLOR: String = "title_color"
    const val TITLE_SIZE: String = "title_size"
    const val TITLE_GRAVITY = "title_gravity"

    //提示信息（alertFragment）
    const val MESSAGE = "message"
    const val MESSAGE_COLOR = "message_color"
    const val MESSAGE_SIZE = "message_size"
    const val MESSAGE_GRAVITY = "message_gravity"

    //按钮
    const val LISTENER_SUBMIT_CLICK = "listener_submit_click"
    const val LISTENER_CANCEL_CLICK = "listener_cancel_click"
    const val SUBMIT_TEXT = "submit_text"
    const val SUBMIT_TEXT_COLOR = "submit_text_color"
    const val CANCEL_TEXT = "cancel_text"
    const val CANCEL_TEXT_COLOR = "cancel_text_color"
    const val BUTTON_SIZE: String = "button_size"
    const val LISTENER_SELECTED_CLICK = "listener_selected_click"

    //自定义布局
    const val IS_HAS_CUSTOM_LAYOUT = "is_has_custom_layout"
    const val CUSTOM_LAYOUT = "custom_layout"
    const val CUSTOM_LISTENER = "custom_listener"

    //列表数据
    const val is_HAS_ITEM_LAYOUT = "is_has_item_layout"
    const val IS_HAS_ITEM_SELECTED = "is_has_item_selected"
    const val ITEM_COMMON_LIST = "item_common_list"
    const val ITEM_COLOR_LIST = "item_color_list"
    const val ITEM_SELECTED_LIST = "item_selected_list"
    const val ITEM_SELECTED_DATA_LIST = "item_selected_data_list"
    const val ITEM_SELECTED_MAX = "item_selected_max"
    const val ITEM_SELECTED_MIN = "item_selected_min"
    const val ITEM_TEXT_COLOR = "item_text_color"
    const val ITEM_TEXT_SIZE = "item_text_size"
    const val ITEM_SELECTED_IMAGE = "item_selected_image"
    const val ITEM_UNSELECTED_IMAGE = "item_unselected_image"
    const val LISTENER_ITEM_CLICK = "listener_item_click"
    const val SHOW_MAX_ITEM_SIZE = "list_item_size"
    const val IS_SET_MAX_ITEM_SIZE = "is_set_max_item_size"

    //alertFragment样式
    const val DIALOG_STYLE = "dialog_style"

    //activity销毁
    const val ACTIVITY_DESTROY = "activity_destroy"


    //圆的属性（LoadingFragment）
    const val CIRCLE_WIDTH = "circle_width"
    const val CIRCLE_RADIUS = "circle_radius"
    const val CIRCLE_COLOR = "circle_color"
    const val CIRCLE_BOTTOM_COLOR = "circle_bottom_color"

    //百分比属性（PercentFragment)
    const val SCALE_DISPLAY: String = "scale_display"
    const val SCALE_COLOR: String = "scale_color"
    const val SCALE_SIZE: String = "scale_size"
    const val MAX_PROGRESS: String = "max_progress"
    const val IS_BLACK_STYLE: String = "is_black_style"
    const val BACKGROUND_RESOURCE: String = "background_resource"


    //联动
    const val TOPBAR_BACKGROUND_RESOURCE: String = "topBar_background_resource"
    const val OPTIONS_BACKGROUND_RESOURCE: String = "options_background_resource" //options背景色
    const val OPTIONS_TEXT_SIZE: String = "options_text_size" //options 的字体大小
    const val ONLY_CENTER_LABEL: String = "only_center_label_display" //只显示中间itme的单位
    const val DIVIDER_COLOR: String = "divider_color" //分割线颜色
    const val TEXT_COLOR_OUT: String = "text_color_out" //中间字体颜色
    const val TEXT_COLOR_CENTER: String = "text_color_center" //两边字体颜色
    const val DIVIDER_TYPE: String = "divider_type" //分割线类型
    const val ITEM_NUM: String = "item_num" // item个数
    const val OPTIONS_LABELS: String = "options_labels" //item单位
    const val IS_CYCLIC: String = "is_cyclic" //是否循环
    const val DISPLAY_STYLE: String = "display_style"//时间显示类型，6个boolean值对应年月日时分秒
    const val X_OFFSET: String = "x_offset" //x轴的偏移量
    const val SPACING_RATIO: String = "spacing_ratio" //各个item的间距
    const val EXTRA_HEIGHT: String = "extra_height" //设置item的额外高度
    const val DATE_MILLS: String = "date_mills" //设置时间
    const val START_DATE_MILLS: String = "start_date_mills"//起始时间
    const val END_DATE_MILLS: String = "end_date_mills"//结束时间
    const val LUNAR_CALENDAR: String = "lunar_calendar"//是否是农历

    const val OPTIONS_SELECT_ONE: String = "options_select_1"
    const val OPTIONS_SELECT_TWO: String = "options_select_2"
    const val OPTIONS_SELECT_THREE: String = "options_select_3"

    const val OPTIONS_ITEMS_ONE: String = "options_items_1"
    const val OPTIONS_ITEMS_TWO: String = "options_items_2"
    const val OPTIONS_ITEMS_THREE: String = "options_items_3"

    const val OPTIONS_LINKED_ITEMS_ONE: String = "options_linked_items_1"
    const val OPTIONS_LINKED_ITEMS_TWO: String = "options_linked_items_2"
    const val OPTIONS_LINKED_ITEMS_THREE: String = "options_linked_items_3"
    const val OPTIONS_IS_LINKED: String = "options_is_linked"

}