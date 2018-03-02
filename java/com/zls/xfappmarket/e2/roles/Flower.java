package com.zls.xfappmarket.e2.roles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zls.xfappmarket.R;
import com.zls.xfappmarket.e2.data.Const;
import com.zls.xfappmarket.e2.util.GlbDataHolder;

import java.util.Random;

/**
 * Created by oop on 2018/2/12.
 */

public class Flower {

    private int[] res = {R.mipmap.snowflake1, R.mipmap.snowflake2, R.mipmap.snowflake3, R.mipmap.snowflake4, R.mipmap.snowflake5, R.mipmap.snowflake6};

    private Random random;
    private Context context;
    private ViewGroup container;
    private int sleepTime;
    private ImageView ui;
    private boolean moveRight;

    public Flower(Context context, ViewGroup container){
        this.random = new Random();
        this.context = context;
        this.container = container;
        this.sleepTime = (random.nextInt(4) + 1) * 20 + 100;

        Bitmap bg = BitmapFactory.decodeResource(context.getResources(), res[random.nextInt(res.length)]);
        int width = bg.getWidth();
        int height = bg.getHeight();
        ui = new ImageView(context);
        ui.setImageBitmap(bg);
        ui.setAlpha((float) (((random.nextInt(80) + 10) * 1.0) / 100));
        ui.setX(random.nextInt(GlbDataHolder.halfStageWidth - 1 - width) + 1);
        ui.setY(0);
        ui.setLayoutParams(new LinearLayout.LayoutParams(width, height));

        moveRight = random.nextBoolean();

        container.addView(ui);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == Const.MsgWhat.FLOWER_MOVE){
                ((Flower)msg.obj).moveMe();
            }else if(msg.what == Const.MsgWhat.CLEAR_FLOWER){
                ((Flower)msg.obj).clearMe();
            }
        }
    };

    public void moveMe(){
        int index = random.nextInt(Const.FLOWER_DIRECTION_CHANGE);
        if(index == 0){//X分之一的概率改变方向
            moveRight = !moveRight;
        }
        if(ui.getX() <= 1){
            moveRight = true;
        }else if(ui.getX() + ui.getWidth() >= GlbDataHolder.halfStageWidth - 1){
            moveRight = false;
        }

        int stepX = moveRight ? Const.FLOWER_STEP_X : 0 - Const.FLOWER_STEP_X;
        int stepY = Const.FLOWER_STEP_Y;
        ui.setX(ui.getX() + stepX);
        ui.setY(ui.getY() + stepY);

        ui.setPivotX(ui.getWidth()/2);
        ui.setPivotY(ui.getHeight()/2);//支点在图片中心
        ui.setRotation(ui.getRotation() + Const.FLOWER_PIVOT_ANGLE);
    }

    public void clearMe(){
        container.removeView(ui);
    }

    private boolean moveable(){
        return ui.getY() < GlbDataHolder.stageHeight;
    }

    public void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (moveable()){
                    try {
                        Thread.sleep(sleepTime);
                        Message msg = Message.obtain();
                        msg.what = Const.MsgWhat.FLOWER_MOVE;
                        msg.obj = Flower.this;
                        handler.sendMessage(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message msg = Message.obtain();
                msg.what = Const.MsgWhat.CLEAR_FLOWER;
                msg.obj = Flower.this;
                handler.sendMessage(msg);
            }
        }).start();
    }



}
