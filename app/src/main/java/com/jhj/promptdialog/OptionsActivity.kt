package com.jhj.promptdialog

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import com.jhj.prompt.fragment.OptionsFragment
import com.jhj.prompt.fragment.TimeFragment
import com.jhj.prompt.fragment.options.interfaces.OnOptionsSelectedListener
import com.jhj.prompt.fragment.options.interfaces.OnTimeSelectedListener
import kotlinx.android.synthetic.main.activity_progress.*
import org.jetbrains.anko.doAsync
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

        zero.setOnClickListener {
            val a = System.nanoTime()
            OptionsFragment.Builder<AreaUtil.AreaNode>()
                    .setTitle("地区选择")
                    .setSelectOptions(3)
                    .setShowItemNum(11)
                    .setLabels("1", "2", null)
                    .setLinkedPicker(provinceList, cityList)
                    .setSelectOptions(1, 1)
                    .setOptionsBackgroundResource(R.drawable.bg_progress_black_dialog)
                    .setCyclic(false)
                    .setSubmitListener(object : OnOptionsSelectedListener<AreaUtil.AreaNode> {
                        override fun onOptionsSelect(options: OptionsFragment<AreaUtil.AreaNode>, options1: Int?, options2: Int?, options3: Int?) {
                            toast(options1.toString())
                        }
                    })
                    .build()
                    .show(supportFragmentManager)
            Log.w("xxx", (System.nanoTime() - a).toString())
        }

        one.setOnClickListener {
            OptionsFragment.Builder<AreaUtil.AreaNode>()
                    .setLinkedPicker(provinceList, cityList)
                    .setMarginHorizontal(150)
                    .setShowItemNum(9)
                    .setAnimation(R.style.anim_dialog_center)
                    .setDialogGravity(Gravity.CENTER)
                    .setSubmitListener(object : OnOptionsSelectedListener<AreaUtil.AreaNode> {
                        override fun onOptionsSelect(options: OptionsFragment<AreaUtil.AreaNode>, options1: Int?, options2: Int?, options3: Int?) {
                            toast(options1.toString())
                        }
                    })
                    .build()
                    .show(supportFragmentManager)
        }

        two.setOnClickListener {
            OptionsFragment.Builder<AreaUtil.AreaNode>()
                    .setLinkedPicker(provinceList, cityList, districtList)
                    .setSubmitListener(object : OnOptionsSelectedListener<AreaUtil.AreaNode> {
                        override fun onOptionsSelect(options: OptionsFragment<AreaUtil.AreaNode>, options1: Int?, options2: Int?, options3: Int?) {
                            toast(options1.toString())
                        }
                    })
                    .build()
                    .show(supportFragmentManager)
        }

        three.setOnClickListener {
            val selectedDate = Calendar.getInstance()//系统当前时间
            TimeFragment.Builder()
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
                    .build()
                    .show(supportFragmentManager)
        }
        four.setOnClickListener {
            val selectedDate = Calendar.getInstance()//系统当前时间
            TimeFragment.Builder()
                    .setDate(selectedDate)
                    .setDialogGravity(Gravity.CENTER)
                    .setAnimation(R.style.anim_dialog_center)
                    .setMarginHorizontal(200)
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
                    .build()
                    .show(supportFragmentManager)
        }
        five.setOnClickListener {
            val selectedDate = Calendar.getInstance()//系统当前时间
            val startDate = Calendar.getInstance()
            startDate.set(2014, 0, 1)
            val endDate = Calendar.getInstance()
            endDate.set(2025, 12, 31)
            TimeFragment.Builder()
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
                    .build()
                    .show(supportFragmentManager)
        }
        six.setOnClickListener {
            val selectedDate = Calendar.getInstance()//系统当前时间
            val startDate = Calendar.getInstance()
            startDate.set(2018, 1, 1)
           /* val endDate = Calendar.getInstance()
            endDate.set(2018, 1, 1)*/
            TimeFragment.Builder()
                    .setDate(selectedDate)
                    .setCyclic(false)
                    .setTitle("日历")
                    .setRangDate(startDate)
                    .setDisplayStyle(booleanArrayOf(true, true, true, false, false, false))
                    .setOnlyCenterLabel(false)
                    .setSubmitListener(object : OnTimeSelectedListener {
                        override fun onTimeSelect(date: Date, v: View) {
                            val df = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
                            toast(df.format(date))
                        }

                    })
                    .build()
                    .show(supportFragmentManager)
        }

        seven.setOnClickListener { }
        seven.setOnClickListener {

            val a = System.nanoTime()
            val selectedDate = Calendar.getInstance()//系统当前时间
            val startDate = Calendar.getInstance()
            startDate.set(2014, 0, 1, 12, 12)
            val endDate = Calendar.getInstance()
            endDate.set(2015, 11, 31, 8, 8)
            TimeFragment.Builder()
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
                    .build()
                    .show(supportFragmentManager)
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