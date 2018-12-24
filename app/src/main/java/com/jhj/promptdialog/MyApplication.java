package com.jhj.promptdialog;

import android.app.Application;
import android.graphics.Color;

import com.jhj.prompt.fragment.base.PromptConfig;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PromptConfig.getInstance()
                .setAlertTextColorTitle(Color.BLUE)
                .setAlertTextColorItemTitle(Color.GREEN)
                .setBlackStyleScaleColor(Color.RED);
    }
}
