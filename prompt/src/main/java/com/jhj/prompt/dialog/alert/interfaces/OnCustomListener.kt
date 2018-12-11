package com.jhj.prompt.dialog.alert.interfaces

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import com.jhj.prompt.dialog.alert.AlertFragment

/**
 * 自定义View
 * Created by jhj on 2018-3-6 0006.
 */
interface OnCustomListener : Parcelable {

    fun onLayout(view: View, alertFragment: AlertFragment)

    override fun writeToParcel(dest: Parcel?, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }
}