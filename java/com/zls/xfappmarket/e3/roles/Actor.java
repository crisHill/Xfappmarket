package com.zls.xfappmarket.e3.roles;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by oop on 2018/2/11.
 */

public abstract class Actor{

    //构造器时赋值
    protected Context context;
    protected View ui;
    protected String tag;
    protected int uiWidth;
    protected int uiHeight;

    protected boolean moving;

    public Actor(Context context, View ui, int uiWidth, int uiHeight){
        this.context = context;
        this.ui = ui;
        this.tag = this.getClass().getSimpleName();
        this.uiWidth = uiWidth;
        this.uiHeight = uiHeight;
    }

    public void reportInfo(String s){
        System.out.println(tag + s + " realX = " + ui.getX() + ", realY = " + ui.getY());
    }

    public void animateMove(long time, float byX, float byY, Animation.AnimationListener animationListener){

        System.out.println(tag + " animateMove, byX = " + byX + ", byY = " + byY + ", realX = " + ui.getX() + ", realY = " + ui.getY());

        if(byX == 0 && byY == 0){
            onPhaseEnd();
            return;
        }

        TranslateAnimation animation = new TranslateAnimation(0, byX, 0, byY);
        animation.setDuration(time);
        animation.setFillAfter(true);
        animation.setAnimationListener(animationListener);
        this.ui.startAnimation(animation);
    }

    public void realMove(float byX, float byY){System.out.println(tag + " realMove, byX = " + byX + ", byY = " + byY + ", realX = " + ui.getX() + ", realY = " + ui.getY());
        ui.clearAnimation();

        ui.setX(ui.getX() + byX);
        ui.setY(ui.getY() + byY);
    }

    protected abstract void init();
    public abstract void onPhaseEnd();
    public abstract void onReset();

}
