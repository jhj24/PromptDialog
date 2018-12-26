package com.jhj.prompt.fragment.base;

import android.graphics.Color;
import android.support.annotation.FloatRange;
import android.view.Gravity;

import com.jhj.prompt.R;
import com.jhj.prompt.fragment.LoadingFragment;
import com.jhj.prompt.fragment.options.utils.DividerType;

import java.util.Calendar;

/**
 * 各个Fragment都有默认样式，但不能满足不同App的需求，可以Application中进行设置，
 * 设置LoadingFragment和PercentFragment样式保持一致，
 * 而OptionsFragment和TimeFragment设置样式一致。
 */
public class PromptConfig {


    private static final float ALERT_TEXT_SIZE_TITLE = 22f;
    private static final float ALERT_TEXT_SIZE_ITEM_TITLE = 15f;
    private static final float ALERT_TEXT_SIZE_BUTTON = 18f;
    private static final int ALERT_TEXT_COLOR_TITLE = Color.BLACK;
    private static final int ALERT_TEXT_COLOR_MSG = Color.BLACK;
    private static final int ALERT_TEXT_COLOR_SUBMIT = Color.RED;
    private static final int ALERT_TEXT_COLOR_CANCEL = Color.BLUE;
    private static final int ALERT_TEXT_COLOR_ITEM = Color.BLUE;
    private static final int ALERT_ITEM_IMAGE_SELECTED = R.drawable.icon_choice;
    private static final int ALERT_ITEM_IMAGE_UNSELECTED = R.drawable.icon_choice_no;
    private static final int ALERT_TEXT_GRAVITY_TITLE = Gravity.CENTER;
    private static final int ALERT_TEXT_GRAVITY_MSG = Gravity.CENTER;
    private static final int ALERT_BACKGROUND_RESOURCE = R.drawable.bg_dialog_corner;
    private static final int ALERT_TEXT_COLOR_ITEM_TITLE = Color.GRAY;


    private static final float SCALE_SIZE = 12f;
    private static final int MAX_PROGRESS = 100;

    private static final float MESSAGE_TEXT_SIZE = 15f;
    private static final float CIRCLE_WIDTH = 6f;
    private static final int CIRCLE_RADIUS = 75;
    private static final LoadingFragment.LoadingStyle LOADING_STYLE = LoadingFragment.LoadingStyle.OLD_STYLE;

    private static final int BLACK_STYLE_TEXT_COLOR = Color.WHITE;
    private static final int BLACK_STYLE_SCALE_COLOR = Color.WHITE;
    private static final int BLACK_STYLE_CIRCLE_COLOR = Color.WHITE;
    private static final int BLACK_STYLE_CIRCLE_BOTTOM_COLOR = Color.GRAY;
    private static final int BLACK_STYLE_BACKGROUND = R.drawable.bg_progress_black_dialog;

    private static final int WHITE_STYLE_TEXT_COLOR = Color.BLACK;
    private static final int WHITE_STYLE_CIRCLE_COLOR = Color.BLACK;
    private static final int WHITE_STYLE_SCALE_COLOR = Color.BLACK;
    private static final int WHITE_STYLE_CIRCLE_BOTTOM_COLOR = Color.LTGRAY;
    private static final int WHITE_STYLE_BACKGROUND = R.drawable.bg_progress_white_dialog;


    public static final float PICKER_TEXT_SIZE_TITLE = 21f;
    public static final int PICKER_TEXT_COLOR_TITLE = Color.BLACK;
    public static final int PICKER_TEXT_COLOR_BUTTON = R.drawable.selector_pickerview_btn;
    public static final float PICKER_TEXT_SIZE_BOTTON = 18f;
    public static final int PICKER_TOP_BAR_BACKGROUND = R.color.pickerview_bg_topbar;
    public static final int PICKER_OPTIONS_BACKGROUND = R.drawable.bg_dialog_no_corner;
    public static final float PICKER_TEXT_SIZE_CENTER = 20f;
    public static final int PICKER_TEXT_COLOR_CENTER = 0xFF2a2a2a;
    public static final int PICKER_TEXT_COLOR_OUT = 0xFFa8a8a8;
    public static final DividerType PICKER_DIVIDER_TYPE = DividerType.FILL;
    public static final int PICKER_DIVIDER_COLOR = 0xFFd5d5d5;
    public static final int PICKER_SHOW_ITEM_NUM = 9;
    public static final float PICKER_LINE_SPACEING_RATIO = 1.6f;
    public static final int PICKER_EXTRA_HEIGHT = 2;
    public static final int PICKER_X_OFFSET = 0;
    public static final int PICKER_OPTIONS_TEXT_GRAVITY = Gravity.CENTER;


