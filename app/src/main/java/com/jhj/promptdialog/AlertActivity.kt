package com.jhj.promptdialog

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Gravity
import android.view.View
import com.jhj.prompt.fragment.AlertFragment
import com.jhj.prompt.fragment.base.OnDialogDismissListener
import com.jhj.prompt.fragment.base.OnDialogShowOnBackListener
import kotlinx.android.synthetic.main.activity_alert.*
import kotlinx.android.synthetic.main.layout_image.view.*
import org.jetbrains.anko.toast

/**
 * Created by jhj on 2018-3-17 0017.
 */
class AlertActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert)

        btn_default.setOnClickListener {
            AlertFragment.Builder()
                    .setTitle("标题")
                    .setDialogGravity(Gravity.TOP)
                    .setMessage("内容")
                    .setOnDialogDismissListener(object : OnDialogDismissListener {
                        override fun callback(isDismiss: Boolean) {
                            Log.w("dialog", "isDismiss --> $isDismiss")
                        }
                    })
                    .setSubmitListener(object : AlertFragment.OnButtonClickedListener {
                        override fun onClick(alert: AlertFragment, view: View?) {
                            toast("确定")
                        }
                    })
                    .setCancelListener(object : AlertFragment.OnButtonClickedListener {
                        override fun onClick(alert: AlertFragment, view: View?) {
                            toast("取消")
                        }
                    })
                    .build()
                    .show(supportFragmentManager)
        }


        btn_alert.setOnClickListener {
            AlertFragment.Builder()
                    .setTitle("标题")
                    .setTitleColor(ContextCompat.getColor(this@AlertActivity, R.color.red))
                    .setMessage("内容")
                    .build()
                    .show(supportFragmentManager)
        }

        btn_items.setOnClickListener {
            AlertFragment.Builder()
                    .setTitle("列表")
                    .setDataList(arrayListOf("1111", "2222"))
                    .setDataList(arrayListOf("3333", "4444"), Color.RED)
                    .setItemClickedListener(object : AlertFragment.OnItemClickListener {
                        override fun onItemClick(alert: AlertFragment, view: View, position: Int) {
                            toast("position-->$position")
                        }

                    })
                    .setCancelListener(object : AlertFragment.OnButtonClickedListener {
                        override fun onClick(alert: AlertFragment, view: View?) {
                            toast("取消")
                        }

                    })
                    .build()
                    .show(supportFragmentManager)
        }

        btn_custom_layout.setOnClickListener {
            AlertFragment.Builder()
                    .setCustomLayoutRes(R.layout.layout_image, object : AlertFragment.OnCustomListener {
                        override fun onLayout(alert: AlertFragment, view: View) {
                            view.image.setOnClickListener {
                                toast("自定义样式")
                                alert.dismiss()
                            }
                        }

                    })
                    .build()
                    .show(supportFragmentManager)
        }
        btn_bottom.setOnClickListener {
            AlertFragment.Builder()
                    .setDialogGravity(Gravity.BOTTOM)
                    .setTitle("标题")
                    .setMessage("内容")
                    .setSubmitListener(object : AlertFragment.OnButtonClickedListener {
                        override fun onClick(alert: AlertFragment, view: View?) {
                            toast("确定")
                        }
                    })
                    .setCancelListener(object : AlertFragment.OnButtonClickedListener {
                        override fun onClick(alert: AlertFragment, view: View?) {
                            toast("取消")
                        }
                    })
                    .build()
                    .show(supportFragmentManager)
        }

        btn_bottom2.setOnClickListener {
            AlertFragment.Builder()
                    .setDialogGravity(Gravity.BOTTOM)
                    .setDialogBottomSeparate(true)
                    .setTitle("列表")
                    .setDataList(arrayListOf("1111", "2222"))
                    .setDataList(arrayListOf("3333", "4444"), Color.BLACK)
                    .setItemClickedListener(object : AlertFragment.OnItemClickListener {
                        override fun onItemClick(alert: AlertFragment, view: View, position: Int) {
                            toast("position-->$position")
                        }

                    })
                    .setDialogShowOnBackListener(object : OnDialogShowOnBackListener<AlertFragment> {
                        override fun cancel(fragment: AlertFragment) {

                        }
                    })
                    .setCancelListener(object : AlertFragment.OnButtonClickedListener {
                        override fun onClick(alert: AlertFragment, view: View?) {
                            toast("取消")
                        }
                    })
                    .build()
                    .show(supportFragmentManager)
        }

        btn_bottom3.setOnClickListener {
            AlertFragment.Builder()
                    .setDialogGravity(Gravity.BOTTOM)
                    .setMarginHorizontal(0)
                    .setMarginBottom(0)
                    .setBackgroundResource(R.drawable.bg_dialog_no_corner)
                    .setTitle("标题")
                    .setMessage("内容")
                    .setSubmitListener(object : AlertFragment.OnButtonClickedListener {
                        override fun onClick(alert: AlertFragment, view: View?) {
                            toast("确定")
                        }

                    })
                    .setCancelListener(object : AlertFragment.OnButtonClickedListener {
                        override fun onClick(alert: AlertFragment, view: View?) {
                            toast("取消")
                        }
                    })
                    .setDialogShowOnBackListener(object : OnDialogShowOnBackListener<AlertFragment> {
                        override fun cancel(fragment: AlertFragment) {
                            toast("点击了返回按钮")
                        }

                    })
                    .build()
                    .show(supportFragmentManager)
        }

        var selected = listOf<Int>()
        btn_bottom4.setOnClickListener {
            val a = AlertFragment.Builder()
                    .setDialogGravity(Gravity.BOTTOM)
                    .setSelectedDataList(arrayListOf("1111", "2222", "3333", "44444", "5555", "6666", "1111", "2222", "3333", "44444", "5555", "6666"))
                    .setDialogBottomSeparate(true)
                    .setTitle("选择")
                    .setSelectedItemMax(3)
                    .setSelectedItemMin(1)
                    .setListSelectedListener(object : AlertFragment.OnItemSelectedListener {
                        override fun onSelected(alert: AlertFragment, selectedList: List<Int>) {
                            val a: StringBuilder = java.lang.StringBuilder()
                            selectedList.forEach { a.append(it.toString()) }
                            toast(a)
                            selected = selectedList;
                        }
                    })
                    .setSelectedItem(selected.toArrayList())
                    .setDialogShowOnBackListener(object : OnDialogShowOnBackListener<AlertFragment> {
                        override fun cancel(fragment: AlertFragment) {

                        }
                    })
                    .build()
                    .show(supportFragmentManager)
        }
    }

    fun <T> List<T>.toArrayList(): ArrayList<T> {
        return ArrayList(this)
    }

}