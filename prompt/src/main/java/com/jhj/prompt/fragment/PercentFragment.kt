package com.jhj.prompt.fragment

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jhj.prompt.R
import com.jhj.prompt.fragment.base.BaseBuilder
import com.jhj.prompt.fragment.base.BaseDialogFragment
import com.jhj.prompt.fragment.base.Constants
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


        val BLACK_STYLE_TEXT_COLOR = Color.WHITE
        val BLACK_STYLE_CIRCLE_COLOR = Color.WHITE
        val BLACK_STYLE_SCALE_COLOE = Color.WHITE
        val BLACK_STYLE_CIRCLE_BOTTOM_COLOR = Color.GRAY
        val BLACK_STYLE_BACKGROUND = R.drawable.bg_progress_black_dialog

        val WHITE_STYLE_TEXT_COLOR = Color.BLACK
        val WHITE_STYLE_CIRCLE_COLOR = Color.BLACK
        val WHITE_STYLE_SCALE_COLOE = Color.BLACK
        val WHITE_STYLE_CIRCLE_BOTTOM_COLOR = Color.LTGRAY
        val WHITE_STYLE_BACKGROUND = R.drawable.bg_progress_white_dialog

    }

    private var mView: View? = null
    private var isCancel = false
    private var mScaleSize: Float = 0f
    private var mProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mScaleSize = 12f
        savedInstanceState?.let {
            if (it.getBoolean(Constants.ACTIVITY_DESTROY)) {
                dismiss()
            }
        }
    }

    override val layoutRes: Int
        get() = R.layout.layout_progress_view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.mView = view
        initView(mView as View)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(Constants.ACTIVITY_DESTROY, true)
    }


    private fun setProgress(i: Int) {

        if (mView == null) {
            mProgress = i
        }

        if (!isCancel) {
            mView?.circle_progress?.let {
                it.post {
                    it.progress = i
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        isCancel = true
    }

    private fun initView(view: View) {
        val textColor: Int
        val scaleColor: Int
        val circleColor: Int
        val bottomCircleColor: Int
        val backgroundResource: Int
        val isBlackStyle = arguments?.getBoolean(Constants.IS_BLACK_STYLE, false) ?: false
        val text = arguments?.getString(Constants.MESSAGE)
        val textSize = arguments?.getFloat(Constants.MESSAGE_SIZE, MESSAGE_TEXT_SIZE)
                ?: MESSAGE_TEXT_SIZE
        val circleRadius = arguments?.getInt(Constants.CIRCLE_RADIUS, CIRCLE_RADIUS)
                ?: CIRCLE_RADIUS
        val circleWidth = arguments?.getFloat(Constants.CIRCLE_WIDTH, CIRCLE_WIDTH) ?: CIRCLE_WIDTH
        val isShowScale = arguments?.getBoolean(Constants.SCALE_DISPLAY, true) ?: true
        val scaleSize = arguments?.getFloat(Constants.SCALE_SIZE, mScaleSize) ?: mScaleSize
        val maxProgress = arguments?.getInt(Constants.MAX_PROGRESS, MAX_PROGRESS) ?: MAX_PROGRESS
        //黑色主题
        if (isBlackStyle) {
            textColor = arguments?.getInt(Constants.MESSAGE_COLOR, BLACK_STYLE_TEXT_COLOR) ?: BLACK_STYLE_TEXT_COLOR
            scaleColor = arguments?.getInt(Constants.SCALE_COLOR, BLACK_STYLE_SCALE_COLOE) ?: BLACK_STYLE_SCALE_COLOE
            circleColor = arguments?.getInt(Constants.CIRCLE_COLOR, BLACK_STYLE_CIRCLE_COLOR) ?: BLACK_STYLE_CIRCLE_COLOR
            bottomCircleColor = arguments?.getInt(Constants.CIRCLE_BOTTOM_COLOR, BLACK_STYLE_CIRCLE_BOTTOM_COLOR) ?: BLACK_STYLE_CIRCLE_BOTTOM_COLOR
            backgroundResource = arguments?.getInt(Constants.BACKGROUND_RESOURCE, BLACK_STYLE_BACKGROUND) ?: BLACK_STYLE_BACKGROUND
        } else {
            textColor = arguments?.getInt(Constants.MESSAGE_COLOR, WHITE_STYLE_TEXT_COLOR) ?: WHITE_STYLE_TEXT_COLOR
            scaleColor = arguments?.getInt(Constants.SCALE_COLOR, WHITE_STYLE_SCALE_COLOE) ?: WHITE_STYLE_SCALE_COLOE
            circleColor = arguments?.getInt(Constants.CIRCLE_COLOR, WHITE_STYLE_CIRCLE_COLOR) ?: WHITE_STYLE_CIRCLE_COLOR
            bottomCircleColor = arguments?.getInt(Constants.CIRCLE_BOTTOM_COLOR, WHITE_STYLE_CIRCLE_BOTTOM_COLOR) ?: WHITE_STYLE_CIRCLE_BOTTOM_COLOR
            backgroundResource = arguments?.getInt(Constants.BACKGROUND_RESOURCE, WHITE_STYLE_BACKGROUND) ?: WHITE_STYLE_BACKGROUND
        }

        val circleView = view.circle_progress

        if (mProgress != 0) {
            circleView.progress = mProgress
        }

        //提示
        text?.let {
            view.tv_loading_msg.visibility = View.VISIBLE
            view.tv_loading_msg.text = text
            view.tv_loading_msg.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        }

        //百分比
        circleView.showScale = isShowScale
        circleView.scaleSize = scaleSize

        //圆环
        circleView.visibility = View.VISIBLE
        circleView.maxProgress = maxProgress
        circleView.circleWidth = circleWidth
        circleView.layoutParams = LinearLayout.LayoutParams(circleRadius * 2, circleRadius * 2)

        view.tv_loading_msg.textColor = textColor
        circleView.scaleColor = scaleColor
        circleView.circleColor = circleColor
        circleView.bottomCircleColor = bottomCircleColor
        view.layout_progress_dialog.backgroundResource = backgroundResource
    }

    class Builder(val context: Context) : BaseBuilder<Builder>(context) {

        private var maxProgress = 100
        private val mFragment = PercentFragment()

        override val fragment: PercentFragment
            get() = mFragment

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

        fun setDialogBlack(isBlackStyle: Boolean): Builder {
            arg.putBoolean(Constants.IS_BLACK_STYLE, isBlackStyle)
            return this
        }

        override fun dismiss() {
            Handler().postDelayed({
                if (fragment.dialog?.isShowing == true) {
                    super.dismiss()
                }
            }, 100)
        }
    }
}