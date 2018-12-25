# PromptDialog

可以根据需求设置各种弹出框和、示框

- AlertFragment：普通提示框
- LoadingFragment：处于加载中的提示框
- PercentFragment：处于加载中的带百分比的提示框
- OptionsFragment：三级联动
- TimeFragment：时间选择联动
- PopWindow：Pop弹出框


## AlertFragment 普通提示框
setDialogBottomSeparate(isSeparate: Boolean)
setBackgroundResource(resource: Int)
setTitle(title: String)
setTitleColor(titleColor: Int)
setTitleSize(titleSize: Float)
setTitleGravity(titleGravity: Int)
setMessage(message: String)
setMessageColor(messageColor: Int)
setMessageSize(messageSize: Float)
setMessageGravity(messageGravity: Int)
setItems(items: ArrayList<String>)
setItems(items: ArrayList<String>, textColor: Int)
setCustomLayoutRes(@LayoutRes resource: Int)
setCustomLayoutRes(@LayoutRes resource: Int, listener: OnCustomListener)
setSubmitListener(listener: OnButtonClickedListener)
setSubmitListener(text: String?, listener: OnButtonClickedListener)
setSubmitListener(text: String?, textColor: Int, listener: OnButtonClickedListener)
setCancelListener(listener: OnButtonClickedListener)
setCancelListener(text: String?, listener: OnButtonClickedListener)
setCancelListener(text: String?, textColor: Int, listener: OnButtonClickedListener)
setButtonTextSize(size: Float)
setItemClickedListener(listener: OnItemClickListener)

## LoadingFragment 处于加载中的提示框

## PercentFragment 处于加载中的带百分比的提示框

## OptionsFragment 三级联动

## TimeFragment 时间选择联动

## PopWindow Pop弹出框
