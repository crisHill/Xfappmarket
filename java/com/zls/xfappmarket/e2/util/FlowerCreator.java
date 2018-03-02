package com.zls.xfappmarket.e2.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;

import com.zls.xfappmarket.e2.data.Const;
import com.zls.xfappmarket.e2.roles.Flower;

/**
 * Created by oop on 2018/2/12.
 */

public class FlowerCreator {

    private static boolean keep_create = false;

    private ViewGroup root;
    private Context context;

    public FlowerCreator(ViewGroup root, Context context){
        this.root = root;
        this.context = context;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == Const.MsgWhat.CREATE_FLOWER){
                create();
            }
        }
    };
    private void create(){
        new Flower(context, root).start();
    }
    public void stop(){
        keep_create = false;
    }
    public void start(){
        keep_create = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (keep_create){
                    try {
                        Thread.sleep(Const.FLOWER_CREATE_TIME);
                        handler.sendEmptyMessage(Const.MsgWhat.CREATE_FLOWER);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
