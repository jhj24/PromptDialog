package com.jhj.prompt.alert.constants

import android.view.Gravity
import java.io.Serializable

/**
 * Dialog位置
 * Created by jhj on 2018-3-1 0001;
 */
enum class TextGravity(val value: Int) : Serializable {

    CENTER(Gravity.CENTER),
    RIGHT(Gravity.RIGHT),
    LEFT(Gravity.LEFT)
}