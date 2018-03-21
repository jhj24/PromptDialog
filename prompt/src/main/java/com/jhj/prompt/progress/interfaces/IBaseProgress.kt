package com.jhj.prompt.progress.interfaces

import com.jhj.prompt.OnDialogCancelListener

/**
 * Created by jhj on 2018-3-2 0002;
 */
interface IBaseProgress<T : IBaseProgress<T>> {

    /**
     * 设置提示文字
     */
    fun setText(text: String?): T

    /**
     * 设置提示文字颜色
     */
    fun setTextColor(textColor: Int): T

    /**
     * 设置提示文字大小
     */
    fun setTextSize(textSize: Float): T

    /**
     * 设置圆环半径
     */
    fun setCircleRadius(radius: Int): T

    /**
     * 设置圆环宽度
     */
    fun setCircleWidth(circleWidth: Float): T

    /**
     * 设置圆环颜色
     */
    fun setCircleColor(circleColor: Int): T

    /**
     * 设置底层圆环颜色
     */
    fun setBottomCircleColor(bottomCircleColor: Int): T

    /**
     * 设置dialog背景色
     */
    fun setBackgroundResource(resource: Int): T

    /**
     * 设置非dialog部分透明度
     */
    fun setDimAmount(dimAmount: Float): T

    /**
     * 设置dialog进出动画
     */
    fun setAnimResource(resource: Int): T

    /**
     * 设置使用黑色主题风格
     */
    fun setBlackStyle(): T

    /**
     * dialog弹出按返回键需要的操作
     */
    fun setOnDialogCancelListener(listener: OnDialogCancelListener): T

    fun setOutSideCancel(cancel: Boolean): T

    fun show(): T

    fun dismiss()


}