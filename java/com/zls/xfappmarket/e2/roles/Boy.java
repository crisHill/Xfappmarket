package com.zls.xfappmarket.e2.roles;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zls.xfappmarket.e2.beans.Location;
import com.zls.xfappmarket.e2.data.Const;
import com.zls.xfappmarket.e2.util.GlbDataHolder;

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
