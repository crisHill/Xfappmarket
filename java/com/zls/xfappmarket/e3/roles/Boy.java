package com.zls.xfappmarket.e3.roles;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;

import com.zls.xfappmarket.e3.beans.Location;

import java.util.List;

/**
 * Created by oop on 2018/2/11.
 */

public class Boy extends Human {

    public Boy(Context context, View ui, int uiWidth, int uiHeight, List<Bitmap> walkRes, List<Bitmap> turnRes, Bitmap stillRes, Location initLocation) {
        super(context, ui, uiWidth, uiHeight, walkRes, turnRes, stillRes, initLocation);
    }

    @Override
    protected void init() {
        //初始化长宽
        ViewGroup.LayoutParams lp = ui.getLayoutParams();
        lp.width = uiWidth;
        lp.height = uiHeight;
        ui.setLayoutParams(lp);

        onReset();
    }

    @Override
    public void onReset() {
        //初始化位置
        ui.setX(initLocation.getX());
        ui.setY(initLocation.getY());

        //初始化背景
        exeChange();
    }
}
