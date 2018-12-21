package com.jhj.prompt.dialog.progress

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentActivity
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
class LoadingFragment : BaseDialogFragment() {


    companion object {
        const val MESSAGE_TEXT_SIZE = 15f
        const val CIRCLE_WIDTH = 6f
        const val CIRCLE_RADIUS = 75
    }

    enum class LoadingStyle {
        OLD_STYLE,
        NEW_STYLE
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGravity = Gravity.CENTER
    }

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_progress_view, container)
        initView(view)
        return view
    }


    override fun initParams(bundle: Bundle?) {
        super.initParams(bundle)
        bundle?.let {
            val text = arguments?.getString(Constants.MESSAGE)
            val textSize = arguments?.getFloat(Constants.MESSAGE_SIZE, MESSAGE_TEXT_SIZE)
            val style = arguments?.getSerializable(Constants.DIALOG_STYLE) as? LoadingStyle
            val isBlackStyle = arguments?.getBoolean(Constants.IS_BLACK_STYLE)
            val circleWidth = arguments?.getFloat(Constants.CIRCLE_WIDTH, CIRCLE_WIDTH)
            val circleRadius = arguments?.getInt(Constants.CIRCLE_RADIUS, CIRCLE_RADIUS)

            val textColor = arguments?.getInt(Constants.MESSAGE_COLOR, Color.WHITE)
            val circleColor = arguments?.getInt(Constants.CIRCLE_COLOR, Color.WHITE)
            val bottomCircleColor = arguments?.getInt(Constants.CIRCLE_BOTTOM_COLOR, Color.GRAY)
            val backgroundResource = arguments?.getInt(Constants.BACKGROUND_RESOURCE, R.drawable.bg_progress_black_dialog)

        }
    }

    private fun initView(view: View) {
        val text = arguments?.getString(Constants.MESSAGE)
        val textSize = arguments?.getFloat(Constants.MESSAGE_SIZE, MESSAGE_TEXT_SIZE)
        val style = arguments?.getSerializable(Constants.DIALOG_STYLE) as? LoadingStyle

        text?.let {
            view.tv_loading_msg.visibility = View.VISIBLE
            view.tv_loading_msg.text = it
            view.tv_loading_msg.textSize = textSize ?: MESSAGE_TEXT_SIZE
        }
        if (style == LoadingStyle.NEW_STYLE) {
            setNewStyleCircle(view)
        } else {
            setOldStyleCircle(view)
        }

    }


    private fun setNewStyleCircle(view: View) {

        val isBlackStyle = arguments?.getBoolean(Constants.IS_BLACK_STYLE)
        val circleWidth = arguments?.getFloat(Constants.CIRCLE_WIDTH, CIRCLE_WIDTH)
        val circleRadius = arguments?.getInt(Constants.CIRCLE_RADIUS, CIRCLE_RADIUS)

        val circleView = view.circle_loading_new_style

        circleView.visibility = View.VISIBLE
        circleView.circleWidth = circleWidth ?: CIRCLE_WIDTH
        circleView.layoutParams = LinearLayout.LayoutParams(
                (circleRadius ?: CIRCLE_RADIUS) * 2,
                (circleRadius ?: CIRCLE_RADIUS) * 2)

        if (isBlackStyle == true) {
            val textColor = arguments?.getInt(Constants.MESSAGE_COLOR, Color.WHITE)
            val circleColor = arguments?.getInt(Constants.CIRCLE_COLOR, Color.WHITE)
            val bottomCircleColor = arguments?.getInt(Constants.CIRCLE_BOTTOM_COLOR, Color.GRAY)
            val backgroundResource = arguments?.getInt(Constants.BACKGROUND_RESOURCE, R.drawable.bg_progress_black_dialog)

            circleView.circleColor = circleColor ?: Color.WHITE
            circleView.bottomCircleColor = bottomCircleColor ?: Color.GRAY
            view.tv_loading_msg.textColor = textColor ?: Color.WHITE
            view.layout_progress_dialog.backgroundResource = backgroundResource ?: R.drawable.bg_progress_black_dialog
        } else {
            val textColor = arguments?.getInt(Constants.MESSAGE_COLOR, Color.BLACK)
            val circleColor = arguments?.getInt(Constants.CIRCLE_COLOR, Color.BLACK)
            val bottomCircleColor = arguments?.getInt(Constants.CIRCLE_BOTTOM_COLOR, Color.LTGRAY)
            val backgroundResource = arguments?.getInt(Constants.BACKGROUND_RESOURCE, R.drawable.bg_progress_white_dialog)

            circleView.circleColor = circleColor ?: Color.WHITE
            circleView.bottomCircleColor = bottomCircleColor ?: Color.GRAY
            view.tv_loading_msg.textColor = textColor ?: Color.WHITE
            view.layout_progress_dialog.backgroundResource = backgroundResource ?: R.drawable.bg_progress_black_dialog
        }

    }

    private fun setOldStyleCircle(view: View) {
        val isBlackStyle = arguments?.getBoolean(Constants.IS_BLACK_STYLE)
        val circleWidth = arguments?.getFloat(Constants.CIRCLE_WIDTH, CIRCLE_WIDTH)
        val circleRadius = arguments?.getInt(Constants.CIRCLE_RADIUS, CIRCLE_RADIUS)

        val circleView = view.circle_loading_old_style

        circleView.visibility = View.VISIBLE
        circleView.circleWidth = circleWidth ?: CIRCLE_WIDTH

        circleView.layoutParams = LinearLayout.LayoutParams(
                (circleRadius ?: CIRCLE_RADIUS) * 2,
                (circleRadius ?: CIRCLE_RADIUS) * 2)


        if (isBlackStyle == true) {
            val textColor = arguments?.getInt(Constants.MESSAGE_COLOR, Color.WHITE)
            val circleColor = arguments?.getInt(Constants.CIRCLE_COLOR, Color.WHITE)
            val bottomCircleColor = arguments?.getInt(Constants.CIRCLE_BOTTOM_COLOR, Color.GRAY)
            val backgroundResource = arguments?.getInt(Constants.BACKGROUND_RESOURCE, R.drawable.bg_progress_black_dialog)

            circleView.circleColor = circleColor ?: Color.WHITE
            circleView.bottomCircleColor = bottomCircleColor ?: Color.GRAY
            view.tv_loading_msg.textColor = textColor ?: Color.WHITE
            view.layout_progress_dialog.backgroundResource = backgroundResource ?: R.drawable.bg_progress_black_dialog
        } else {
            val textColor = arguments?.getInt(Constants.MESSAGE_COLOR, Color.BLACK)
            val circleColor = arguments?.getInt(Constants.CIRCLE_COLOR, Color.BLACK)
            val bottomCircleColor = arguments?.getInt(Constants.CIRCLE_BOTTOM_COLOR, Color.LTGRAY)
            val backgroundResource = arguments?.getInt(Constants.BACKGROUND_RESOURCE, R.drawable.bg_progress_white_dialog)

            circleView.circleColor = circleColor ?: Color.WHITE
            circleView.bottomCircleColor = bottomCircleColor ?: Color.GRAY
            view.tv_loading_msg.textColor = textColor ?: Color.WHITE
            view.layout_progress_dialog.backgroundResource = backgroundResource ?: R.drawable.bg_progress_black_dialog
        }

    }

    class Builder(val mContext: Context) : BaseBuilder<Builder>() {

        private val fragment = LoadingFragment()

        fun setLoadingStyle(style: LoadingStyle): Builder {
            arg.putSerializable(Constants.DIALOG_STYLE, style)
            return this
        }

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

        fun isShow(): Boolean {
            return fragment.isShow() ?: false
        }

        fun show(): Builder {
            fragment.arguments = arg
            fragment.show((mContext as FragmentActivity).supportFragmentManager)
            return this
        }

        fun dismiss() {
            fragment.dismiss()
        }

    }
}