package com.zls.xfappmarket.e1;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * Created by oop on 2018/2/8.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        SpeechUtility.createUtility(this.getApplicationContext(), "appid=570f44b8, usr=hzauzls@126.com, pwd=criszls123");
    }
}
