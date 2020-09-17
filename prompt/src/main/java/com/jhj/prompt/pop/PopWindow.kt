package com.jhj.prompt.pop

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.PopupWindow

/**
 * 自定义PopWindow
 * Created by jhj on 19-1-19.
 */
class PopWindow(private val mContext: Context) : PopupWindow() {

    private var view: View? = null

    private var layoutRes: Int? = null
    private var animEnter: Int? = null
    private var animExit: Int? = null
    private var dimAmount = 0.7f// 背景变暗的值，0 - 1
    private var mWindow: Window? = null//当前Activity 的窗口
    private var body: (View, PopWindow) -> Unit = { v, popWindow -> }
    private var popWindowWidth = 0
    private var popWindowHeight = 0
    private var isDefineWidth = false
    private var isDefineHeight = false

    fun getPopWindowWidth(): Int {
        return popWindowWidth
    }

    fun getPopWindowHeight(): Int {
        return popWindowHeight
    }


    private fun build() {
        val localDisplayMetrics = DisplayMetrics()
        (mContext as Activity).windowManager.defaultDisplay.getMetrics(localDisplayMetrics)
        if (view == null && layoutRes == null) {
            throw NullPointerException()
        }

        contentView = if (view != null) view else LayoutInflater.from(mContext).inflate(layoutRes!!, null)
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        width = if (isDefineWidth)
            popWindowWidth
        else {
            popWindowWidth = contentView.measuredWidth
            contentView.measuredWidth
        }
        height = if (isDefineHeight) {
            popWindowHeight
        } else {
            popWindowHeight = contentView.measuredHeight
            contentView.measuredHeight
        }
        val activity = contentView.context as? Activity
        val alpha = if (dimAmount > 0 && dimAmount < 1) dimAmount else 0f
        if (activity != null && alpha != 0f) {
            mWindow = activity.window
            val params = mWindow?.attributes
            params?.alpha = alpha
            mWindow?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            mWindow?.attributes = params
        }
        isFocusable = true
        isOutsideTouchable = true
        update()
        setBackgroundDrawable(ColorDrawable(Color.argb(0, 0, 0, 0)))
        contentView?.setOnClickListener {
            dismiss()
        }
        mWindow?.decorView?.setOnClickListener {
            dismiss()
        }
        body(contentView, this)
    }

    override fun showAsDropDown(anchor: View, xoff: Int, yoff: Int) {
        try {
            val rect = Rect()
            anchor.getGlobalVisibleRect(rect)
            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            width = if (isDefineWidth) popWindowWidth else contentView.measuredWidth
            height = if (isDefineHeight) popWindowHeight else contentView.measuredHeight
            super.showAsDropDown(anchor, xoff, yoff)
            if (animEnter != null) {
                val animationIn = AnimationUtils.loadAnimation(mContext, animEnter!!)
                contentView?.startAnimation(animationIn)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun showAsDropDown(anchor: View) {
        try {
            val rect = Rect()
            anchor.getGlobalVisibleRect(rect)
            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            width = if (isDefineWidth) popWindowWidth else contentView.measuredWidth
            height = if (isDefineHeight) popWindowHeight else contentView.measuredHeight
            super.showAsDropDown(anchor)
            if (animEnter != null) {
                val animationIn = AnimationUtils.loadAnimation(mContext, animEnter!!)
                contentView?.startAnimation(animationIn)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun dismiss() {
        if (mWindow != null) {
            val params = mWindow?.attributes
            params?.alpha = 1.0f
            mWindow?.attributes = params
        }
        super@PopWindow.dismiss()
        if (animExit != null) {
            contentView?.clearAnimation()
            val animationOut = AnimationUtils.loadAnimation(mContext, animExit!!)
            contentView?.startAnimation(animationOut)
        }
    }

    class Builder(mContext: Context) {

        private val popWindow = PopWindow(mContext)

        fun setView(view: View, body: (View, PopWindow) -> Unit = { v, popWindow -> }): Builder {
            popWindow.view = view
            popWindow.body = body
            return this
        }

        fun setLayoutRes(res: Int): Builder {
            popWindow.layoutRes = res
            return this
        }

        fun setLayoutRes(res: Int, body: (View, PopWindow) -> Unit): Builder {
            popWindow.layoutRes = res
            popWindow.body = body
            return this
        }


        fun setOpenAnim(anim: Int): Builder {
            popWindow.animEnter = anim
            return this
        }

        fun setCloseAnim(anim: Int): Builder {
            popWindow.animExit = anim
            return this
        }

        fun setPopWindowWidth(width: Int): Builder {
            popWindow.isDefineWidth = true
            popWindow.popWindowWidth = width
            return this
        }

        fun setPopWindowHeight(height: Int): Builder {
            popWindow.isDefineHeight = true
            popWindow.popWindowHeight = height
            return this
        }

        /**
         * 设置背景透明度
         */
        fun setBackgroundAlpha(alpha: Float): Builder {
            popWindow.dimAmount = alpha
            return this
        }


        fun build(): PopWindow {
            popWindow.build()
            return popWindow
        }
    }

}