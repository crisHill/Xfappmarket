package com.zls.xfappmarket.e3.roles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by oop on 2018/2/11.
 */

public abstract class Role {

    //构造器时赋值
    protected String tag;
    protected Context context;
    protected int myWidth;
    protected int myHeight;

    protected View ui;

    public Role(Context context, int myWidth, int myHeight){
        this.tag = this.getClass().getSimpleName();
        this.context = context;
        this.myWidth = myWidth;
        this.myHeight = myHeight;

        init();
    }

    protected abstract void init();

    public View getUi(){
        return ui;
    }

}
