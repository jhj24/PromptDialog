package com.jhj.prompt

import android.app.DialogFragment
import android.app.FragmentManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import org.jetbrains.anko.padding
import java.lang.Exception
import java.lang.IllegalStateException

/**
 * DialogFragment基类
 * Created by jhj on 2018-3-14 0014.
 */
abstract class BaseDialogFragment : DialogFragment() {

    var mGravity: Int? = null
    var mAnim: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val cancel = arguments.getBoolean(Constants.OUT_SIDE_CANCEL, true)
        dialog.setCanceledOnTouchOutside(cancel)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window.decorView.padding = 0
        dialog.window.setBackgroundDrawableResource(R.drawable.transition)
        setAttributes(dialog.window)
        return createView(inflater, container, savedInstanceState)
    }

    var attr: WindowManager.LayoutParams? = null

    private fun setAttributes(window: Window) {
        attr = window.attributes
        val dm = DisplayMetrics()
        window.windowManager.defaultDisplay.getMetrics(dm)
        val bottom = arguments.getInt(Constants.PADDING_BOTTOM, -1)
        val top = arguments.getInt(Constants.PADDING_TOP, -1)
        val horizontal = arguments.getInt(Constants.PADDING_HORIZONTAL, -1)
        val dim = arguments.getFloat(Constants.DIM_AMOUNT, -1f)
        val blackStyle = arguments.getBoolean(Constants.IS_BLACK_STYLE)
        val anim = if (arguments.getInt(Constants.ANIMATION, -1) == -1) {
            if (mAnim == null) {
                R.style.anim_dialog_bottom
            } else {
                mAnim
            }
        } else {
            arguments.getInt(Constants.ANIMATION)
        }
        val gravity = if (arguments.getInt(Constants.DIALOG_GRAVITY, -1) == -1) {
            if (mGravity == null) {
                Gravity.BOTTOM
            } else {
                mGravity
            }
        } else {
            arguments.getInt(Constants.DIALOG_GRAVITY)
        }


        attr?.let {
            //位置
            it.windowAnimations = anim ?: R.style.anim_dialog_bottom
            it.gravity = gravity ?: Gravity.BOTTOM

            if (bottom != -1 && gravity == Gravity.BOTTOM) {
                if (bottom < 0) {
                    throw IllegalArgumentException("Bottom padding cannot be less than 0")
                }
                it.y = bottom
            }

            if (top != -1 && gravity == Gravity.TOP) {
                if (top < 0) {
                    throw IllegalArgumentException("Top padding cannot be less than 0")
                }
                it.y = top
            }

            if (horizontal != -1) {
                if (horizontal < 0) {
                    throw IllegalArgumentException("Horizontal padding cannot be less than 0")
                }
                val width = dm.widthPixels - horizontal * 2
                it.width = width
                it.height = WindowManager.LayoutParams.WRAP_CONTENT
            } else {
                it.width = WindowManager.LayoutParams.WRAP_CONTENT
                it.height = WindowManager.LayoutParams.WRAP_CONTENT
            }

            //透明度
            if (dim != -1f) {
                it.dimAmount = dim
            } else {
                if (blackStyle){
                    it.dimAmount = 0f
                }else{
                    it.dimAmount = 0.3f
                }
            }

        }

        window.attributes = attr
    }


    fun show(fragmentManager: FragmentManager) {
        try {
            val ft = fragmentManager.beginTransaction()
            if (this.isAdded) {
                ft.remove(this).commit()
            }
            ft.add(this, System.currentTimeMillis().toString())
            ft.commitAllowingStateLoss()
        } catch (e: Exception) {
            /*
             * 出现情况:
             *      出现一个AlertFragment后，屏幕旋转（没有设置配置文件，Activity生命周期方法重新运行），
             * 点击确认/取消安全，想弹出另一个提示框时，出现异常
             */
            throw IllegalStateException("Activity has been destroyed")
        }
    }

    abstract fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?

}