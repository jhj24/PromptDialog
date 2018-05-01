package com.jhj.promptdialog

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.jhj.prompt.listener.OnDialogShowOnBackListener
import com.jhj.prompt.dialog.alert.AlertFragment
import com.jhj.prompt.dialog.alert.constants.DialogStyle
import com.jhj.prompt.dialog.alert.interfaces.OnButtonClickedListener
import com.jhj.prompt.dialog.alert.interfaces.OnCustomListener
import com.jhj.prompt.dialog.alert.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.activity_alert.*
import kotlinx.android.synthetic.main.layout_image.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

/**
 * Created by jhj on 2018-3-17 0017.
 */
class AlertActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert)

        btn_default.onClick {
            AlertFragment.Builder(this@AlertActivity)
                    .setTitle("标题")
                    .setMessage("内容")
                    .setSubmitListener(object : OnButtonClickedListener {
                        override fun onClick(view: View?) {
                            toast("确定")
                        }
                    })
                    .setCancelListener(object : OnButtonClickedListener {
                        override fun onClick(view: View?) {
                            toast("取消")
                        }
                    })
                    .show()
        }


        btn_alert.onClick {
            AlertFragment.Builder(this@AlertActivity)
                    .setTitle("标题")
                    .setTitleColor(ContextCompat.getColor(this@AlertActivity, R.color.red))
                    .setMessage("内容")
                    .show()
        }

        btn_items.onClick {
            AlertFragment.Builder(this@AlertActivity)
                    .setTitle("列表")
                    .setItems(arrayListOf("1111", "2222"))
                    .setItems(arrayListOf("3333", "4444"), Color.BLACK)
                    .setItemClickedListener(object : OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            toast("position-->$position")
                        }
                    })
                    .setCancelListener(object : OnButtonClickedListener {
                        override fun onClick(view: View?) {
                            toast("取消")
                        }
                    })
                    .show()
        }

        btn_custom_layout.onClick {
            AlertFragment.Builder(this@AlertActivity)
                    .setTitle("自定义")
                    .setLayoutRes(R.layout.layout_image, object : OnCustomListener {
                        override fun onLayout(view: View) {
                            view.image.onClick {
                                toast("自定义样式")
                            }
                        }
                    }).setCancelListener(object : OnButtonClickedListener {
                        override fun onClick(view: View?) {
                            toast("取消")
                        }
                    })
                    .show()
        }
        btn_bottom.onClick {
            AlertFragment.Builder(this@AlertActivity)
                    .setDialogStyle(DialogStyle.DIALOG_BOTTOM)
                    .setTitle("标题")
                    .setMessage("内容")
                    .setSubmitListener(object : OnButtonClickedListener {
                        override fun onClick(view: View?) {
                            toast("确定")
                        }
                    })
                    .setCancelListener(object : OnButtonClickedListener {
                        override fun onClick(view: View?) {
                            toast("取消")
                        }
                    })
                    .show()
        }

        btn_bottom2.onClick {
            AlertFragment.Builder(this@AlertActivity)
                    .setDialogStyle(DialogStyle.DIALOG_BOTTOM_SEPARATE)
                    .setTitle("列表")
                    .setItems(arrayListOf("1111", "2222"))
                    .setItems(arrayListOf("3333", "4444"), Color.BLACK)
                    .setItemClickedListener(object : OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            toast("position-->$position")
                        }
                    })
                    .setCancelListener(object : OnButtonClickedListener {
                        override fun onClick(view: View?) {
                            toast("取消")
                        }
                    })
                    .show()
        }

        btn_bottom3.onClick {
            AlertFragment.Builder(this@AlertActivity)
                    .setDialogStyle(DialogStyle.DIALOG_BOTTOM)
                    .setPaddingHorizontal(0)
                    .setPaddingBottom(0)
                    .setBackgroundResource(R.drawable.bg_dialog_no_corner)
                    .setTitle("标题")
                    .setMessage("内容")
                    .setSubmitListener(object : OnButtonClickedListener {
                        override fun onClick(view: View?) {
                            toast("确定")
                        }
                    })
                    .setCancelListener(object : OnButtonClickedListener {
                        override fun onClick(view: View?) {
                            toast("取消")
                        }
                    })
                    .setDialogShowOnBackListener(object : OnDialogShowOnBackListener {
                        override fun cancel() {
                            toast("点击了返回按钮")
                        }
                    })
                    .show()
        }

    }

}