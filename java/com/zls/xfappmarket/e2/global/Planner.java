package com.zls.xfappmarket.e2.global;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.zls.xfappmarket.e2.data.Const;
import com.zls.xfappmarket.e2.data.VoiceBean;
import com.zls.xfappmarket.e2.itf.SyntaxListener;
import com.zls.xfappmarket.e2.roles.Actor;
import com.zls.xfappmarket.e2.roles.Human;
import com.zls.xfappmarket.e2.roles.Stage;
import com.zls.xfappmarket.e2.util.Ear;
import com.zls.xfappmarket.e2.util.FlowerManager;
import com.zls.xfappmarket.e2.util.GlbDataHolder;
import com.zls.xfappmarket.e2.util.MusicManager;
import com.zls.xfappmarket.e2.util.Speaker;
import com.zls.xfappmarket.e2.util.TextResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oop on 2018/2/11.
 */

public class Planner {

    private static Planner INSTANCE;
    public static Planner getINSTANCE(Context context){
        if(INSTANCE == null){
            INSTANCE = new Planner(context);
        }
        return INSTANCE;
    }

    private List<Actor> actors = new ArrayList<>();
    private Stage stage;
    private Human boy, girl;
    private int animateStartCount = 0;
    private int animateCompleteCount = 0;
    private boolean started = false;
    private Context context;
    private FlowerManager flowerManager;
    private TextResolver textResolver;

    private Planner(final Context context){
        this.context = context;
        this.textResolver = new TextResolver(context, new SyntaxListener() {
            @Override
            public void startFlower() {
                flowerManager.start();
            }

            @Override
            public void endFlower() {
                flowerManager.stop();
            }

            @Override
            public void startMusic() {
                MusicManager.getINSTANCE(context).start();
            }

            @Override
            public void endMusic() {
                MusicManager.getINSTANCE(context).end();
            }

            @Override
            public void toFindGirl() {
                goForXiaoyu();
            }

            @Override
            public void toReachGirl() {
                GlbDataHolder.resetPhaseToToLover();
                startNextPhase();
            }

            @Override
            public void respWhatToDo(String resp) {
                Speaker.getINSTANCE(context).say(VoiceBean.create(resp, true));
            }

            @Override
            public void onReachGirl(String boyResp, String girlResp) {
                Speaker.getINSTANCE(context).say(VoiceBean.create(boyResp, true), VoiceBean.create(girlResp, false));
            }

            @Override
            public void noMatch(String resp) {
                Speaker.getINSTANCE(context).say(VoiceBean.create(resp, true));
            }
        });
    }

    public void setFlowerManager(FlowerManager flowerManager){
        this.flowerManager = flowerManager;
    }

    public void onRecognizeVoice(String text){
        if(textResolver != null && !TextUtils.isEmpty(text)){
            textResolver.resolve(text);
        }
    }

    private void goForXiaoyu(){
        if(started){
            return;
        }
        setStarted(true);
        startNextPhase();
    }

    private void setStarted(boolean started){
        this.started = started;
    }

    public void onGlobalLayoutFinished(Context context, ViewGroup root, int halfStageWidth, int stageHeight) {
        GlbDataHolder.halfStageWidth = halfStageWidth;
        GlbDataHolder.stageHeight = stageHeight;

        this.stage = ActorCreator.createStage(context, root);
        this.boy = ActorCreator.createBoy(context, root);
        this.girl = ActorCreator.createGirl(context, root);

        actors.add(stage);
        actors.add(girl);
        actors.add(boy);

    }

    public void onReset(){
        setStarted(false);
        for (Actor actor : actors){
            actor.onReset();
        }
    }

    public void startListen(){
        Ear.getINSTANCE(context).startRecognize();
    }

    private void startNextPhase(){
        if(!GlbDataHolder.hasNextPhase()){
            return;
        }
        if(GlbDataHolder.isPhaseExecuting()){
            return;
        }
        GlbDataHolder.nextPhase();

        animateStartCount = 0;
        animateCompleteCount = 0;

        int phase = GlbDataHolder.getPhase();
        long time = Const.PHASE_TIME[phase];

        final int size = actors.size();
        for (int i = 0; i < size; i++) {
            final Actor actor = actors.get(i);
            final float byX = Const.MoveBy.X[i][phase] * GlbDataHolder.halfStageWidth;
            final float byY = 0 - Const.MoveBy.Y[i][phase] * GlbDataHolder.stageHeight;

            Animation.AnimationListener listener = new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {actor.reportInfo("onAnimationStart");
                    animateStartCount ++;
                }

                @Override
                public void onAnimationEnd(Animation animation) {actor.reportInfo("onAnimationEnd");
                    actor.realMove(byX, byY);

                    animateCompleteCount ++;
                    callEnd(actor, animateStartCount == animateCompleteCount && animateStartCount > 0);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            };


            callStart(actor, time, byX, byY, listener);
        }

    }

    private void callEnd(Actor actor, boolean startNext){

        actor.onPhaseEnd();
        GlbDataHolder.endPhase();

        if(startNext){

            /*if(GlbDataHolder.isPhaseLast()){
                if(speaker != null && GlbDataHolder.reachedGirl()){
                    speaker.say(TextResolver.getEndSpeech(), true);
                }
            }*/

            startNextPhase();

        }
    }

    private void callStart(Actor actor, long time, float byX, float byY, Animation.AnimationListener animationListener){
        GlbDataHolder.startPhase();
        callActorMove(actor, time, byX, byY, animationListener);
        if(actor instanceof Human){
            callHumanChangeBg((Human)actor);
        }
    }
    private void callActorMove(Actor actor, Long time, float byX, float byY, Animation.AnimationListener animationListener){
        actor.animateMove(time, byX, byY, animationListener);
    }
    private void callHumanChangeBg(Human human){
        human.changeBg(Const.BG_CHANGE_INTERVAL_TIME[GlbDataHolder.getPhase()]);
    }




}
