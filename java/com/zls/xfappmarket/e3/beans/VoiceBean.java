package com.zls.xfappmarket.e3.beans;

import android.text.TextUtils;

/**
 * Created by oop on 2018/3/2.
 */

public class VoiceBean {

    public static VoiceBean create(String str, boolean isBoy){
        return new VoiceBean(str, isBoy);
    }

    private String str;
    private boolean isBoy;

    private VoiceBean(String str, boolean isBoy){
        this.str = str;
        this.isBoy = isBoy;
    }

    public String getStr() {
        if(TextUtils.isEmpty(str)){
            return "科大讯飞，让世界聆听我们的声音";
        }
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public boolean isBoy() {
        return isBoy;
    }

    public void setBoy(boolean boy) {
        isBoy = boy;
    }
}
