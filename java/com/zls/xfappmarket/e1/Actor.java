package com.zls.xfappmarket.e1;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;

/**
 * Created by oop on 2018/2/8.
 */

public abstract class Actor {

    //构造器时赋值
    protected Context context;
    protected View ui;
    protected String tag;

    //需要在初始化的时候给以下变量赋值!!!
    protected int halfStageWidth;
    protected int stageHeight;
    protected int sleepTime;
    protected int uiWidth;
    protected int uiHeight;

    //每次 doMove 时赋值
    protected int timeInMillis;
    protected int destX, destY;
    protected boolean toRight, toBottom;
    protected float stepX;
    protected float stepY;

    protected Informer informer;


    public abstract void init(int halfStageWidth, int stageHeight);
    public abstract void startNextPhase(Informer informer);
    public void react2PhaseDone(){}

    private class ActionThread extends Thread{
        private int what;
        private long curTime = 0;
        public ActionThread(int what){
            this.what = what;
        }
        @Override
        public void run() {
            while (!arrivedDest()){
                try {
                    long extraCost = 0;
                    if(curTime > 0){
                        extraCost = System.currentTimeMillis() - curTime - sleepTime;
                        System.out.println(Actor.this.tag +  " extraCost = " + extraCost);
                    }
                    if(extraCost < 0){
                        extraCost = 0;
                    }
                    curTime = System.currentTimeMillis();
                    Thread.sleep(sleepTime - extraCost);

                    handler.sendEmptyMessage(this.what);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final boolean arrivedDest(){
        float curX = ui.getX();
        float curY = ui.getY();

        boolean xArrived = toRight ? curX >= destX : curX <= destX;
        boolean yArrived = toBottom ? curY >= destY : curY <= destY;

        boolean result = xArrived && yArrived;
        if(result){
            System.out.println(this.tag +  " arrivedDest: curPhase = " + Var.curPhase);
            handler.sendEmptyMessage(Const.WHAT_PHASE_DONE);
        }

        return result;
    }

    public final void doMove(int timeInMillis, int destX, int destY, boolean toRight, boolean toBottom){

        System.out.println(this.tag +  " doMove: destX = " + destX + ", destY = " + destY + ", toRight = " + toRight + ", toBottom = " + toBottom);

        this.timeInMillis = timeInMillis;
        this.destX = destX;
        this.destY = destY;
        this.toRight = toRight;
        this.toBottom = toBottom;

        int count = timeInMillis / sleepTime;
        float distanceX = Math.abs(destX - getCurX());
        stepX = processFloat(distanceX / count);
        float distanceY = Math.abs(destY - getCurY());
        stepY = processFloat(distanceY / count);

        if(arrivedDest()){
            return;
        }else {
            new ActionThread(Const.WHAT_PHASE_EXECUTING[Var.curPhase]).start();
        }
    }

    protected Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what >= Const.WHAT_PHASE_EXECUTING[0] && msg.what <= Const.WHAT_PHASE_EXECUTING[Const.WHAT_PHASE_EXECUTING.length - 1]){
                updateBg();
                updateLocation();
            }else if(msg.what == Const.WHAT_PHASE_DONE){
                if(informer != null){
                    informer.onPhaseDone();
                    Actor.this.react2PhaseDone();
                }
            }
        }
    };

    private void updateLocation() {

        float curX = getCurX();
        float aimX;
        if(toRight){
            aimX = curX + stepX <= destX ? curX + stepX : destX;
        }else {
            aimX = curX - stepX >= destX ? curX - stepX : destX;
        }

        float curY = getCurY();
        float aimY;
        if(toBottom){
            aimY = curY + stepY <= destY ? curY + stepY : destY;
        }else {
            aimY = curY - stepY >= destY ? curY - stepY : destY;
        }

        System.out.println(this.tag +  " updateLocation: from (" + curX + ", " + curY + ") to (" + aimX + ", " + aimY + ")");
        executeMove(aimX, aimY);
    }

    protected float getCurX(){
        return processFloat(ui.getX());
    }
    protected float getCurY(){
        return processFloat(ui.getY());
    }
    private float processFloat(float num){
        return (float) (((int)(num * 10000)) * 1.0 / 10000);
    }
    protected void executeMove(float aimX, float aimY){
        ui.setX(aimX);
        ui.setY(aimY);
    }


    protected void updateBg(){}

}
