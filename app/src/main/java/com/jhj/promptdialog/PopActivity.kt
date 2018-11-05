package com.jhj.promptdialog

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.widget.PopupWindow
import com.jhj.prompt.pop.OnCustomListener
import com.jhj.prompt.pop.PromptPopWindow
import kotlinx.android.synthetic.main.activity_pop.*
import kotlinx.android.synthetic.main.layout_pop_item.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

/**
 * Created by jhj on 18-7-17.
 */

class PopActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop)

        btn_0.onClick {
            PromptPopWindow.Builder(this@PopActivity)
                    .setLayoutRes(R.layout.layout_pop_item)
                    .build()
                    .showAsDropDown(btn_0)
        }


        btn_1.onClick {
            val pop = PromptPopWindow.Builder(this@PopActivity)
                    .setLayoutRes(R.layout.layout_pop_item)
                    .setBackgroundAlpha(0.7f)
                    .build()
            pop.showAsDropDown(btn_1, -(pop.getWidth() - btn_1.width), 0)
        }

        btn_2.onClick {
            val pop = PromptPopWindow.Builder(this@PopActivity)
                    .setCanceledOnTouchOutSide(false)
                    .setLayoutRes(R.layout.layout_pop_item, object : OnCustomListener {
                        override fun onLayout(view: View, popupWindow: PopupWindow?) {
                            view.tv_1.onClick {
                                popupWindow?.dismiss()
                                toast("1111")
                            }
                            view.tv_2.onClick {
                                popupWindow?.dismiss()
                                toast("2222")
                            }
                            view.tv_3.onClick {
                                popupWindow?.dismiss()
                                toast("3333")
                            }
                        }

                    })
                    .build()
            pop.showAsDropDown(btn_2, 0, -(btn_2.height + pop.getHeight()))
        }
        btn_3.onClick {
            PromptPopWindow.Builder(this@PopActivity)
                    .setLayoutRes(R.layout.layout_pop_item)
                    .setOnDismissListener(PopupWindow.OnDismissListener {
                        toast("popupWindow消失")
                    })
                    .setOnTouchListener(View.OnTouchListener { p0, p1 ->
                        if (p1.action == KeyEvent.ACTION_DOWN)
                            toast("触摸了popupWindow")
                        false
                    })
                    .build()
                    .showAsDropDown(btn_3)
        }


    }

}
