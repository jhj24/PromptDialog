package com.jhj.promptdialog;

import android.app.Application;
import android.graphics.Color;

import com.jhj.prompt.fragment.base.PromptConfig;

import java.util.Calendar;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(2010,10,10);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(2018,11,12);

        PromptConfig.getInstance()
                .setAlertTextColorTitle(Color.BLUE)
                .setAlertTextColorItemTitle(Color.GREEN)
                .setBlackStyleScaleColor(Color.RED);


    }
}
