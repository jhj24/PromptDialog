package com.jhj.prompt

import java.io.Serializable

/**
 * dialog 弹出时，按返回键操作
 * Created by jhj on 2018-3-20 0020.
 */
interface OnDialogCancelListener : Serializable {
    fun cancel()
}