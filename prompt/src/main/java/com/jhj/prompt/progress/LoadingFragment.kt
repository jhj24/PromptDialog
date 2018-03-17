package com.jhj.prompt.progress

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jhj.prompt.BaseDialogFragment
import com.jhj.prompt.Constants
import com.jhj.prompt.R
import com.jhj.prompt.progress.constants.LoadingStyle
import com.jhj.prompt.progress.interfaces.IBaseProgress
import kotlinx.android.synthetic.main.layout_progress_view.view.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.textColor

/**
 * 处于加载中的dialogFragment
 * Created by jhj on 2018-3-15 0015.
 */
class LoadingFragment : BaseDialogFragment() {

    companion object {
        const val MESSAGE_TEXT_SIZE = 15f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGravity = Gravity.CENTER
        mAnim = R.style.anim_dialog_center
    }

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_progress_view, container)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        val text = arguments.getString(Constants.MESSAGE)
        val textSize = arguments.getFloat(Constants.MESSAGE_SIZE, MESSAGE_TEXT_SIZE)
        val style = arguments.getSerializable(Constants.DIALOG_STYLE) as? LoadingStyle

        text?.let {
            view.tv_msg.visibility = View.VISIBLE
            view.tv_msg.text = it
            view.tv_msg.textSize = textSize
        }
        if (style == LoadingStyle.NEW_STYLE) {
            setNewStyleCircle(view)
        } else {
            setOldStyleCircle(view)
        }
    }

    private fun setNewStyleCircle(view: View) {

        val density = activity.resources.displayMetrics.density
        val circleWidth = arguments.getFloat(Constants.CIRCLE_WIDTH, 2 * density)
        val circleRadius = arguments.getInt(Constants.CIRCLE_RADIUS, -1)
        val circleColor = arguments.getInt(Constants.CIRCLE_COLOR, -1)
        val bottomCircleColor = arguments.getInt(Constants.CIRCLE_BOTTOM_COLOR, -1)
        val textColor = arguments.getInt(Constants.MESSAGE_COLOR, -1)
        val isBlackStyle = arguments.getBoolean(Constants.IS_BLACK_STYLE)
        val backgroundResource = arguments.getInt(Constants.BACKGROUND_RESOURCE, -1)

        val circleView = view.circle_turn
        circleView.visibility = View.VISIBLE
        circleView.circleWidth = circleWidth

        val radius = if (circleRadius == -1) {
            25 * density.toInt()
        } else {
            circleRadius
        }
        val params = LinearLayout.LayoutParams(radius * 2, radius * 2)
        circleView.layoutParams = params

        if (isBlackStyle) {
            view.tv_msg.textColor = if (textColor != -1) textColor else Color.WHITE
            circleView.circleColor = if (circleColor != -1) circleColor else Color.WHITE
            circleView.bottomCircleColor = if (bottomCircleColor != -1) bottomCircleColor else Color.GRAY
            view.layout_progress_dialog.backgroundResource = if (backgroundResource != -1) backgroundResource else R.drawable.bg_progress_black_dialog
        } else {
            view.tv_msg.textColor = if (textColor != -1) textColor else Color.BLACK
            circleView.circleColor = if (circleColor != -1) circleColor else Color.BLACK
            circleView.bottomCircleColor = if (bottomCircleColor != -1) bottomCircleColor else Color.LTGRAY
            view.layout_progress_dialog.backgroundResource = if (backgroundResource != -1) backgroundResource else R.drawable.bg_progress_dialog
        }

    }

    private fun setOldStyleCircle(view: View) {
        val density = activity.resources.displayMetrics.density
        val circleWidth = arguments.getFloat(Constants.CIRCLE_WIDTH, 2 * density)
        val circleRadius = arguments.getInt(Constants.CIRCLE_RADIUS, -1)
        val circleColor = arguments.getInt(Constants.CIRCLE_COLOR, -1)
        val bottomCircleColor = arguments.getInt(Constants.CIRCLE_BOTTOM_COLOR, -1)
        val textColor = arguments.getInt(Constants.MESSAGE_COLOR, -1)
        val isBlackStyle = arguments.getBoolean(Constants.IS_BLACK_STYLE)
        val backgroundResource = arguments.getInt(Constants.BACKGROUND_RESOURCE, -1)

        val circleView = view.circle_around
        circleView.visibility = View.VISIBLE
        circleView.circleWidth = circleWidth

        val radius = if (circleRadius == -1) {
            25 * density.toInt()
        } else {
            circleRadius
        }
        val params = LinearLayout.LayoutParams(radius * 2, radius * 2)
        circleView.layoutParams = params

        if (isBlackStyle) {
            view.tv_msg.textColor = if (textColor != -1) textColor else Color.WHITE
            circleView.circleColor = if (circleColor != -1) circleColor else Color.WHITE
            circleView.bottomCircleColor = if (bottomCircleColor != -1) bottomCircleColor else Color.GRAY
            view.layout_progress_dialog.backgroundResource = if (backgroundResource != -1) backgroundResource else R.drawable.bg_progress_black_dialog
        } else {
            view.tv_msg.textColor = if (textColor != -1) textColor else Color.BLACK
            circleView.circleColor = if (circleColor != -1) circleColor else Color.BLACK
            circleView.bottomCircleColor = if (bottomCircleColor != -1) bottomCircleColor else Color.LTGRAY
            view.layout_progress_dialog.backgroundResource = if (backgroundResource != -1) backgroundResource else R.drawable.bg_progress_dialog
        }

    }

    class Builder(val mContext: Context) : IBaseProgress<Builder> {


        private val arg = Bundle()
        private val fragment = LoadingFragment()

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

        override fun setOutSideCanccel(cancel: Boolean): Builder {
            arg.putBoolean(Constants.OUT_SIDE_CANCEL, cancel)
            return this
        }

        override fun setBlackStyle(): Builder {
            arg.putBoolean(Constants.IS_BLACK_STYLE, true)
            return this
        }

        fun setLoadingStyle(style: LoadingStyle): Builder {
            arg.putSerializable(Constants.DIALOG_STYLE, style)
            return this
        }

        override fun show(): Builder {
            fragment.arguments = arg
            fragment.show((mContext as Activity).fragmentManager)
            return this
        }

        override fun dismiss() {
            fragment.dismiss()
        }

    }
}