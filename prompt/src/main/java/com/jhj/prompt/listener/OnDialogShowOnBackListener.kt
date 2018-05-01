package com.jhj.prompt.listener

import java.io.Serializable

/**
 * dialog 弹出时，按返回键操作
 * Created by jhj on 2018-3-20 0020.
 */
interface OnDialogShowOnBackListener : Serializable {
    fun cancel()
}