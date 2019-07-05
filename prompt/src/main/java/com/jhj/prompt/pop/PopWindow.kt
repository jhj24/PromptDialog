package com.jhj.prompt.pop

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Handler
import android.util.DisplayMetrics
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.PopupWindow

/**
 * 自定义PopWindow
 * Created by jhj on 19-1-19.
 */
class PopWindow(private val mContext: Context) : PopupWindow() {

    private var isDismiss = false
    private var view: View? = null

    private var layoutRes: Int? = null
    private var animIn: Int? = null
    private var animOut: Int? = null
    private var dimAmount = 0.7f// 背景变暗的值，0 - 1
    private var mWindow: Window? = null//当前Activity 的窗口
    private var body: (View, PopWindow) -> Unit = { v, popWindow -> }

    private fun build() {
        val localDisplayMetrics = DisplayMetrics()
        (mContext as Activity).windowManager.defaultDisplay.getMetrics(localDisplayMetrics)
        if (layoutRes == null) {
            throw NullPointerException("layoutRes cannot null")
        }

        view = LayoutInflater.from(mContext).inflate(layoutRes!!, null)
        contentView = view
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT

        val activity = view?.context as? Activity
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
        view?.setOnClickListener {
            dismiss()
        }
        mWindow?.decorView?.setOnClickListener {
            dismiss()
        }
        body(view as View, this)
    }

    override fun showAsDropDown(anchor: View, xoff: Int, yoff: Int) {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                val rect = Rect()
                anchor.getGlobalVisibleRect(rect)
                val h = anchor.resources.displayMetrics.heightPixels - rect.bottom
                height = h
            }
            super.showAsDropDown(anchor, xoff, yoff)
            isDismiss = false
            if (animIn != null) {
                val animationIn = AnimationUtils.loadAnimation(mContext, animIn!!)
                view?.startAnimation(animationIn)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun showAsDropDown(anchor: View) {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                val rect = Rect()
                anchor.getGlobalVisibleRect(rect)
                val h = anchor.resources.displayMetrics.heightPixels - rect.bottom
                height = h
            }
            super.showAsDropDown(anchor)
            isDismiss = false
            if (animIn != null) {
                val animationIn = AnimationUtils.loadAnimation(mContext, animIn!!)
                view?.startAnimation(animationIn)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun dismiss() {
        if (animOut != null) {
            if (isDismiss) {
                return
            }
            isDismiss = true
            val animationOut = AnimationUtils.loadAnimation(mContext, animOut!!)
            view?.startAnimation(animationOut)
            dismiss()
            if (mWindow != null) {
                val params = mWindow?.attributes
                params?.alpha = 1.0f
                mWindow?.attributes = params
            }
            animationOut.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {
                    isDismiss = false
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                        Handler().post { super@PopWindow.dismiss() }
                    } else {
                        super@PopWindow.dismiss()
                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        } else {
            super@PopWindow.dismiss()
        }
    }

    class Builder(mContext: Context) {

        private val popWindow = PopWindow(mContext)

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
            popWindow.animIn = anim
            return this
        }

        fun setCloseAnim(anim: Int): Builder {
            popWindow.animOut = anim
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