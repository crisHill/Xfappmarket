package com.zls.xfappmarket.e2.global;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.zls.xfappmarket.e2.data.Const;
import com.zls.xfappmarket.e2.itf.SyntaxListener;
import com.zls.xfappmarket.e2.itf.VoiceMessenger;
import com.zls.xfappmarket.e2.roles.Actor;
import com.zls.xfappmarket.e2.roles.Human;
import com.zls.xfappmarket.e2.roles.Stage;
import com.zls.xfappmarket.e2.util.Ear;
import com.zls.xfappmarket.e2.util.FlowerCreator;
import com.zls.xfappmarket.e2.util.GlbDataHolder;
import com.zls.xfappmarket.e2.util.Speaker;
import com.zls.xfappmarket.e2.util.TextResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by oop on 2018/2/11.
 */

public class Planner {

    private List<Actor> actors = new ArrayList<>();
    private Stage stage;
    private Human boy, girl;
    private int animateStartCount = 0;
    private int animateCompleteCount = 0;

    private Speaker speaker;
    private Ear ear;
    private boolean started = false;

    private ViewGroup root;
    private Context context;
    private FlowerCreator flowerCreator;
    private MediaPlayer mediaPlayer;

    public Planner(ViewGroup root, Context context){
        this.root = root;
        this.context = context;
        flowerCreator = new FlowerCreator(root, context);

    }

    private String[] musics = {
            "music/1947.wav",
            "music/Beatrice La Belle.mp3",
            "music/Enchanted.wav",
            "music/Feeling Lucky.mp3",
            "music/Glory.wav",
            "music/kiss the rain.mp3",
            "music/marryme.wav",
            "music/StrollInThePark.wav",
            "music/Summer Days No Drm.mp3",
            "music/wedding.mp3",
            "music/Wonder.mp3",
    };

    public void startMusic(){
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC); // 获取当前音乐音量
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);// 获取最大声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0); // 设置为最大声音，可通过SeekBar更改音量大小

        AssetFileDescriptor fileDescriptor;
        try {
            String fileName = musics[new Random().nextInt(musics.length)];
            fileDescriptor = context.getAssets().openFd(fileName);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());

            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void endMusic(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
        }
        mediaPlayer = null;
    }

    public void setSpeaker(Speaker speaker){
        this.speaker = speaker;
    }
    public void setEar(Ear ear){
        this.ear = ear;
        ear.setTextResolver(new TextResolver(context, new SyntaxListener() {
            @Override
            public void startFlower() {
                flowerCreator.start();
            }

            @Override
            public void endFlower() {
                flowerCreator.stop();
            }

            @Override
            public void startMusic() {
                startMusic();
            }

            @Override
            public void endMusic() {
                Planner.this.endMusic();
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
                speaker.say(resp, true);
            }

            @Override
            public void onReachGirl(String boyResp, String girlResp) {
                speaker.say(boyResp, true);
                nextSpeech = girlResp;
                boySay = false;
            }

            @Override
            public void noMatch(String resp) {
                speaker.say(resp, true);
            }
        }));

        /*ear.setVoiceMessenger(new VoiceMessenger() {
            @Override
            public void onResult(String text) {

                if(TextResolver.startFlower(text)){
                    flowerCreator.start();
                }else if(TextResolver.endFlower(text)){
                    flowerCreator.stop();
                }else if(TextResolver.go4Xiaoyu(text)){
                    goForXiaoyu();
                }else if(TextResolver.timeToSayWhenMeet(text)){
                    speaker.say(TextResolver.sayWhenMeet(), true);
                    nextSpeech = TextResolver.getGirlSpeech();
                    boySay = false;
                }else if(TextResolver.reachXiaoyu(text)){
                    GlbDataHolder.resetPhaseToToLover();
                    startNextPhase();
                }else if(TextResolver.startMusic(text)){
                    startMusic();
                }else if(TextResolver.endMusic(text)){
                    endMusic();
                }else{
                    String toSay = TextResolver.resp(text);
                    if(toSay.length() > 0){
                        speaker.say(toSay, true);
                    }
                }

            }
        });*/
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

    public void onStart(){
        ear.startRecognize();
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


    private String nextSpeech;
    private boolean boySay = true;
    public void nextSpeech() {
        if(TextUtils.isEmpty(nextSpeech)){
            return;
        }
        speaker.say(nextSpeech, boySay);
        nextSpeech = null;
        boySay = true;
    }
}
