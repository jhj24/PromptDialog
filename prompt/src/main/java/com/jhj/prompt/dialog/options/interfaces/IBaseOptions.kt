package com.jhj.prompt.dialog.options.interfaces

import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import com.jhj.prompt.dialog.options.utils.DividerType
import com.jhj.prompt.listener.OnDialogShowOnBackListener

/**
 * 选择器的基类方法
 * Created by jhj on 2018-3-12 0012.
 */
interface IBaseOptions<T : IBaseOptions<T>> {

    fun setTitle(title: String): T
    fun setTitleColor(color: Int): T
    fun setTitleSize(size: Float): T

    fun setCancel(cancel: String): T
    fun setSubmit(submit: String): T
    fun setCancelColor(@DrawableRes color: Int): T
    fun setSubmitColor(@DrawableRes color: Int): T
    fun setButtonSize(size: Float): T
    fun setTopBarBackgroundResource(@DrawableRes resource: Int): T

    fun setOptionsBackgroundResource(@DrawableRes resource: Int): T

    fun setOptionsSize(size: Float): T
    fun setTextColorOut(color: Int): T//分割线以外的文字颜色
    fun setTextColorCenter(color: Int): T//分割线之间的文字颜色

    fun setDividerColor(@ColorInt color: Int): T//分割线的颜色
    fun setDividerType(type: DividerType): T//分隔线类型
    fun setOnlyCenterLabel(centerLabel: Boolean): T //是否只显示中间的单位
    fun setItemsSpacingRatio(spacingRatio: Float): T   // 设置间距倍数默认1.6,但是只能在1.2-2.0f之间
    fun setExtraHeight(height: Int): T //设置字体默认height情况下增加的高度
    fun setTextGravity(gravity: Int): T //字体显示位置
    fun setItemNum(num: Int): T //显示课件item个数

    fun setCyclic(cyclic: Boolean): T
    fun setTextXOffset(offset: Int): T//偏移量
    fun setGravity(gravity: Int): T
    fun setPaddingTop(top: Int): T
    fun setPaddingBottom(bottom: Int): T
    fun setPaddingHorizontal(padding: Int): T
    fun setDimAmount(dimAmount: Float): T
    fun setAnimation(resource: Int): T
    fun setOutSideCancelable(cancelable: Boolean): T
    fun setDialogShowOnBackListener(listener: OnDialogShowOnBackListener): T//dialog弹出时点击返回键进行的操作

    fun isShow(): Boolean
    fun show(): T
    fun dismiss()
}