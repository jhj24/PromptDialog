package com.jhj.prompt.pop

import android.app.Activity
import android.content.Context
import android.view.*
import android.widget.PopupWindow

/**
 * 自定义弹出popWindow
 *
 * Created by jhj on 18-7-16.
 */
class PopWindow() {

    private var mContext: Context? = null
    private var popupWindow: PopupWindow? = null
    private var mWindow: Window? = null//当前Activity 的窗口

    private var width: Int = 0
    private var height: Int = 0
    private var anim = -1
    private var layoutRes = -1
    private var isFocusable = true
    private var isClipEnable = true
    private var isTouchable = true
    private var backgroundAlpha = 0f// 背景变暗的值，0 - 1
    private var canceledOnTouchOutSide = true
    private var inputMode: Int? = null
    private var softInputMode: Int? = null
    private var touchListener: View.OnTouchListener? = null
    private var dismissListener: PopupWindow.OnDismissListener? = null
    private var customListener: OnCustomListener? = null


    private constructor(context: Context) : this() {
        this.mContext = context
    }

    fun getHeight(): Int {
        return height
    }

    fun getWidth(): Int {
        return width
    }

    fun build(): PopupWindow? {
        val view = LayoutInflater.from(mContext).inflate(layoutRes, null)
                ?: throw NullPointerException("layoutRes cannot be null,please call on setCustomLayoutRes()")

        popupWindow = if (width != 0 && height != 0) {
            PopupWindow(view, width, height)
        } else {
            PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        customListener?.onLayout(view, popupWindow)

        val activity = view.context as? Activity
        val alpha = if (backgroundAlpha > 0 && backgroundAlpha < 1) backgroundAlpha else 0f
        if (activity != null && alpha != 0f) {
            mWindow = activity.window
            val params = mWindow?.attributes
            params?.alpha = alpha
            mWindow?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            mWindow?.attributes = params
        }

        popupWindow?.let {
            apply()
            if (anim != -1) {
                it.animationStyle = anim
            }

            if (width == 0 || height == 0) {
                it.contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
                height = it.contentView.measuredHeight
                width = it.contentView.measuredWidth
            }

            // 判断是否点击PopupWindow之外的地方关闭 popWindow
            if (!canceledOnTouchOutSide) {
                //注意这三个属性必须同时设置，不然不能disMiss，以下三行代码在Android 4.4 上是可以，然后在Android 6.0以上，下面的三行代码就不起作用了，就得用下面的方法
                it.isFocusable = true
                it.isOutsideTouchable = false
                it.setBackgroundDrawable(null)
                //注意下面这三个是contentView 不是PopupWindow
                it.contentView.isFocusable = true
                it.contentView.isFocusableInTouchMode = true
                it.contentView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        it.dismiss()
                        return@OnKeyListener true
                    }
                    false
                })
                //在Android 6.0以上 ，通过拦截事件来解决
                it.setTouchInterceptor(View.OnTouchListener { v, event ->
                    val x = event.x.toInt()
                    val y = event.y.toInt()

                    if (event.action == MotionEvent.ACTION_DOWN && (x < 0 || x >= width || y < 0 || y >= height)) {
                        return@OnTouchListener true
                    } else if (event.action == MotionEvent.ACTION_OUTSIDE) {
                        return@OnTouchListener true
                    }
                    false
                })
            }

        }
        popupWindow?.update()
        return popupWindow
    }


    fun showAsDropDown(view: View, xOff: Int, yOff: Int) {
        popupWindow?.showAsDropDown(view, xOff, yOff)
    }

    fun showAsDropDown(view: View) {
        popupWindow?.showAsDropDown(view)
    }

    fun setAtLocation(view: View, gravity: Int, x: Int, y: Int) {
        popupWindow?.showAtLocation(view, gravity, x, y)
    }

    private fun apply() {
        popupWindow?.let {
            if (dismissListener == null) {
                it.setOnDismissListener {
                    onDismiss()
                }
            } else {
                it.setOnDismissListener(dismissListener)
            }
            if (touchListener != null) {
                it.setTouchInterceptor(touchListener)
            }
            if (inputMode != null) {
                it.inputMethodMode = inputMode as Int
            }
            if (softInputMode != null) {
                it.softInputMode = softInputMode as Int
            }

            it.isClippingEnabled = isClipEnable
            it.isTouchable = isTouchable
            it.isFocusable = isFocusable
        }
    }

    fun onDismiss() {
        dismissListener?.onDismiss()
        //如果设置了背景变暗，那么在dismiss的时候需要还原
        if (mWindow != null) {
            val params = mWindow?.attributes
            params?.alpha = 1.0f
            mWindow?.attributes = params
        }
        if (popupWindow != null && popupWindow?.isShowing == true) {
            popupWindow?.dismiss()
        }
    }


    class Builder(mContext: Context) {

        private val popWindow = PopWindow(mContext)

        fun setPopWindowSize(height: Int, width: Int): Builder {
            popWindow.height = height
            popWindow.width = width
            return this
        }


        fun setLayoutRes(res: Int): Builder {
            popWindow.layoutRes = res
            return this
        }

        fun setLayoutRes(res: Int, listener: OnCustomListener): Builder {
            popWindow.layoutRes = res
            popWindow.customListener = listener
            return this
        }

        fun setCanceledOnTouchOutSide(cancel: Boolean): Builder {
            popWindow.canceledOnTouchOutSide = cancel
            return this
        }


        fun setAnimation(anim: Int): Builder {
            popWindow.anim = anim
            return this
        }

        /**
         * true - 点击返回键弹出的popupWindow消失
         * false - 点击返回键，弹出popupWindow的界面关闭
         */
        fun setFocusable(isFocusable: Boolean): Builder {
            popWindow.isFocusable = isFocusable
            return this
        }

        /**
         * 设置背景透明度
         */
        fun setBackgroundAlpha(alpha: Float): Builder {
            popWindow.backgroundAlpha = alpha
            return this
        }

        /**
         * 默认情况下，窗口被剪切到屏幕边界,当设置为false时，弹出的popupWindow可以超出屏幕
         */
        fun setClippingEnable(isClipEnable: Boolean): Builder {
            popWindow.isClipEnable = isClipEnable
            return this
        }

        /**
         * 当可触摸时，窗口将接收触摸事件，否则触摸事件将进入其下方的窗口。
         */
        fun setTouchable(isTouchable: Boolean): Builder {
            popWindow.isTouchable = isTouchable
            return this
        }

        /**
         * 设置输入模式(允许，不允许，根据焦点判断是否可输入)
         *
         */
        fun setInputMethodMode(mode: Int): Builder {
            popWindow.inputMode = mode
            return this
        }

        /**
         * 设置软键盘模式
         */
        fun setSoftInputMode(softInputMode: Int): Builder {
            popWindow.softInputMode = softInputMode
            return this
        }


        /**
         * popupWindow消失事件监听
         */
        fun setOnDismissListener(dismissListener: PopupWindow.OnDismissListener): Builder {
            popWindow.dismissListener = dismissListener
            return this
        }


        /**
         * popupWindow触摸事件监听
         */
        fun setOnTouchListener(touchListener: View.OnTouchListener): Builder {
            popWindow.touchListener = touchListener
            return this
        }

        fun build(): PopWindow {
            popWindow.build()
            return popWindow
        }
    }


    interface OnCustomListener {
        fun onLayout(view: View, popupWindow: PopupWindow?)
    }
}