    private static volatile PromptConfig singleton;


    //AlertFragment
    private float alertTextSizeTitle = ALERT_TEXT_SIZE_TITLE;
    private float alertTextSizeItemTitle = ALERT_TEXT_SIZE_ITEM_TITLE;
    private float alertTextSizeMessage = MESSAGE_TEXT_SIZE;
    private float alertTextSizeButton = ALERT_TEXT_SIZE_BUTTON;
    private int alertTextColorTitle = ALERT_TEXT_COLOR_TITLE;
    private int alertTextColorMessage = ALERT_TEXT_COLOR_MSG;
    private int alertTextColorSubmit = ALERT_TEXT_COLOR_SUBMIT;
    private int alertTextColorCancel = ALERT_TEXT_COLOR_CANCEL;
    private int alertTextColorItem = ALERT_TEXT_COLOR_ITEM;
    private int alertItemImageSelected = ALERT_ITEM_IMAGE_SELECTED;
    private int alertItemImageUnselected = ALERT_ITEM_IMAGE_UNSELECTED;
    private int alertTextGravityTitle = ALERT_TEXT_GRAVITY_TITLE;
    private int alertTextGravityMessage = ALERT_TEXT_GRAVITY_MSG;
    private int alertBackgroundResource = ALERT_BACKGROUND_RESOURCE;
    private int alertTextColorItemTitle = ALERT_TEXT_COLOR_ITEM_TITLE;


    //LoadingFragment 和 PercentFragment 共同属性
    private float textSizeMessage = MESSAGE_TEXT_SIZE;
    private float circleWidth = CIRCLE_WIDTH;
    private int circleRadius = CIRCLE_RADIUS;
    private int blackStyleTextColor = BLACK_STYLE_TEXT_COLOR;
    private int blackStyleCircleColor = BLACK_STYLE_CIRCLE_COLOR;
    private int blackStyleCircleBottomColor = BLACK_STYLE_CIRCLE_BOTTOM_COLOR;
    private int blackStyleBackground = BLACK_STYLE_BACKGROUND;
    private int whiteStyleTextColor = WHITE_STYLE_TEXT_COLOR;
    private int whiteStyleCircleColor = WHITE_STYLE_CIRCLE_COLOR;
    private int whiteStyleCircleBottomColor = WHITE_STYLE_CIRCLE_BOTTOM_COLOR;
    private int whiteStyleBackground = WHITE_STYLE_BACKGROUND;

    //LoadingFragment
    private LoadingFragment.LoadingStyle loadingStyle = LOADING_STYLE;

    //PercentFragment
    private float scaleSize = SCALE_SIZE;
    private int maxProgress = MAX_PROGRESS;
    private int blackStyleScaleColor = BLACK_STYLE_SCALE_COLOR;
    private int whiteStyleScaleColor = WHITE_STYLE_SCALE_COLOR;


    //OptionsFragment 和 TimeFragment 共同属性
    private float pickerTextSizeTitle = PICKER_TEXT_SIZE_TITLE;
    private float pickerTextSizeButton = PICKER_TEXT_SIZE_BOTTON;
    private float pickerTextSizeCenter = PICKER_TEXT_SIZE_CENTER;
    private int pickerTextColorTitle = PICKER_TEXT_COLOR_TITLE;
    private int pickerTextColorButton = PICKER_TEXT_COLOR_BUTTON;
    private int pickerTextColorCenter = PICKER_TEXT_COLOR_CENTER;
    private int pickerTextColorOUT = PICKER_TEXT_COLOR_OUT;
    private int pickerTopBarBackground = PICKER_TOP_BAR_BACKGROUND;
    private int pickerOptionsBackground = PICKER_OPTIONS_BACKGROUND;
    private DividerType pickerDividerType = PICKER_DIVIDER_TYPE;
    private int pickerDividerColor = PICKER_DIVIDER_COLOR;
    private int pickerItemNum = PICKER_SHOW_ITEM_NUM;
    private float pickerLineSpacingRatio = PICKER_LINE_SPACEING_RATIO;
    private int pickerExtraHeight = PICKER_EXTRA_HEIGHT;
    private int pickerXOffset = PICKER_X_OFFSET;
    private int pickerOptionsTextGravity = PICKER_OPTIONS_TEXT_GRAVITY;

