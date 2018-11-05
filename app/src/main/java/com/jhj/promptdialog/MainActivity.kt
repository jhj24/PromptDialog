package com.jhj.promptdialog

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        one.onClick {
            startActivity<AlertActivity>()
        }

        two.onClick {
            startActivity<LoadingActivity>()

        }

        three.onClick {
            startActivity<OptionsActivity>()
        }

        four.onClick {
            startActivity<PopActivity>()
        }

    }


}
