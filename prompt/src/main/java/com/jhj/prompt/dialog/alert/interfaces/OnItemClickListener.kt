package com.jhj.prompt.dialog.alert.interfaces

import android.view.View
import java.io.Serializable

/**
 * 单击item时监听器
 * Created by jhj on 17-10-12.
 */
interface OnItemClickListener : Serializable {
    fun onItemClick(view: View, position: Int)
}