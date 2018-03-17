package com.jhj.promptdialog

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.jhj.prompt.progress.LoadingFragment
import com.jhj.prompt.progress.PercentFragment
import com.jhj.prompt.progress.constants.LoadingStyle
import kotlinx.android.synthetic.main.activity_loading.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by jhj on 2018-3-17 0017.
 */
class LoadingActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        no_suggest_style_1.onClick {
            LoadingFragment.Builder(this@LoadingActivity)
                    .show()
        }

        style1.onClick {
            LoadingFragment.Builder(this@LoadingActivity)
                    .setText("....")
                    .show()
        }
        black_style1.onClick {
            LoadingFragment.Builder(this@LoadingActivity)
                    .setBlackStyle()
                    .setText("....")
                    .show()
        }
        define_style1.onClick {
            LoadingFragment.Builder(this@LoadingActivity)
                    .setCircleRadius((40 * resources.displayMetrics.density).toInt())
                    .setCircleColor(Color.RED)
                    .setCircleWidth(4 * resources.displayMetrics.density)
                    .show()
        }
        no_suggest_style_2.onClick {
            LoadingFragment.Builder(this@LoadingActivity)
                    .setLoadingStyle(LoadingStyle.NEW_STYLE)
                    .show()
        }
        style2.onClick {
            LoadingFragment.Builder(this@LoadingActivity)
                    .setLoadingStyle(LoadingStyle.NEW_STYLE)
                    .setText("....")
                    .show()
        }
        black_style2.onClick {
            LoadingFragment.Builder(this@LoadingActivity)
                    .setLoadingStyle(LoadingStyle.NEW_STYLE)
                    .setBlackStyle()
                    .setText("....")
                    .show()
        }
        define_style2.onClick {
            LoadingFragment.Builder(this@LoadingActivity)
                    .setLoadingStyle(LoadingStyle.NEW_STYLE)
                    .setCircleRadius((40 * resources.displayMetrics.density).toInt())
                    .setCircleColor(Color.RED)
                    .setCircleWidth(4 * resources.displayMetrics.density)
                    .show()
        }

        percent.onClick {
            val dialog = PercentFragment.Builder(this@LoadingActivity)
                    .show()
            setPro(dialog)
        }
        percent1.onClick {
            val dialog = PercentFragment.Builder(this@LoadingActivity)
                    .setScaleDisplay()
                    .setBlackStyle()
                    .show()
            setPro(dialog)
        }
        percent2.onClick {
            val dialog = PercentFragment.Builder(this@LoadingActivity)
                    .setScaleDisplay()
                    .setText("....")
                    .show()
            setPro(dialog)
        }
        percent3.onClick {
            val dialog = PercentFragment.Builder(this@LoadingActivity)
                    .setText("......")
                    .setScaleColor(ContextCompat.getColor(this@LoadingActivity, R.color.red))
                    .setScaleDisplay()
                    .setScaleColor(ContextCompat.getColor(this@LoadingActivity, R.color.red))
                    .setScaleSize(resources.displayMetrics.density * 24)
                    .setBottomCircleColor(ContextCompat.getColor(this@LoadingActivity, R.color.red))
                    .setCircleColor(ContextCompat.getColor(this@LoadingActivity, R.color.blue))
                    .setCircleRadius(40 * resources.displayMetrics.density.toInt())
                    .setCircleWidth(10 * resources.displayMetrics.density)
                    .setAnimResource(R.style.anim_dialog_bottom)
                    .setBlackStyle()
                    .setText("......")
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
}