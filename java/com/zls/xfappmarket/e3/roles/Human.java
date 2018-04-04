package com.zls.xfappmarket.e3.roles;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zls.xfappmarket.e3.beans.BgChangeParam;
import com.zls.xfappmarket.e3.itf.BgChangeable;
import com.zls.xfappmarket.e3.util.BgChangeHelper;

import java.util.List;

/**
 * Created by oop on 2018/2/11.
 */

public class Human extends MovableRole implements BgChangeable {

    private BitmapDrawable rawBpRes;
    private float initX, initY;

    private List<BitmapDrawable> walkBpRes, turnBpRes;

    public Human(Context context, int myWidth, int myHeight, BitmapDrawable rawBpRes, float initX, float initY) {
        super(context, myWidth, myHeight);
        this.rawBpRes = rawBpRes;
        this.initX = initX;
        this.initY = initY;
    }

    @Override
    protected void init() {
        ui = new ImageView(context);
        ViewGroup.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.width = myWidth;
        lp.height = myHeight;
        ui.setLayoutParams(lp);
    }

    public void initBgAndLocation(){
        ui.setBackground(rawBpRes);
        ui.setX(initX);
        ui.setY(initY);
    }

    public BitmapDrawable getRawBpRes(){
        return rawBpRes;
    }

    @Override
    public void changeBg(BgChangeParam bgChangeParam, BitmapDrawable endRes) {
        BgChangeHelper.helpChange(ui, bgChangeParam, endRes);
    }



    public List<BitmapDrawable> getWalkBpRes() {
        return walkBpRes;
    }

    public void setWalkBpRes(List<BitmapDrawable> walkBpRes) {
        this.walkBpRes = walkBpRes;
    }

    public List<BitmapDrawable> getTurnBpRes() {
        return turnBpRes;
    }

    public void setTurnBpRes(List<BitmapDrawable> turnBpRes) {
        this.turnBpRes = turnBpRes;
    }


}
