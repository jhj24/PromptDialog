package com.jhj.promptdialog

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val a = AlertDialog.Builder(this)
                .create()


        one.setOnClickListener {
            startActivity<AlertActivity>()
        }

        two.setOnClickListener {
            startActivity<LoadingActivity>()

        }

        three.setOnClickListener {
            startActivity<OptionsActivity>()
        }

        four.setOnClickListener {
            startActivity<PopActivity>()
        }

    }


}
