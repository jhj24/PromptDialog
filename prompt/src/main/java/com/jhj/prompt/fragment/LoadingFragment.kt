package com.jhj.prompt.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
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
class LoadingFragment : BaseDialogFragment() {

    enum class LoadingStyle {
        OLD_STYLE,
        NEW_STYLE
    }

    companion object {
        const val MESSAGE_TEXT_SIZE = 15f
        const val CIRCLE_WIDTH = 6f
        const val CIRCLE_RADIUS = 75
        val LOADING_STYLE = LoadingStyle.OLD_STYLE

        val BLACK_STYLE_TEXT_COLOR = Color.WHITE
        val BLACK_STYLE_CIRCLE_COLOR = Color.WHITE
        val BLACK_STYLE_CIRCLE_BOTTOM_COLOR = Color.GRAY
        val BLACK_STYLE_BACKGROUND = R.drawable.bg_progress_black_dialog

        val WHITE_STYLE_TEXT_COLOR = Color.BLACK
        val WHITE_STYLE_CIRCLE_COLOR = Color.BLACK
        val WHITE_STYLE_CIRCLE_BOTTOM_COLOR = Color.LTGRAY
        val WHITE_STYLE_BACKGROUND = R.drawable.bg_progress_white_dialog
    }

    private var mTextColor: Int = -1
    private var mCircleColor: Int = -1
    private var mBottomCircleColor: Int = -1
    private var mBackgroundResource: Int = -1
    private var mCircleWidth: Float = -1f
    private var mCircleRadius: Int = -1

    override val layoutRes: Int
        get() = R.layout.layout_progress_view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isBlackStyle = arguments?.getBoolean(Constants.IS_BLACK_STYLE, false)
                ?: false
        val defaultTextColor: Int
        val defaultCircleColor: Int
        val defaultBottomCircleColor: Int
        val defaultBackgroundResource: Int

        if (isBlackStyle) {
            defaultTextColor = BLACK_STYLE_TEXT_COLOR
            defaultCircleColor = BLACK_STYLE_CIRCLE_COLOR
            defaultBottomCircleColor = BLACK_STYLE_CIRCLE_BOTTOM_COLOR
            defaultBackgroundResource = BLACK_STYLE_BACKGROUND
        } else {
            defaultTextColor = WHITE_STYLE_TEXT_COLOR
            defaultCircleColor = WHITE_STYLE_CIRCLE_COLOR
            defaultBottomCircleColor = WHITE_STYLE_CIRCLE_BOTTOM_COLOR
            defaultBackgroundResource = WHITE_STYLE_BACKGROUND
        }

        mCircleWidth = arguments?.getFloat(Constants.CIRCLE_WIDTH, CIRCLE_WIDTH)
                ?: CIRCLE_WIDTH
        mCircleRadius = arguments?.getInt(Constants.CIRCLE_RADIUS, CIRCLE_RADIUS)
                ?: CIRCLE_RADIUS
        mTextColor = arguments?.getInt(Constants.MESSAGE_COLOR, defaultTextColor)
                ?: defaultTextColor
        mCircleColor = arguments?.getInt(Constants.CIRCLE_COLOR, defaultCircleColor)
                ?: defaultCircleColor
        mBottomCircleColor = arguments?.getInt(Constants.CIRCLE_BOTTOM_COLOR, defaultBottomCircleColor)
                ?: defaultBottomCircleColor
        mBackgroundResource = arguments?.getInt(Constants.BACKGROUND_RESOURCE, defaultBackgroundResource)
                ?: defaultBackgroundResource

        initView(view)
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
        val circleView = view.circle_loading_new_style
        circleView.visibility = View.VISIBLE
        circleView.circleWidth = mCircleWidth
        circleView.layoutParams = LinearLayout.LayoutParams(mCircleRadius * 2, mCircleRadius * 2)
        circleView.circleColor = mCircleColor
        circleView.bottomCircleColor = mBottomCircleColor
        view.tv_loading_msg.textColor = mTextColor
        view.layout_progress_dialog.backgroundResource = mBackgroundResource
    }

    private fun setOldStyleCircle(view: View) {
        val circleView = view.circle_loading_old_style
        circleView.visibility = View.VISIBLE
        circleView.circleWidth = mCircleWidth
        circleView.layoutParams = LinearLayout.LayoutParams(mCircleRadius * 2, mCircleRadius * 2)
        circleView.circleColor = mCircleColor
        circleView.bottomCircleColor = mBottomCircleColor
        view.tv_loading_msg.textColor = mTextColor
        view.layout_progress_dialog.backgroundResource = mBackgroundResource
    }

    class Builder(val context: Context) : BaseBuilder<Builder>(context) {

        private val mFragment = LoadingFragment()

        override val fragment: LoadingFragment
            get() = mFragment

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

        fun setDialogBlack(isBlackStyle: Boolean): Builder {
            arg.putBoolean(Constants.IS_BLACK_STYLE, isBlackStyle)
            return this
        }
    }
}