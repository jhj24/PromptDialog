package com.jhj.prompt.fragment.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.FloatRange
import android.support.annotation.IntRange
import android.support.annotation.StyleRes
import android.support.v4.app.FragmentActivity

abstract class BaseBuilder<F : BaseDialogFragment<F>, T : BaseBuilder<F, T>>() {

    val arg = Bundle()


    @Suppress("UNCHECKED_CAST")
    fun setDialogGravity(gravity: Int): T {
        arg.putInt(Constants.DIALOG_GRAVITY, gravity)
        return this as T
    }

    @Suppress("UNCHECKED_CAST")
    fun setMarginHorizontal(@IntRange(from = 0) padding: Int): T {
        arg.putInt(Constants.MARGIN_HORIZONTAL, padding)
        return this as T
    }

    @Suppress("UNCHECKED_CAST")
    fun setMarginBottom(@IntRange(from = 0) bottom: Int): T {
        arg.putInt(Constants.MARGIN_BOTTOM, bottom)
        return this as T
    }

    @Suppress("UNCHECKED_CAST")
    fun setMarginTop(@IntRange(from = 0) top: Int): T {
        arg.putInt(Constants.MARGIN_TOP, top)
        return this as T
    }

    @Suppress("UNCHECKED_CAST")
    fun setCancelOnTouchOut(isCancel: Boolean): T {
        arg.putBoolean(Constants.OUT_SIDE_CANCEL, isCancel)
        return this as T
    }


    @Suppress("UNCHECKED_CAST")
    fun setDimAmount(@FloatRange(from = 0.0, to = 1.0) dimAmount: Float): T {
        arg.putFloat(Constants.DIM_AMOUNT, dimAmount)
        return this as T
    }

    @Suppress("UNCHECKED_CAST")
    fun setAnimation(@StyleRes resource: Int): T {
        arg.putInt(Constants.ANIMATION, resource)
        return this as T
    }

    @Suppress("UNCHECKED_CAST")
    fun setDialogShowOnBackListener(listener: OnDialogShowOnBackListener<F>): T {
        arg.putSerializable(Constants.DIALOG_ON_BACK_LISTENER, listener)
        return this as T
    }

    @Suppress("UNCHECKED_CAST")
    fun setOnDialogDismissListener(listener: OnDialogDismissListener): T {
        arg.putSerializable(Constants.DIALOG_ON_DISMISS_LISTENER, listener)
        return this as T
    }

    abstract fun build():F

}