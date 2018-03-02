package com.zls.xfappmarket.e2.global;

import android.print.PageRange;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

/**
 * Created by oop on 2018/2/27.
 */

public class MyMenu{

    private final double RATIO = 0.3;
    private final long DURATION = 1000;

    private boolean displaying = false;
    private boolean toggling = false;
    private int width;
    private int height;

    private View ui;
    private float closeX;
    private float openX;

    public MyMenu(View ui){
        this.ui = ui;
    }

    public void toggle(boolean show){
        if(toggling){
            return ;
        }
        if(displaying == show){
            return ;
        }
        displaying = show;
        startToggle();
        return;
    }

    private void startToggle(){
        toggling = true;

        float toXDelta = displaying ? this.width : 0 - this.width;
        TranslateAnimation animation = new TranslateAnimation(0, toXDelta, 0, 0);
        animation.setDuration(DURATION);
        animation.setFillAfter(true);
        animation.setAnimationListener(animationListener);
        this.ui.startAnimation(animation);
    }

    private void endToggle(){
        int left = ui.getLeft() + (displaying ? this.width : 0 - this.width);
        int top = ui.getTop();
        ui.clearAnimation();
        relocate(left, top);

        /*ui.clearAnimation();
        float x = displaying ? openX: closeX;
        ui.setX(x);*/

        toggling = false;
    }

    private void relocate(int left, int top){
        ui.layout(left, top, left+width, top+height);
    }

    public void onGlobalLayoutFinished(int halfStageWidth, int stageHeight) {

        this.width = (int) (halfStageWidth * RATIO);
        this.height = stageHeight;

        ViewGroup.LayoutParams lp = ui.getLayoutParams();
        lp.width = this.width;
        lp.height = this.height;
        ui.setLayoutParams(lp);

        float init = this.width;
        //float init = 0;
        //float init = this.width / 2;
        closeX = init - this.width;
        openX = init;
        //ui.setX(closeX);
        relocate((int) closeX, 0);
    }

    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            endToggle();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
}
