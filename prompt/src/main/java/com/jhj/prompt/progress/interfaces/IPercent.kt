package com.jhj.prompt.progress.interfaces

/**
 * 带百分比的加载进度
 * Created by jhj on 2018-3-4 0004.
 */
interface IPercent<T : IPercent<T>> {

    /**
     * 中间进度百分比是否显示
     */
    fun setScaleDisplay(): T

    /**
     * 中间进度百分比颜色
     */
    fun setScaleColor(scaleColor: Int): T

    /**
     * 中间百分比字体大小
     */
    fun setScaleSize(scaleSize: Float): T

    /**
     * 设置进度
     */
    fun setProgress(progress: Int): T

    /**
     * 获取最大进度
     */
    fun getMaxProgress(): Int

    /**
     * 设置最大进度
     */
    fun setMaxProgress(maxProgress: Int): T

}