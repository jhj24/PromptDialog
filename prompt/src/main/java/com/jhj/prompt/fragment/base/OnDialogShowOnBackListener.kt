package com.jhj.prompt.fragment.base

import java.io.Serializable

interface OnDialogShowOnBackListener<T : BaseDialogFragment<T>> : Serializable {
    fun cancel(fragment: T)
}