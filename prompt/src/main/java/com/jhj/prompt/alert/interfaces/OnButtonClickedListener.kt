package com.jhj.prompt.alert.interfaces

import android.view.View
import java.io.Serializable

/**
 * 按钮点击接口
 * Created by jhj on 2018-3-1 0001;
 */
interface OnButtonClickedListener : Serializable {

    fun onClick(view: View?)

}