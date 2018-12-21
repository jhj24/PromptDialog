package com.jhj.prompt.dialog.progress

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jhj.prompt.R
import com.jhj.prompt.base.BaseBuilder
import com.jhj.prompt.base.BaseDialogFragment
import com.jhj.prompt.base.Constants
import kotlinx.android.synthetic.main.layout_progress_view.view.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.textColor

/**
 * 处于加载中的dialogFragment
 * Created by jhj on 2018-3-15 0015.
 */
class PercentFragment : BaseDialogFragment() {

    companion object {
        const val MESSAGE_TEXT_SIZE = 15f
        const val CIRCLE_WIDTH = 6f
        const val CIRCLE_RADIUS = 75
        const val MAX_PROGRESS = 100
    }

    private var mView: View? = null
    private var isCancel = false
    private var mScaleSize: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGravity = Gravity.CENTER
        mScaleSize = requireActivity().resources.getDimensionPixelSize(R.dimen.textSize_scale).toFloat()
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(Constants.ACTIVITY_DESTROY, true)
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


        val isBlackStyle = arguments?.getBoolean(Constants.IS_BLACK_STYLE)
        val text = arguments?.getString(Constants.MESSAGE)
        val textSize = arguments?.getFloat(Constants.MESSAGE_SIZE, MESSAGE_TEXT_SIZE)
        val circleRadius = arguments?.getInt(Constants.CIRCLE_RADIUS, CIRCLE_RADIUS)
        val circleWidth = arguments?.getFloat(Constants.CIRCLE_WIDTH, CIRCLE_WIDTH)
        val isShowScale = arguments?.getBoolean(Constants.SCALE_DISPLAY, true)
        val scaleSize = arguments?.getFloat(Constants.SCALE_SIZE, mScaleSize)
        val maxProgress = arguments?.getInt(Constants.MAX_PROGRESS, MAX_PROGRESS)

        val circleView = view.circle_progress

        //提示
        text?.let {
            view.tv_loading_msg.visibility = View.VISIBLE
            view.tv_loading_msg.text = text
            view.tv_loading_msg.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize?: MESSAGE_TEXT_SIZE)
        }

        //百分比
        circleView.showScale = isShowScale ?: true
        circleView.scaleSize = scaleSize ?: mScaleSize

        //圆环
        circleView.visibility = View.VISIBLE
        circleView.maxProgress = maxProgress ?: MAX_PROGRESS
        circleView.circleWidth = circleWidth ?: CIRCLE_WIDTH
        circleView.layoutParams = LinearLayout.LayoutParams(
                (circleRadius ?: CIRCLE_RADIUS) * 2,
                (circleRadius ?: CIRCLE_RADIUS) * 2)

        //黑色主题
        if (isBlackStyle == true) {
            val textColor = arguments?.getInt(Constants.MESSAGE_COLOR, Color.WHITE)
            val scaleColor = arguments?.getInt(Constants.SCALE_COLOR, Color.WHITE)
            val circleColor = arguments?.getInt(Constants.CIRCLE_COLOR, Color.WHITE)
            val bottomCircleColor = arguments?.getInt(Constants.CIRCLE_BOTTOM_COLOR, Color.GRAY)
            val backgroundResource = arguments?.getInt(Constants.BACKGROUND_RESOURCE, R.drawable.bg_progress_black_dialog)

            view.tv_loading_msg.textColor = textColor ?: Color.WHITE
            circleView.circleColor = circleColor ?: Color.WHITE
            circleView.scaleColor = scaleColor ?: Color.WHITE
            circleView.bottomCircleColor = bottomCircleColor ?: Color.GRAY
            view.layout_progress_dialog.backgroundResource = backgroundResource ?: R.drawable.bg_progress_black_dialog
        } else {
            val textColor = arguments?.getInt(Constants.MESSAGE_COLOR, Color.BLACK)
            val scaleColor = arguments?.getInt(Constants.SCALE_COLOR, Color.BLACK)
            val circleColor = arguments?.getInt(Constants.CIRCLE_COLOR, Color.BLACK)
            val bottomCircleColor = arguments?.getInt(Constants.CIRCLE_BOTTOM_COLOR, Color.LTGRAY)
            val backgroundResource = arguments?.getInt(Constants.BACKGROUND_RESOURCE, R.drawable.bg_progress_white_dialog)

            view.tv_loading_msg.textColor = textColor ?: Color.BLACK
            circleView.circleColor = circleColor ?: Color.BLACK
            circleView.scaleColor = scaleColor ?: Color.BLACK
            circleView.bottomCircleColor = bottomCircleColor ?: Color.LTGRAY
            view.layout_progress_dialog.backgroundResource = backgroundResource ?: R.drawable.bg_progress_white_dialog

        }
    }

    class Builder(val mContext: Context) : BaseBuilder<Builder>() {

        private val fragment = PercentFragment()
        private var maxProgress = 100

        fun setText(text: String?): Builder {
            arg.putString(Constants.MESSAGE, text)
            return this
        }

        fun setTextColor(textColor: Int): Builder {
            arg.putInt(Constants.MESSAGE_COLOR, textColor)
            return this
        }

        fun setTextSize(textSize: Float): Builder {
            arg.putFloat(Constants.MESSAGE_SIZE, textSize)
            return this
        }

        fun setCircleRadius(radius: Int): Builder {
            arg.putInt(Constants.CIRCLE_RADIUS, radius)
            return this
        }

        fun setCircleWidth(circleWidth: Float): Builder {
            arg.putFloat(Constants.CIRCLE_WIDTH, circleWidth)
            return this
        }

        fun setCircleColor(circleColor: Int): Builder {
            arg.putInt(Constants.CIRCLE_COLOR, circleColor)
            return this
        }

        fun setBottomCircleColor(bottomCircleColor: Int): Builder {
            arg.putInt(Constants.CIRCLE_BOTTOM_COLOR, bottomCircleColor)
            return this
        }

        fun setBackgroundResource(resource: Int): Builder {
            arg.putInt(Constants.BACKGROUND_RESOURCE, resource)
            return this
        }

        fun setScaleDisplay(): Builder {
            arg.putBoolean(Constants.SCALE_DISPLAY, true)
            return this
        }

        fun setScaleColor(scaleColor: Int): Builder {
            arg.putInt(Constants.SCALE_COLOR, scaleColor)
            return this
        }

        fun setScaleSize(scaleSize: Float): Builder {
            arg.putFloat(Constants.SCALE_SIZE, scaleSize)
            return this
        }

        fun setProgress(progress: Int): Builder {
            fragment.setProgress(progress)
            return this
        }

        fun getMaxProgress(): Int {
            return maxProgress
        }

        fun setMaxProgress(maxProgress: Int): Builder {
            this.maxProgress = maxProgress
            arg.putInt(Constants.MAX_PROGRESS, maxProgress)
            return this
        }

        fun isShow(): Boolean {
            return fragment.isShow() ?: false
        }


        fun show(): Builder {
            fragment.arguments = arg
            fragment.show((mContext as FragmentActivity).supportFragmentManager)
            return this
        }

        fun dismiss() {
            Handler().postDelayed({
                if (fragment.dialog?.isShowing == true)
                    fragment.dismiss()
            }, 100)
        }

    }
}