    private Calendar timeStartCalendar;
    private Calendar timeEndCalendar;


    private PromptConfig() {
    }

    public static PromptConfig getInstance() {
        if (singleton == null) {
            synchronized (PromptConfig.class) {
                if (singleton == null) {
                    singleton = new PromptConfig();
                }
            }
        }
        return singleton;
    }

    public static PromptConfig getSingleton() {
        return singleton;
    }

    public static void setSingleton(PromptConfig singleton) {
        PromptConfig.singleton = singleton;
    }


    public float getAlertTextSizeTitle() {
        return alertTextSizeTitle;
    }

    public PromptConfig setAlertTextSizeTitle(float alertTextSizeTitle) {
        this.alertTextSizeTitle = alertTextSizeTitle;
        return this;
    }

    public float getAlertTextSizeItemTitle() {
        return alertTextSizeItemTitle;
    }

    public PromptConfig setAlertTextSizeItemTitle(float alertTextSizeItemTitle) {
        this.alertTextSizeItemTitle = alertTextSizeItemTitle;
        return this;
    }

    public float getAlertTextSizeMessage() {
        return alertTextSizeMessage;
    }

    public PromptConfig setAlertTextSizeMessage(float alertTextSizeMessage) {
        this.alertTextSizeMessage = alertTextSizeMessage;
        return this;
    }

    public float getAlertTextSizeButton() {
        return alertTextSizeButton;
    }

    public PromptConfig setAlertTextSizeButton(float alertTextSizeButton) {
        this.alertTextSizeButton = alertTextSizeButton;
        return this;
    }

    public int getAlertTextColorTitle() {
        return alertTextColorTitle;
    }

    public PromptConfig setAlertTextColorTitle(int alertTextColorTitle) {
        this.alertTextColorTitle = alertTextColorTitle;
        return this;
    }

    public int getAlertTextColorMessage() {
        return alertTextColorMessage;
    }

    public PromptConfig setAlertTextColorMessage(int alertTextColorMessage) {
        this.alertTextColorMessage = alertTextColorMessage;
        return this;
    }

    public int getAlertTextColorSubmit() {
        return alertTextColorSubmit;
    }

    public PromptConfig setAlertTextColorSubmit(int alertTextColorSubmit) {
        this.alertTextColorSubmit = alertTextColorSubmit;
        return this;
    }

    public int getAlertTextColorCancel() {
        return alertTextColorCancel;
    }

    public PromptConfig setAlertTextColorCancel(int alertTextColorCancel) {
        this.alertTextColorCancel = alertTextColorCancel;
        return this;
    }

    public int getAlertTextColorItem() {
        return alertTextColorItem;
    }


    public int getAlertItemImageSelected() {
        return alertItemImageSelected;
    }

    public PromptConfig setAlertItemImageSelected(int alertItemImageSelected) {
        this.alertItemImageSelected = alertItemImageSelected;
        return this;
    }

    public int getAlertItemImageUnselected() {
        return alertItemImageUnselected;
    }

    public PromptConfig setAlertItemImageUnselected(int alertItemImageUnselected) {
        this.alertItemImageUnselected = alertItemImageUnselected;
        return this;
    }

    public PromptConfig setAlertTextColorItem(int alertTextColorItem) {
        this.alertTextColorItem = alertTextColorItem;
        return this;
    }

    public int getAlertTextGravityTitle() {
        return alertTextGravityTitle;
    }

    public PromptConfig setAlertTextGravityTitle(int alertTextGravityTitle) {
        this.alertTextGravityTitle = alertTextGravityTitle;
        return this;
    }

    public int getAlertTextGravityMessage() {
        return alertTextGravityMessage;
    }

    public PromptConfig setAlertTextGravityMessage(int alertTextGravityMessage) {
        this.alertTextGravityMessage = alertTextGravityMessage;
        return this;
    }

    public int getAlertBackgroundResource() {
        return alertBackgroundResource;
    }

    public PromptConfig setAlertBackgroundResource(int alertBackgroundResource) {
        this.alertBackgroundResource = alertBackgroundResource;
        return this;
    }

