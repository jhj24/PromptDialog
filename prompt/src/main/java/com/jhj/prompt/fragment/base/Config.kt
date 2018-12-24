package com.jhj.prompt.fragment.base

import android.graphics.Color
import android.view.Gravity
import com.jhj.prompt.R
import com.jhj.prompt.fragment.LoadingFragment
import com.jhj.prompt.fragment.options.utils.DividerType

object Config {

    const val ALERT_TEXT_SIZE_TITLE = 22f
    const val ALERT_TEXT_SIZE_ITEM_TITLE = 15f
    const val ALERT_TEXT_SIZE_BUTTON = 18f
    const val ALERT_TEXT_COLOR_TITLE = Color.BLACK
    const val ALERT_TEXT_COLOR_MSG = Color.BLACK
    const val ALERT_TEXT_COLOR_SUBMIT = Color.RED
    const val ALERT_TEXT_COLOR_CANCEL = Color.BLUE
    const val ALERT_TEXT_COLOR_ITEM = Color.BLUE
    const val ALERT_TEXT_GRAVITY_TITLE = Gravity.CENTER
    const val ALERT_TEXT_GRAVITY_MSG = Gravity.CENTER
    val ALERT_BACKGROUND_RESOURCE = R.drawable.bg_dialog_corner
    const val ALERT_TEXT_COLOR_ITEM_TITLE = Color.GRAY


    const val SCALE_SIZE = 12f
    const val MAX_PROGRESS = 100

    const val MESSAGE_TEXT_SIZE = 15f
    const val CIRCLE_WIDTH = 6f
    const val CIRCLE_RADIUS = 75
    val LOADING_STYLE: LoadingFragment.LoadingStyle = LoadingFragment.LoadingStyle.OLD_STYLE

    const val BLACK_STYLE_TEXT_COLOR = Color.WHITE
    const val BLACK_STYLE_SCALE_COLOR = Color.WHITE
    const val BLACK_STYLE_CIRCLE_COLOR = Color.WHITE
    const val BLACK_STYLE_CIRCLE_BOTTOM_COLOR = Color.GRAY
    val BLACK_STYLE_BACKGROUND = R.drawable.bg_progress_black_dialog

    const val WHITE_STYLE_TEXT_COLOR = Color.BLACK
    const val WHITE_STYLE_CIRCLE_COLOR = Color.BLACK
    const val WHITE_STYLE_SCALE_COLOR = Color.BLACK
    const val WHITE_STYLE_CIRCLE_BOTTOM_COLOR = Color.LTGRAY
    val WHITE_STYLE_BACKGROUND = R.drawable.bg_progress_white_dialog


    const val PICKER_TEXT_SIZE_TITLE = 21f
    const val PICKER_TEXT_COLOR_TITLE = Color.BLACK
    val PICKER_TEXT_COLOR_BUTTON = R.drawable.selector_pickerview_btn
    const val PICKER_TEXT_SIZE_BOTTON = 18f
    val PICKER_TOP_BAR_BACKGROUND = R.color.pickerview_bg_topbar
    val PICKER_OPTIONS_BACKGROUND = R.drawable.bg_dialog_no_corner
    const val PICKER_TEXT_SIZE_CENTER = 20f
    const val PICKER_TEXT_COLOR_CENTER = -0xd5d5d6
    const val PICKER_TEXT_COLOR_OUT = -0x575758
    val PICKER_DIVIDER_TYPE = DividerType.FILL
    const val PICKER_DIVIDER_COLOR = -0x2a2a2b
    const val PICKER_SHOW_ITEM_NUM = 11
    const val PICKER_LINE_SPACEING_RATIO = 1.6f
    const val PICKER_EXTRA_HEIGHT = 2
    const val PICKER_X_OFFSET = 0
    const val PICKER_OPTIONS_TEXT_GRAVITY = Gravity.CENTER

}