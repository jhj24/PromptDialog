package com.jhj.promptdialog

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jhj.prompt.pop.PopWindow
import kotlinx.android.synthetic.main.activity_pop.*
import kotlinx.android.synthetic.main.layout_pop_item.view.*
import org.jetbrains.anko.toast

/**
 * Created by jhj on 18-7-17.
 */

class PopActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop)

        btn_0.setOnClickListener {
            PopWindow.Builder(this@PopActivity)
                    .setLayoutRes(R.layout.layout_pop_item)
                    .setPopWindowHeight(500)
                    .setCloseAnim(R.anim.anim_out_top)
                    .setOpenAnim(R.anim.anim_in_top)
                    .build()
                    .showAsDropDown(btn_0)
        }


        btn_1.setOnClickListener {
            val pop = PopWindow.Builder(this@PopActivity)
                    .setLayoutRes(R.layout.layout_pop_item)
                    .setBackgroundAlpha(0.7f)
                    .build()
            pop.showAsDropDown(btn_1, -(pop.getWidth() - btn_1.width), 0)
        }

        btn_2.setOnClickListener {
            val pop = PopWindow.Builder(this@PopActivity)
                    .setLayoutRes(R.layout.layout_pop_item) { view, popupWindow ->
                        view.tv_1.setOnClickListener {
                            popupWindow?.dismiss()
                            toast("1111")
                        }
                        view.tv_2.setOnClickListener {
                            popupWindow?.dismiss()
                            toast("2222")
                        }
                        view.tv_3.setOnClickListener {
                            popupWindow?.dismiss()
                            toast("3333")
                        }
                    }.build()
            pop.showAsDropDown(btn_2)
        }
        btn_3.setOnClickListener {
            PopWindow.Builder(this@PopActivity)
                    .setLayoutRes(R.layout.layout_pop_item)
                    .build()
                    .showAsDropDown(btn_3)
        }


    }

}
