package com.zls.xfappmarket.e3.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.zls.xfappmarket.e3.beans.MoveParam;

import java.util.List;

/**
 * Created by oop on 2018/3/6.
 */

public class MoveAnimHelper {

    public static void helpAnimateMove(View role, List<MoveParam> moveParamList){
        move(role, moveParamList, 0);
    }

    private static void move(final View role, final List<MoveParam> moveParamList, final int curIndex){
        if(curIndex >= moveParamList.size()){
            //role.clearAnimation();

            /*MoveParam total = CommonUtil.getTotal(moveParamList);
            role.setX(role.getX() + total.getDx());
            role.setY(role.getY() + total.getDy());*/

            return;
        }

        MoveParam param = moveParamList.get(curIndex);
        final float dx = param.getDx();
        final float dy = param.getDy();
        if(dx == 0 && dy == 0){
            move(role, moveParamList, curIndex + 1);
            return;
        }

        TranslateAnimation animation = new TranslateAnimation(0, dx, 0, dy);
        animation.setDuration(param.getTime());
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                role.clearAnimation();
                role.setX(role.getX() + dx);
                role.setY(role.getY() + dy);
                move(role, moveParamList, curIndex + 1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        role.startAnimation(animation);

    }

}
