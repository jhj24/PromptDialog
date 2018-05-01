package com.jhj.prompt.dialog.progress

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jhj.prompt.base.BaseDialogFragment
import com.jhj.prompt.base.Constants
import com.jhj.prompt.listener.OnDialogShowOnBackListener
import com.jhj.prompt.R
import com.jhj.prompt.dialog.progress.interfaces.IBaseProgress
import com.jhj.prompt.dialog.progress.interfaces.IPercent
import kotlinx.android.synthetic.main.layout_progress_view.view.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.textColor
import java.lang.IllegalStateException

/**
 * 处于加载中的dialogFragment
 * Created by jhj on 2018-3-15 0015.
 */
class PercentFragment : BaseDialogFragment() {

    companion object {
        const val MESSAGE_TEXT_SIZE = 15f
    }

    private var mView: View? = null
    private var isCancel = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGravity = Gravity.CENTER
        mAnim = R.style.anim_dialog_center

        savedInstanceState?.let {
            if (it.getBoolean(Constants.ACTIVITY_DESTROY)) {
                dismiss()
            }
        }
    }

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.layout_progress_view, container)
        initView(mView as View)
        return mView
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(Constants.ACTIVITY_DESTROY, true)
    }


    private fun setProgress(i: Int) {
        if (mView == null) {
            throw IllegalStateException("This setProgress() method must be called after show()")
        }
        if (!isCancel) {
            mView?.circle_progress?.progress = i
            Log.w("xxx", i.toString())
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        isCancel = true
    }

    private fun initView(view: View) {

        val circleView = view.circle_progress
        circleView.visibility = View.VISIBLE

        val density = activity.resources.displayMetrics.density
        val text = arguments.getString(Constants.MESSAGE)
        val textSize = arguments.getFloat(Constants.MESSAGE_SIZE, MESSAGE_TEXT_SIZE)
        val circleRadius = arguments.getInt(Constants.CIRCLE_RADIUS, -1)
        val circleColor = arguments.getInt(Constants.CIRCLE_COLOR, -1)
        val bottomCircleColor = arguments.getInt(Constants.CIRCLE_BOTTOM_COLOR, -1)
        val textColor = arguments.getInt(Constants.MESSAGE_COLOR, -1)
        val isBlackStyle = arguments.getBoolean(Constants.IS_BLACK_STYLE)
        val backgroundResource = arguments.getInt(Constants.BACKGROUND_RESOURCE, -1)
        val scaleColor = arguments.getInt(Constants.SCALE_COLOR, -1)

        text?.let {
            view.tv_msg.visibility = View.VISIBLE
            view.tv_msg.text = text
            view.tv_msg.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        }

        //圆环半径
        val radius = if (circleRadius == -1) {
            25 * density.toInt()
        } else {
            circleRadius
        }
        val params = LinearLayout.LayoutParams(radius * 2, radius * 2)
        circleView.layoutParams = params

        circleView.showScale = arguments.getBoolean(Constants.SCALE_DISPLAY, true)
        circleView.maxProgress = arguments.getInt(Constants.MAX_PROGRESS, 100)
        circleView.circleWidth = arguments.getFloat(Constants.CIRCLE_WIDTH, 2 * density)
        circleView.scaleSize = arguments.getFloat(Constants.SCALE_SIZE, 12 * density)


        //黑色主题
        if (isBlackStyle) {
            circleView.circleColor = if (circleColor != -1) circleColor else Color.WHITE
            circleView.scaleColor = if (scaleColor != -1) scaleColor else Color.WHITE
            circleView.bottomCircleColor = if (bottomCircleColor != -1) bottomCircleColor else Color.GRAY
            view.layout_progress_dialog.backgroundResource = if (backgroundResource != -1) backgroundResource else R.drawable.bg_progress_black_dialog
            view.tv_msg.textColor = if (textColor != -1) textColor else Color.WHITE
        } else {
            circleView.circleColor = if (circleColor != -1) circleColor else Color.BLACK
            circleView.scaleColor = if (scaleColor != -1) scaleColor else Color.BLACK
            circleView.bottomCircleColor = if (bottomCircleColor != -1) bottomCircleColor else Color.LTGRAY
            view.layout_progress_dialog.backgroundResource = if (backgroundResource != -1) backgroundResource else R.drawable.bg_progress_dialog
            view.tv_msg.textColor = if (textColor != -1) textColor else Color.BLACK
        }
    }

    class Builder(val mContext: Context) :
            IPercent<Builder>,
            IBaseProgress<Builder> {


        private val arg = Bundle()
        private val fragment = PercentFragment()
        private var maxProgress = 100

        override fun setText(text: String?): Builder {
            arg.putString(Constants.MESSAGE, text)
            return this
        }

        override fun setTextColor(textColor: Int): Builder {
            arg.putInt(Constants.MESSAGE_COLOR, textColor)
            return this
        }

        override fun setTextSize(textSize: Float): Builder {
            arg.putFloat(Constants.MESSAGE_SIZE, textSize)
            return this
        }

        override fun setCircleRadius(radius: Int): Builder {
            arg.putInt(Constants.CIRCLE_RADIUS, radius)
            return this
        }

        override fun setCircleWidth(circleWidth: Float): Builder {
            arg.putFloat(Constants.CIRCLE_WIDTH, circleWidth)
            return this
        }

        override fun setCircleColor(circleColor: Int): Builder {
            arg.putInt(Constants.CIRCLE_COLOR, circleColor)
            return this
        }

        override fun setBottomCircleColor(bottomCircleColor: Int): Builder {
            arg.putInt(Constants.CIRCLE_BOTTOM_COLOR, bottomCircleColor)
            return this
        }

        override fun setBackgroundResource(resource: Int): Builder {
            arg.putInt(Constants.BACKGROUND_RESOURCE, resource)
            return this
        }

        override fun setDimAmount(dimAmount: Float): Builder {
            arg.putFloat(Constants.DIM_AMOUNT, dimAmount)
            return this
        }

        override fun setAnimResource(resource: Int): Builder {
            arg.putInt(Constants.ANIMATION, resource)
            return this
        }

        override fun setOutSideCancel(cancel: Boolean): Builder {
            arg.putBoolean(Constants.OUT_SIDE_CANCEL, cancel)
            return this
        }

        override fun setDialogShowOnBackListener(listener: OnDialogShowOnBackListener): Builder {
            arg.putSerializable(Constants.DIALOG_ON_BACK_LISTENER, listener)
            return this
        }

        override fun setBlackStyle(): Builder {
            arg.putBoolean(Constants.IS_BLACK_STYLE, true)
            return this
        }

        override fun setScaleDisplay(): Builder {
            arg.putBoolean(Constants.SCALE_DISPLAY, true)
            return this
        }

        override fun setScaleColor(scaleColor: Int): Builder {
            arg.putInt(Constants.SCALE_COLOR, scaleColor)
            return this
        }

        override fun setScaleSize(scaleSize: Float): Builder {
            arg.putFloat(Constants.SCALE_SIZE, scaleSize)
            return this
        }

        override fun setProgress(progress: Int): Builder {
            fragment.setProgress(progress)
            return this
        }

        override fun getMaxProgress(): Int {
            return maxProgress
        }

        override fun setMaxProgress(maxProgress: Int): Builder {
            this.maxProgress = maxProgress
            arg.putInt(Constants.MAX_PROGRESS, maxProgress)
            return this
        }


        override fun show(): Builder {
            fragment.arguments = arg
            fragment.show((mContext as Activity).fragmentManager)
            return this
        }

        override fun dismiss() {
            Handler().postDelayed({
                if (fragment.dialog?.isShowing == true)
                    fragment.dismiss()
            }, 100)
        }

    }
}