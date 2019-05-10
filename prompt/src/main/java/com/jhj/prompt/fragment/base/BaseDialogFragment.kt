package com.jhj.prompt.fragment.base

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.util.DisplayMetrics
import android.view.*
import com.jhj.prompt.R
import com.jhj.prompt.fragment.AlertFragment
import org.jetbrains.anko.padding

/**
 * DialogFragment基类
 * Created by jhj on 2018-3-14 0014.
 */
abstract class BaseDialogFragment<T : BaseDialogFragment<T>> : DialogFragment() {

    var mGravity: Int? = null
    private var cancelOut = true
    private var dialogHeight = -1
    private var marginTop = -1
    private var marginBottom = -1
    private var marginHorizontal = -1
    private var dim = 0.3f
    private var isBlackStyle = false
    private var anim = -1
    private var gravity = -1
    private var isTouchWindow = false
    private var backListener: OnDialogShowOnBackListener<T>? = null

    lateinit var inflater: LayoutInflater
    lateinit var config: PromptConfig
    lateinit var mActivity: Activity
    abstract val layoutRes: Int


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_style)
        config = PromptConfig.getInstance()
        if (savedInstanceState == null) {
            initParams(arguments)
        } else {
            initParams(savedInstanceState)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.inflater = inflater
        dialog.setCanceledOnTouchOutside(cancelOut)
        //去掉dialog默认样式中的Title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //去掉默认边距
        dialog.window?.decorView?.padding = 0
        //设置dialog背景透明
        dialog.window?.setBackgroundDrawableResource(R.drawable.transition)
        dialog.window?.let { setAttributes(it) }
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.let {
            it.putInt(Constants.DIALOG_HEIGHT, dialogHeight)
            it.putInt(Constants.MARGIN_TOP, marginTop)
            it.putInt(Constants.MARGIN_BOTTOM, marginBottom)
            it.putInt(Constants.MARGIN_HORIZONTAL, marginHorizontal)
            it.putFloat(Constants.DIM_AMOUNT, dim)
            it.putBoolean(Constants.IS_BLACK_STYLE, isBlackStyle)
            it.putInt(Constants.ANIMATION, anim)
            it.putInt(Constants.DIALOG_GRAVITY, gravity)
            it.putBoolean(Constants.OUT_SIDE_CANCEL, cancelOut)
            it.putSerializable(Constants.DIALOG_ON_BACK_LISTENER, backListener)
        }


    }

    open fun initParams(bundle: Bundle?) {
        bundle?.let {
            dialogHeight = it.getInt(Constants.DIALOG_HEIGHT, -1)
            marginTop = it.getInt(Constants.MARGIN_TOP, -1)
            marginBottom = it.getInt(Constants.MARGIN_BOTTOM, -1)
            marginHorizontal = it.getInt(Constants.MARGIN_HORIZONTAL, -1)
            dim = it.getFloat(Constants.DIM_AMOUNT, -1f)
            isBlackStyle = it.getBoolean(Constants.IS_BLACK_STYLE)
            anim = it.getInt(Constants.ANIMATION, -1)
            gravity = it.getInt(Constants.DIALOG_GRAVITY, -1)
            cancelOut = it.getBoolean(Constants.OUT_SIDE_CANCEL, true)
            backListener = it.getSerializable(Constants.DIALOG_ON_BACK_LISTENER) as? OnDialogShowOnBackListener<T>
        }
    }


    private fun setAttributes(window: Window) {
        val attr = window.attributes
        val dm = DisplayMetrics()
        window.windowManager.defaultDisplay.getMetrics(dm)
        val density = mActivity.resources.displayMetrics.density

        val gravity = if (gravity == -1) {
            if (mGravity == null) {
                Gravity.CENTER
            } else {
                mGravity
            }
        } else {
            gravity
        }


        //是否有自定义动画，没有根据 Gravity 设置动画
        val anim = when {
            anim != -1 -> anim
            gravity == Gravity.TOP -> R.style.anim_dialog_top
            gravity == Gravity.BOTTOM -> R.style.anim_dialog_bottom
            else -> R.style.anim_dialog_center
        }


        attr?.let {
            // 位置
            it.windowAnimations = anim
            it.gravity = gravity ?: Gravity.CENTER

            // Y 轴偏移量
            if (gravity == Gravity.BOTTOM && marginBottom != -1) {  //dialog 在底部时设置Margin Bottom 才起作用
                it.y = marginBottom
            } else if (gravity == Gravity.TOP && marginTop != -1) {  //dialog 在顶部时设置Margin Top 才起作用
                it.y = marginTop
            } else if (this is AlertFragment && gravity == Gravity.BOTTOM && marginBottom == -1) { //dialog 是AlertFragment，并且在底部，则默认5dp的偏移
                it.y = (density * 5).toInt()
            }

            // 设置 Dialog 宽度
            it.width = if (marginHorizontal != -1) { //根据指定水平 Margin 设置 Dialog 宽度
                dm.widthPixels - marginHorizontal * 2
            } else {
                if (this is AlertFragment) { // AlertFragment 根据样式不同显示不同的宽度
                    if (gravity == Gravity.CENTER) {
                        dm.widthPixels - (density * 80).toInt()
                    } else {
                        dm.widthPixels - (density * 30).toInt()
                    }
                } else { //默认 Dialog 宽度
                    WindowManager.LayoutParams.WRAP_CONTENT
                }
            }


            //设置 Dialog 高度
            it.height = if (dialogHeight == -1) {
                WindowManager.LayoutParams.WRAP_CONTENT
            } else {
                dialogHeight
            }

            //透明度
            when {
                dim != -1f -> //自定义了透明度
                    it.dimAmount = dim
                isBlackStyle -> //设置了黑色样式时，完全透明
                    it.dimAmount = 0f
                else -> //默认透明度
                    it.dimAmount = 0.3f
            }

            window.attributes = it
        }

        //是否点击了界面
        window.decorView.setOnTouchListener { v, event ->
            isTouchWindow = true
            false
        }

    }


    fun show(fragmentManager: FragmentManager) {
        try {
            val ft = fragmentManager.beginTransaction()
            if (this.isAdded) {
                ft.remove(this).commit()
            }
            ft.add(this, System.currentTimeMillis().toString())
            ft.commitAllowingStateLoss()
            //fragmentManager.executePendingTransactions()
        } catch (e: Exception) {
            /*
             * 出现情况:
             *      出现一个AlertFragment后，屏幕旋转（没有设置配置文件，Activity生命周期方法重新运行），
             * 点击确认/取消安全，想弹出另一个提示框时，出现异常
             */
            throw IllegalStateException("Activity has been destroyed")
        }
    }

    fun isShow(): Boolean? {
        return dialog?.isShowing
    }


    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        if (!isTouchWindow) {
            backListener?.cancel(this as T)
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        if (!mActivity.isFinishing) {
            super.onDismiss(dialog)
        }
    }

    override fun dismiss() {
        if (!mActivity.isFinishing) {
            super.dismiss()
        }
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }
}

