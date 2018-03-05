package com.zls.xfappmarket.e3.roles;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RelativeLayout;

import com.zls.xfappmarket.e3.beans.Location;
import com.zls.xfappmarket.e3.data.Const;
import com.zls.xfappmarket.e3.util.GlbDataHolder;

import java.util.List;

/**
 * Created by oop on 2018/2/11.
 */

public class Girl extends Human {

    public Girl(Context context, View ui, int uiWidth, int uiHeight, List<Bitmap> walkRes, List<Bitmap> turnRes, Bitmap stillRes, Location initLocation) {
        super(context, ui, uiWidth, uiHeight, walkRes, turnRes, stillRes, initLocation);
    }

    @Override
    protected void init() {
        //初始化长宽 位置
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ui.getLayoutParams();
        lp.width = uiWidth;
        lp.height = uiHeight;
        lp.setMargins(GlbDataHolder.halfStageWidth / 2, (int) ((Const.HUMAN_INIT_TO_TOP_RATIO - Const.BRIDGE_HEIGHT_RATIO) * GlbDataHolder.stageHeight - uiHeight), 0, 0);
        ui.setLayoutParams(lp);
        //初始化背景
        exeChange();
    }

    @Override
    public void onReset() {

    }
}
