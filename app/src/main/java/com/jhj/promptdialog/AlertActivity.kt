package com.jhj.promptdialog

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import com.jhj.prompt.fragment.AlertFragment
import com.jhj.prompt.fragment.base.BaseDialogFragment
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
            AlertFragment.Builder(this@AlertActivity)
                    .setTitle("标题")
                    .setDialogGravity(Gravity.TOP)
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
                    .show()
        }


        btn_alert.setOnClickListener {
            AlertFragment.Builder(this@AlertActivity)
                    .setTitle("标题")
                    .setTitleColor(ContextCompat.getColor(this@AlertActivity, R.color.red))
                    .setMessage("内容")
                    .show()
        }

        btn_items.setOnClickListener {
            AlertFragment.Builder(this@AlertActivity)
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
                    .show()
        }

        btn_custom_layout.setOnClickListener {
            AlertFragment.Builder(this@AlertActivity)
                    .setTitle("自定义")
                    .setCustomLayoutRes(R.layout.layout_image, object : AlertFragment.OnCustomListener {
                        override fun onLayout(alert: AlertFragment, view: View, alertFragment: AlertFragment) {
                            view.image.setOnClickListener {
                                toast("自定义样式")
                                alertFragment.dismiss()
                            }
                        }

                    }).setCancelListener(object : AlertFragment.OnButtonClickedListener {
                        override fun onClick(alert: AlertFragment, view: View?) {
                            toast("取消")
                        }
                    })
                    .setDialogHeight(700)
                    .show()
        }
        btn_bottom.setOnClickListener {
            AlertFragment.Builder(this@AlertActivity)
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
                    .show()
        }

        btn_bottom2.setOnClickListener {
            AlertFragment.Builder(this@AlertActivity)
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
                    .setCancelListener(object : AlertFragment.OnButtonClickedListener {
                        override fun onClick(alert: AlertFragment, view: View?) {
                            toast("取消")
                        }
                    })
                    .show()
        }

        btn_bottom3.setOnClickListener {
            AlertFragment.Builder(this@AlertActivity)
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
                    .setDialogShowOnBackListener(object : BaseDialogFragment.OnDialogShowOnBackListener {
                        override fun cancel(baseDialogFragment: BaseDialogFragment) {
                            toast("点击了返回按钮")
                        }
                    })
                    .show()
        }

        var selected = listOf<Int>()
        btn_bottom4.setOnClickListener {
            val a = AlertFragment.Builder(this@AlertActivity)
                    .setDialogGravity(Gravity.BOTTOM)
                    .setSelectedDataList(arrayListOf("1111", "2222", "3333", "44444", "5555", "6666", "1111", "2222", "3333", "44444", "5555", "6666"))
                    .setDialogHeight(1200)
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
                    .setDialogShowOnBackListener(object : BaseDialogFragment.OnDialogShowOnBackListener {
                        override fun cancel(baseDialogFragment: BaseDialogFragment) {

                        }

                    })
                    .show()
        }
    }

    fun <T> List<T>.toArrayList(): ArrayList<T> {
        return ArrayList(this)
    }

}