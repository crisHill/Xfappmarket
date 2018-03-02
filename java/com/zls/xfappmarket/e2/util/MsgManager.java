package com.zls.xfappmarket.e2.util;

import com.zls.xfappmarket.e2.itf.MsgReceiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oop on 2018/3/2.
 */

public class MsgManager {

    private static MsgManager INSTANCE;
    public static MsgManager getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new MsgManager();
        }
        return INSTANCE;
    }

    public static class Type{
        public static final int SHOW_SETTING = 1;
        public static final int START_OR_END_FLOWER = 2;
        public static final int START_OR_END_MUSIC = 3;
        public static final int HIDE_OR_SHOW_VOICE_BUTTON = 4;
        public static final int ASK_WHAT_TO_DO = 5;
        public static final int ASK_TO_FIND_GIRL = 6;
        public static final int ASK_TO_REACH_GIRL = 7;
        public static final int ASK_WHAT_TO_SAY = 8;
        public static final int NO_MATCH = 9;
        public static final int TURN_ON_OR_OFF_VOICE_BUTTON = 10;
    }

    private Map<Integer, List<MsgReceiver>> datas;

    private MsgManager(){
        datas = new HashMap<>();
    }

    public void inform(int type, Object obj){
        List<MsgReceiver> receivers = datas.get(type);
        if(receivers == null || receivers.size() == 0){
            return;
        }
        for (MsgReceiver receiver : receivers){
            receiver.onReceive(type, obj);
        }
    }

    public void register(int type, MsgReceiver receiver){
        List<MsgReceiver> receivers = datas.get(type);
        if(receivers == null){
            receivers = new ArrayList<>();
            datas.put(type, receivers);
        }
        if(receivers.contains(receiver)){
            return;
        }
        receivers.add(receiver);
    }

    public void unRegister(int type, Object receiver){
        List<MsgReceiver> receivers = datas.get(type);
        if(receivers == null){
            return;
        }
        if(!receivers.contains(receiver)){
            return;
        }
        receivers.remove(receiver);
    }

}
