package com.jhj.promptdialog

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import com.jhj.prompt.options.OptionsFragment
import com.jhj.prompt.options.TimeFragment
import com.jhj.prompt.options.interfaces.OnOptionsSelectedListener
import com.jhj.prompt.options.interfaces.OnTimeSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

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



    }


}
