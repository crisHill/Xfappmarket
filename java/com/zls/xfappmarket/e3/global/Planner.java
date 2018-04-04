package com.zls.xfappmarket.e3.global;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zls.xfappmarket.e3.beans.MoveParam;
import com.zls.xfappmarket.e3.beans.VoiceBean;
import com.zls.xfappmarket.e3.data.Const;
import com.zls.xfappmarket.e3.itf.MsgReceiver;
import com.zls.xfappmarket.e3.roles.Role;
import com.zls.xfappmarket.e3.roles.Human;
import com.zls.xfappmarket.e3.roles.Stage;
import com.zls.xfappmarket.e3.util.CommonUtil;
import com.zls.xfappmarket.e3.util.GlbDataHolder;
import com.zls.xfappmarket.e3.util.MsgManager;
import com.zls.xfappmarket.e3.util.MusicManager;
import com.zls.xfappmarket.e3.util.Speaker;
import com.zls.xfappmarket.e3.util.TextResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oop on 2018/2/11.
 */

public class Planner implements MsgReceiver{

    private static Planner instance;
    public static Planner getIInstance(Context context){
        if(instance == null){
            instance = new Planner(context);
        }
        return instance;
    }

    private Context context;
    private ViewGroup root;
    private Stage stage;
    private Human boy, girl;

    private Planner(Context context){
        this.context = context;

        MsgManager.getINSTANCE().register(MsgManager.Type.ASK_WHAT_TO_DO, this);
        MsgManager.getINSTANCE().register(MsgManager.Type.ASK_TO_FIND_GIRL, this);
        MsgManager.getINSTANCE().register(MsgManager.Type.ASK_TO_REACH_GIRL, this);
        MsgManager.getINSTANCE().register(MsgManager.Type.ASK_WHAT_TO_SAY, this);
        MsgManager.getINSTANCE().register(MsgManager.Type.NO_MATCH, this);
    }

    public void init(ViewGroup root) {
        this.root = root;

        stage = CommonUtil.createStage(context);
        root.addView(stage.getUi(), 0);

        boy = CommonUtil.createBoy(context);
        root.addView(boy.getUi(), 1);

        girl = CommonUtil.createGirl(context);
        ViewGroup.LayoutParams lp = girl.getUi().getLayoutParams();
        RelativeLayout.LayoutParams newLp = new RelativeLayout.LayoutParams(lp.width, lp.height);
        //newLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        girl.getUi().setLayoutParams(newLp);
        ((ViewGroup)stage.getUi()).addView(girl.getUi());
        //root.addView(girl.getUi(), 2);
    }

    @Override
    public void onReceive(int type, Object obj) {

        if(type == MsgManager.Type.ASK_WHAT_TO_DO){
            String str = TextResolver.answerWhatToDo(context);
            Speaker.getINSTANCE(context).say(VoiceBean.create(str, true));
            return;
        }

        if(type == MsgManager.Type.ASK_TO_FIND_GIRL){
            roleAct(Const.Phase.WALK_2_SCENE2, Const.Phase.WALK_2_SCENE2);
            return;
        }

        if(type == MsgManager.Type.ASK_TO_REACH_GIRL){
            roleAct(Const.Phase.WALK_2_BRIDGE, Const.Phase.WALK_2_BRIDGE);
            return;
        }

        if(type == MsgManager.Type.ASK_WHAT_TO_SAY){
            String boyStr = TextResolver.boyAnswerReachGirl(context);
            String girlStr = TextResolver.girlAnswerReachGirl(context);
            Speaker.getINSTANCE(context).say(VoiceBean.create(boyStr, true), VoiceBean.create(girlStr, false));
            return;
        }

        if(type == MsgManager.Type.NO_MATCH){
            String str = TextResolver.answerNoMatch(context);
            Speaker.getINSTANCE(context).say(VoiceBean.create(str, true));
            return;
        }

    }

    private void roleAct(int fromPhase, int toPhase){
        if(fromPhase > toPhase){
            return;
        }
        if(fromPhase < 0){
            return;
        }
        stage.move(CommonUtil.getMoveParamList(Const.RoleType.STAGE, fromPhase, toPhase));
        girl.move(CommonUtil.getMoveParamList(Const.RoleType.GIRL, fromPhase, toPhase));
        boy.move(CommonUtil.getMoveParamList(Const.RoleType.BOY, fromPhase, toPhase));
        //boy.changeBg(CommonUtil.getBgChangeParam(boy.getWalkBpRes(), fromPhase, toPhase), boy.getRawBpRes());
    }

}
