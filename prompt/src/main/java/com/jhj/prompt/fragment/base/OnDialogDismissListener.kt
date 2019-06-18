package com.jhj.prompt.fragment.base

import java.io.Serializable

interface OnDialogDismissListener : Serializable {
    fun callback(isDismiss: Boolean)
}