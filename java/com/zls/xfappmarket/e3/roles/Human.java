package com.zls.xfappmarket.e3.roles;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.zls.xfappmarket.e3.beans.Location;
import com.zls.xfappmarket.e3.data.Const;
import com.zls.xfappmarket.e3.util.GlbDataHolder;

import java.util.List;

/**
 * Created by oop on 2018/2/11.
 */

public abstract class Human extends Actor {

    protected List<Bitmap> walkRes;
    protected List<Bitmap> turnRes;
    protected Bitmap stillRes;
    protected Bitmap curRes;
    protected Location initLocation;
    protected int walkingBgResIndex = -1;

    public Human(Context context, View ui, int uiWidth, int uiHeight, List<Bitmap> walkRes, List<Bitmap> turnRes, Bitmap stillRes, Location initLocation) {
        super(context, ui, uiWidth, uiHeight);
        this.walkRes = walkRes;
        this.turnRes = turnRes;
        this.stillRes = stillRes;
        this.curRes = stillRes;
        this.initLocation = initLocation;
        init();
    }

    public void changeBg(final int bgChangeIntervalTime) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (GlbDataHolder.isPhaseExecuting()){
                    try {
                        Thread.sleep(bgChangeIntervalTime);
                        handler.sendEmptyMessage(Const.MsgWhat.CHANGE_BG);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    protected Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == Const.MsgWhat.CHANGE_BG){
                doChangeBg();
            }
        }
    };

    protected void doChangeBg() {
        if(walkRes == null){
            return;
        }
        walkingBgResIndex ++;
        if(walkingBgResIndex >= walkRes.size()){
            walkingBgResIndex = 0;
        }
        curRes = walkRes.get(walkingBgResIndex);

        exeChange();
    }

    protected void exeChange(){
        ((ImageView)ui).setImageBitmap(curRes);
    }

    @Override
    public void onPhaseEnd() {
        this.curRes = stillRes;
        exeChange();
        System.out.println(tag + " onPhaseEnd,  realX = " + ui.getX() + ", realY = " + ui.getY());
    }
}
