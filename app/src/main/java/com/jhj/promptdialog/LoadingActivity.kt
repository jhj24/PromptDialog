package com.jhj.promptdialog

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import com.jhj.prompt.fragment.base.BaseDialogFragment
import com.jhj.prompt.fragment.LoadingFragment
import com.jhj.prompt.fragment.PercentFragment
import com.jhj.prompt.fragment.base.OnDialogShowOnBackListener
import kotlinx.android.synthetic.main.activity_loading.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast

/**
 * Created by jhj on 2018-3-17 0017.
 */
class LoadingActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        no_suggest_style_1.setOnClickListener {

            LoadingFragment.Builder(this@LoadingActivity)
                    .setText("111")
                    .setDialogGravity(Gravity.TOP)
                    .setCancelOnTouchOut(false)
                    .setDialogShowOnBackListener(object : OnDialogShowOnBackListener<LoadingFragment> {
                        override fun cancel(fragment: LoadingFragment) {
                            toast("Dialog弹出时，我进行了back操作")
                        }
                    })
                    .show()
        }

        style1.setOnClickListener {
            LoadingFragment.Builder(this@LoadingActivity)
                    .setText("加载中...")
                    .show()
        }
        black_style1.setOnClickListener {
            LoadingFragment.Builder(this@LoadingActivity)
                    .setDialogBlack(true)
                    .setText("加载中...")
                    .show()
        }
        define_style1.setOnClickListener {
            LoadingFragment.Builder(this@LoadingActivity)
                    .setCircleRadius((40 * resources.displayMetrics.density).toInt())
                    .setCircleColor(Color.RED)
                    .setCircleWidth(4 * resources.displayMetrics.density)
                    .setDialogShowOnBackListener(object : OnDialogShowOnBackListener<LoadingFragment> {
                        override fun cancel(fragment: LoadingFragment) {
                            toast("Dialog弹出时，我进行了back操作")
                        }
                    })
                    .show()
        }
        no_suggest_style_2.setOnClickListener {
            LoadingFragment.Builder(this@LoadingActivity)
                    .setLoadingStyle(LoadingFragment.LoadingStyle.NEW_STYLE)
                    .show()
        }
        style2.setOnClickListener {
            LoadingFragment.Builder(this@LoadingActivity)
                    .setLoadingStyle(LoadingFragment.LoadingStyle.NEW_STYLE)
                    .setText("加载中...")
                    .show()
        }
        black_style2.setOnClickListener {
            LoadingFragment.Builder(this@LoadingActivity)
                    .setLoadingStyle(LoadingFragment.LoadingStyle.NEW_STYLE)
                    .setDialogBlack(true)
                    .setText("加载中...")
                    .show()
        }
        define_style2.setOnClickListener {
            LoadingFragment.Builder(this@LoadingActivity)
                    .setLoadingStyle(LoadingFragment.LoadingStyle.NEW_STYLE)
                    .setCircleRadius((40 * resources.displayMetrics.density).toInt())
                    .setCircleColor(Color.RED)
                    .setCircleWidth(4 * resources.displayMetrics.density)
                    .show()
        }

        percent.setOnClickListener {
            val dialog = PercentFragment.Builder(this@LoadingActivity)
                    .show()
            setPro(dialog)
        }
        percent1.setOnClickListener {
            val dialog = PercentFragment.Builder(this@LoadingActivity)
                    .setScaleDisplay()
                    .setDialogBlack(true)
                    .show()
            setPro(dialog)
        }
        percent2.setOnClickListener {
            val dialog = PercentFragment.Builder(this@LoadingActivity)
                    .setScaleDisplay()
                    .setText("加载中...")
                    .show()
            setPro(dialog)
        }
        percent3.setOnClickListener {
            val dialog = PercentFragment.Builder(this@LoadingActivity)
                    .setText("加载中...")
                    .setScaleColor(ContextCompat.getColor(this@LoadingActivity, R.color.red))
                    .setScaleDisplay()
                    .setScaleColor(ContextCompat.getColor(this@LoadingActivity, R.color.red))
                    .setScaleSize(24f)
                    .setBottomCircleColor(ContextCompat.getColor(this@LoadingActivity, R.color.red))
                    .setCircleColor(ContextCompat.getColor(this@LoadingActivity, R.color.blue))
                    .setCircleRadius(40 * resources.displayMetrics.density.toInt())
                    .setCircleWidth(10 * resources.displayMetrics.density)
                    .setAnimation(R.style.anim_dialog_bottom)
                    .setDialogBlack(true)
                    .setText("加载中...")
                    .setTextColor(ContextCompat.getColor(this@LoadingActivity, R.color.red))
                    .show()

            setPro(dialog)
        }


    }


    fun setPro(dialog: PercentFragment.Builder) {
        doAsync {
            for (i in 1..100) {
                Thread.sleep(25)
                runOnUiThread {
                    dialog.setProgress(i)
                    if (i == 100) {
                        dialog.dismiss()
                    }
                }
            }
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Log.w("xxx", "down")
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.w("xxx", "press")
    }


}