    public int getAlertTextColorItemTitle() {
        return alertTextColorItemTitle;
    }

    public PromptConfig setAlertTextColorItemTitle(int alertTextColorItemTitle) {
        this.alertTextColorItemTitle = alertTextColorItemTitle;
        return this;
    }

    public float getTextSizeMessage() {
        return textSizeMessage;
    }

    public PromptConfig setTextSizeMessage(float textSizeMessage) {
        this.textSizeMessage = textSizeMessage;
        return this;
    }

    public float getCircleWidth() {
        return circleWidth;
    }

    public PromptConfig setCircleWidth(float circleWidth) {
        this.circleWidth = circleWidth;
        return this;
    }

    public int getCircleRadius() {
        return circleRadius;
    }

    public PromptConfig setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
        return this;
    }

    public int getBlackStyleTextColor() {
        return blackStyleTextColor;
    }

    public PromptConfig setBlackStyleTextColor(int blackStyleTextColor) {
        this.blackStyleTextColor = blackStyleTextColor;
        return this;
    }

    public int getBlackStyleCircleColor() {
        return blackStyleCircleColor;
    }

    public PromptConfig setBlackStyleCircleColor(int blackStyleCircleColor) {
        this.blackStyleCircleColor = blackStyleCircleColor;
        return this;
    }

    public int getBlackStyleCircleBottomColor() {
        return blackStyleCircleBottomColor;
    }

    public PromptConfig setBlackStyleCircleBottomColor(int blackStyleCircleBottomColor) {
        this.blackStyleCircleBottomColor = blackStyleCircleBottomColor;
        return this;
    }

    public int getBlackStyleBackground() {
        return blackStyleBackground;
    }

    public PromptConfig setBlackStyleBackground(int blackStyleBackground) {
        this.blackStyleBackground = blackStyleBackground;
        return this;
    }

    public int getWhiteStyleTextColor() {
        return whiteStyleTextColor;
    }

    public PromptConfig setWhiteStyleTextColor(int whiteStyleTextColor) {
        this.whiteStyleTextColor = whiteStyleTextColor;
        return this;
    }

    public int getWhiteStyleCircleColor() {
        return whiteStyleCircleColor;
    }

    public PromptConfig setWhiteStyleCircleColor(int whiteStyleCircleColor) {
        this.whiteStyleCircleColor = whiteStyleCircleColor;
        return this;
    }

    public int getWhiteStyleCircleBottomColor() {
        return whiteStyleCircleBottomColor;
    }

    public PromptConfig setWhiteStyleCircleBottomColor(int whiteStyleCircleBottomColor) {
        this.whiteStyleCircleBottomColor = whiteStyleCircleBottomColor;
        return this;
    }

    public int getWhiteStyleBackground() {
        return whiteStyleBackground;
    }

    public PromptConfig setWhiteStyleBackground(int whiteStyleBackground) {
        this.whiteStyleBackground = whiteStyleBackground;
        return this;
    }

    public LoadingFragment.LoadingStyle getLoadingStyle() {
        return loadingStyle;
    }

    public PromptConfig setLoadingStyle(LoadingFragment.LoadingStyle loadingStyle) {
        this.loadingStyle = loadingStyle;
        return this;
    }

    public float getScaleSize() {
        return scaleSize;
    }

    public PromptConfig setScaleSize(float scaleSize) {
        this.scaleSize = scaleSize;
        return this;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public PromptConfig setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        return this;
    }

    public int getBlackStyleScaleColor() {
        return blackStyleScaleColor;
    }

    public PromptConfig setBlackStyleScaleColor(int blackStyleScaleColor) {
        this.blackStyleScaleColor = blackStyleScaleColor;
        return this;
    }

    public int getWhiteStyleScaleColor() {
        return whiteStyleScaleColor;
    }

    public PromptConfig setWhiteStyleScaleColor(int whiteStyleScaleColor) {
        this.whiteStyleScaleColor = whiteStyleScaleColor;
        return this;
    }

    public float getPickerTextSizeTitle() {
        return pickerTextSizeTitle;
    }

    public PromptConfig setPickerTextSizeTitle(float pickerTextSizeTitle) {
        this.pickerTextSizeTitle = pickerTextSizeTitle;
        return this;
    }

    public int getPickerTextColorTitle() {
        return pickerTextColorTitle;
    }

    public PromptConfig setPickerTextColorTitle(int pickerTextColorTitle) {
        this.pickerTextColorTitle = pickerTextColorTitle;
        return this;
    }

    public int getPickerTextColorButton() {
        return pickerTextColorButton;
    }

    public PromptConfig setPickerTextColorButton(int pickerTextColorButton) {
        this.pickerTextColorButton = pickerTextColorButton;
        return this;
    }

    public float getPickerTextSizeButton() {
        return pickerTextSizeButton;
    }

    public PromptConfig setPickerTextSizeButton(float pickerTextSizeButton) {
        this.pickerTextSizeButton = pickerTextSizeButton;
        return this;
    }

    public int getPickerTopBarBackground() {
        return pickerTopBarBackground;
    }

    public PromptConfig setPickerTopBarBackground(int pickerTopBarBackground) {
        this.pickerTopBarBackground = pickerTopBarBackground;
        return this;
    }

    public int getPickerOptionsBackground() {
        return pickerOptionsBackground;
    }

    public PromptConfig setPickerOptionsBackground(int pickerOptionsBackground) {
        this.pickerOptionsBackground = pickerOptionsBackground;
        return this;
    }

    public float getPickerTextSizeCenter() {
        return pickerTextSizeCenter;
    }

    public PromptConfig setPickerTextSizeCenter(float pickerTextSizeCenter) {
        this.pickerTextSizeCenter = pickerTextSizeCenter;
        return this;
    }

    public int getPickerTextColorCenter() {
        return pickerTextColorCenter;
    }

    public PromptConfig setPickerTextColorCenter(int pickerTextColorCenter) {
        this.pickerTextColorCenter = pickerTextColorCenter;
        return this;
    }

    public int getPickerTextColorOUT() {
        return pickerTextColorOUT;
    }

    public PromptConfig setPickerTextColorOUT(int pickerTextColorOUT) {
        this.pickerTextColorOUT = pickerTextColorOUT;
        return this;
    }

    public DividerType getPickerDividerType() {
        return pickerDividerType;
    }

    public PromptConfig setPickerDividerType(DividerType pickerDividerType) {
        this.pickerDividerType = pickerDividerType;
        return this;
    }

    public int getPickerDividerColor() {
        return pickerDividerColor;
    }

    public PromptConfig setPickerDividerColor(int pickerDividerColor) {
        this.pickerDividerColor = pickerDividerColor;
        return this;
    }

    public int getPickerItemNum() {
        return pickerItemNum;
    }

    public PromptConfig setPickerItemNum(int pickerItemNum) {
        this.pickerItemNum = pickerItemNum;
        return this;
    }

    public float getPickerLineSpacingRatio() {
        return pickerLineSpacingRatio;
    }

    public PromptConfig setPickerLineSpacingRatio(@FloatRange(from = 1.0, to = 2.0)float pickerLineSpacingRatio) {
        this.pickerLineSpacingRatio = pickerLineSpacingRatio;
        return this;
    }

    public int getPickerExtraHeight() {
        return pickerExtraHeight;
    }

    public PromptConfig setPickerExtraHeight(int pickerExtraHeight) {
        this.pickerExtraHeight = pickerExtraHeight;
        return this;
    }

    public int getPickerXOffset() {
        return pickerXOffset;
    }

    public PromptConfig setPickerXOffset(int pickerXOffset) {
        this.pickerXOffset = pickerXOffset;
        return this;
    }

    public int getPickerOptionsTextGravity() {
        return pickerOptionsTextGravity;
    }

    public PromptConfig setPickerOptionsTextGravity(int pickerOptionsTextGravity) {
        this.pickerOptionsTextGravity = pickerOptionsTextGravity;
        return this;
    }

    public Calendar getTimeStartCalendar() {
        if (timeStartCalendar == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(1999, 0, 1);
            return calendar;
        }
        return timeStartCalendar;
    }

    public void setTimeStartCalendar(Calendar timeStartCalendar) {
        this.timeStartCalendar = timeStartCalendar;
    }

    public Calendar getTimeEndCalendar() {
        if (timeEndCalendar == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(2099, 11, 31);
            return calendar;
        }
        return timeEndCalendar;
    }

    public void setTimeEndCalendar(Calendar timeEndCalendar) {
        this.timeEndCalendar = timeEndCalendar;
    }
}