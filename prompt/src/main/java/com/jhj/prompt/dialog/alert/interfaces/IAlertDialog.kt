package com.jhj.prompt.dialog.alert.interfaces

import com.jhj.prompt.dialog.alert.constants.DialogStyleEnum
import com.jhj.prompt.dialog.alert.constants.TextGravity
import com.jhj.prompt.listener.OnDialogShowOnBackListener

/**
 * 警告提示框方法接口
 * Created by jhj on 2018-3-1 0001;
 */

interface IAlertDialog<T : IAlertDialog<T>> {

    fun setDialogStyle(style: DialogStyleEnum): T

    fun setBackgroundResource(resource: Int): T

    fun setTitle(title: String): T

    fun setTitleColor(titleColor: Int): T

    fun setTitleSize(titleSize: Float): T

    fun setTitleGravity(titleGravity: TextGravity): T

    fun setMessage(message: String): T

    fun setMessageColor(messageColor: Int): T

    fun setMessageSize(messageSize: Float): T

    fun setMessageGravity(messageGravity: TextGravity): T

    fun setItems(items: ArrayList<String>): T

    fun setItems(items: ArrayList<String>, textColor: Int): T

    fun setItemClickedListener(listener: OnItemClickListener): T

    fun setLayoutRes(resource: Int): T

    fun setLayoutRes(resource: Int, listener: OnCustomListener): T

    fun setSubmitListener(listener: OnButtonClickedListener): T

    fun setSubmitListener(text: String?, listener: OnButtonClickedListener): T

    fun setSubmitListener(text: String?, textColor: Int, listener: OnButtonClickedListener): T

    fun setCancelListener(listener: OnButtonClickedListener): T

    fun setCancelListener(text: String?, listener: OnButtonClickedListener): T

    fun setCancelListener(text: String?, textColor: Int, listener: OnButtonClickedListener): T

    fun setButtonTextSize(size: Float): T

    fun setCanceledOnTouchOutSide(cancel: Boolean): T

    fun setPaddingTop(top: Int): T

    fun setPaddingBottom(bottom: Int): T

    fun setPaddingHorizontal(horizontal: Int): T

    fun setDimAmount(dimAmount: Float): T

    fun setAnimation(resource: Int): T

    fun setDialogShowOnBackListener(listener: OnDialogShowOnBackListener): T

    fun isShow(): Boolean

    fun show(): T

    fun dismiss()


}
