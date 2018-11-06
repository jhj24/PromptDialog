package com.jhj.promptdialog

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import com.jhj.prompt.dialog.options.OptionsFragment
import com.jhj.prompt.dialog.options.TimeFragment
import com.jhj.prompt.dialog.options.interfaces.OnOptionsSelectedListener
import com.jhj.prompt.dialog.options.interfaces.OnTimeSelectedListener
import kotlinx.android.synthetic.main.activity_progress.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by jhj on 2018-3-17 0017.
 */
class OptionsActivity : FragmentActivity() {

    var provinceList = ArrayList<AreaUtil.AreaNode>()
    var cityList = ArrayList<ArrayList<AreaUtil.AreaNode>>()
    var districtList = ArrayList<ArrayList<ArrayList<AreaUtil.AreaNode>>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)
        initData()

        zero.onClick {
            val a = System.nanoTime()
            OptionsFragment.Builder<AreaUtil.AreaNode>(this@OptionsActivity)
                    .setTitle("地区选择")
                    .setSelectOptions(3)
                    .setLabels("1", "2", null)
                    .setLinkedPicker(provinceList, cityList)
                    .setSelectOptions(1, 1)
                    .setOptionsBackgroundResource(R.drawable.bg_progress_black_dialog)
                    .setCyclic(false)
                    .setSubmitListener(object : OnOptionsSelectedListener {
                        override fun onOptionsSelect(options1: Int?, options2: Int?, options3: Int?) {
                            toast(options1.toString())
                        }
                    })
                    .show()
            Log.w("xxx", (System.nanoTime() - a).toString())
        }

        one.onClick {
            OptionsFragment.Builder<AreaUtil.AreaNode>(this@OptionsActivity)
                    .setLinkedPicker(provinceList, cityList)
                    .setPaddingHorizontal(150)
                    .setItemNum(9)
                    .setAnimation(R.style.anim_dialog_center)
                    .setGravity(Gravity.CENTER)
                    .setSubmitListener(object : OnOptionsSelectedListener {
                        override fun onOptionsSelect(options1: Int?, options2: Int?, options3: Int?) {
                            toast(options1.toString())
                        }
                    })
                    .show()
        }

        two.onClick {
            OptionsFragment.Builder<AreaUtil.AreaNode>(this@OptionsActivity)
                    .setLinkedPicker(provinceList, cityList, districtList)
                    .setSubmitListener(object : OnOptionsSelectedListener {
                        override fun onOptionsSelect(options1: Int?, options2: Int?, options3: Int?) {
                            toast(options1.toString())
                        }
                    })
                    .show()
        }

        three.onClick {
            val selectedDate = Calendar.getInstance()//系统当前时间
            TimeFragment.Builder(this@OptionsActivity)
                    .setDate(selectedDate)
                    .setDisplayStyle(booleanArrayOf(false, false, false, true, true, false))
                    .setOnlyCenterLabel(false)
                    .setLabels(null, null, null, "时", "分", null)
                    .setSubmitListener(object : OnTimeSelectedListener {
                        override fun onTimeSelect(date: Date, v: View) {
                            val df = SimpleDateFormat("HH:mm", Locale.CHINA)
                            toast(df.format(date))
                        }

                    })
                    .show()
        }
        four.onClick {
            val selectedDate = Calendar.getInstance()//系统当前时间
            TimeFragment.Builder(this@OptionsActivity)
                    .setDate(selectedDate)
                    .setGravity(Gravity.CENTER)
                    .setAnimation(R.style.anim_dialog_center)
                    .setPaddingHorizontal(200)
                    .setOptionsBackgroundResource(R.drawable.bg_progress_black_dialog)
                    .setDisplayStyle(booleanArrayOf(false, false, false, true, true, false))
                    .setOnlyCenterLabel(false)
                    .setLabels(null, null, null, "时", "分", null)
                    .setSubmitListener(object : OnTimeSelectedListener {
                        override fun onTimeSelect(date: Date, v: View) {
                            val df = SimpleDateFormat("HH:mm", Locale.CHINA)
                            toast(df.format(date))
                        }

                    })
                    .show()
        }
        five.onClick {
            val selectedDate = Calendar.getInstance()//系统当前时间
            val startDate = Calendar.getInstance()
            startDate.set(2014, 0, 1)
            val endDate = Calendar.getInstance()
            endDate.set(2025, 12, 31)
            TimeFragment.Builder(this@OptionsActivity)
                    .setDate(selectedDate)
                    .setDisplayStyle(booleanArrayOf(true, true, true, false, false, false))
                    .setOnlyCenterLabel(false)
                    .setLabels("年", "月", "日", null, null, null)
                    .setSubmitListener(object : OnTimeSelectedListener {
                        override fun onTimeSelect(date: Date, v: View) {
                            val df = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
                            toast(df.format(date))
                        }

                    })
                    .show()
        }
        six.onClick {
            val selectedDate = Calendar.getInstance()//系统当前时间
            val startDate = Calendar.getInstance()
            startDate.set(2014, 0, 1)
            val endDate = Calendar.getInstance()
            endDate.set(2015, 11, 31)
            TimeFragment.Builder(this@OptionsActivity)
                    .setDate(selectedDate)
                    .setCyclic(false)
                    .setRangDate(startDate, endDate)
                    .setDisplayStyle(booleanArrayOf(true, true, true, true, true, true))
                    .setOnlyCenterLabel(false)
                    .setSubmitListener(object : OnTimeSelectedListener {
                        override fun onTimeSelect(date: Date, v: View) {
                            val df = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
                            toast(df.format(date))
                        }

                    })
                    .show()
        }

        seven.setOnClickListener { }
        seven.onClick {
            val a = System.nanoTime()
            val selectedDate = Calendar.getInstance()//系统当前时间
            val startDate = Calendar.getInstance()
            startDate.set(2014, 0, 1)
            val endDate = Calendar.getInstance()
            endDate.set(2015, 11, 31)
            TimeFragment.Builder(this@OptionsActivity)
                    .setDate(selectedDate)
                    .setCyclic(false)
                    .setRangDate(startDate, endDate)
                    .setDisplayStyle(booleanArrayOf(true, true, true, true, true, true))
                    .setSubmitListener(object : OnTimeSelectedListener {
                        override fun onTimeSelect(date: Date, v: View) {
                            val df = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
                            toast(df.format(date))
                        }

                    })
                    .show()
            Log.w("xxx", (System.nanoTime() - a).toString())
        }


    }

    private fun initData() {
        val area = AreaUtil.getAreaList(this)

        doAsync {
            provinceList = area.filter { it.areaDeep == 1 }.toArrayList()
            cityList = provinceList.map { provinceBean ->
                area.filter { it.areaDeep == 2 && it.areaParentId == provinceBean.areaId }.toArrayList()
            }.toArrayList()
            districtList = cityList.map { cityList ->
                cityList.map { cityBean ->
                    area.filter { it.areaDeep == 3 && it.areaParentId == cityBean.areaId }.toArrayList()
                }.toArrayList()
            }.toArrayList()
        }
    }

    private fun <T> List<T>.toArrayList(): ArrayList<T> {
        return ArrayList(this)
    }

}