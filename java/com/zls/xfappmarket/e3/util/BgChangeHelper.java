package com.zls.xfappmarket.e3.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.zls.xfappmarket.e3.beans.BgChangeParam;
import com.zls.xfappmarket.e3.beans.MoveParam;

import java.util.List;

/**
 * Created by oop on 2018/3/6.
 */

public class BgChangeHelper {

    public static void helpChange(View role, BgChangeParam bgChangeParam, BitmapDrawable endBpRes){
        change(role, bgChangeParam.getTime(), 0, bgChangeParam.getRefreshGapTime(), bgChangeParam.getTempResList(), -1, endBpRes);
    }

    private static void change(final View role, final long totalTime, final long alreadyCostTime, final long sleepTime, final List<BitmapDrawable> tempResList, final int curIndex, final BitmapDrawable endBpRes){
        if(alreadyCostTime >= totalTime){
            role.setBackground(endBpRes);
            return;
        }

        role.postDelayed(new Runnable() {
            @Override
            public void run() {
                int newIndex = curIndex + 1;
                if(newIndex >= tempResList.size()){
                    newIndex = 0;
                }
                long newAlreadyCostTime = alreadyCostTime + sleepTime;
                role.setBackground(tempResList.get(newIndex));
                change(role, totalTime, newAlreadyCostTime, sleepTime, tempResList, newIndex, endBpRes);
            }
        }, sleepTime);

    }

}
