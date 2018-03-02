package com.zls.xfappmarket.e2.global;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.zls.xfappmarket.R;
import com.zls.xfappmarket.e2.itf.VoiceDetectListener;
import com.zls.xfappmarket.e2.util.Ear;
import com.zls.xfappmarket.e2.util.GlbDataHolder;
import com.zls.xfappmarket.e2.util.Speaker;

public class StageActivity extends Activity {

    private Context context;
    private FrameLayout root;
    //private Button btnStart, btnReset;

    private Planner planner;
    private Speaker speaker;
    private Ear ear;
    private float touchX;
    private int halfStageWidth;
    private SettingPopUp settingPopUp;
    private VoiceRecorder voiceRecorder;

    @Override
    protected void onStart() {
        super.onStart();
        ViewTreeObserver observer = root.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                halfStageWidth = root.getMeasuredWidth();
                int stageHeight = root.getMeasuredHeight();

                planner.onGlobalLayoutFinished(context, root, halfStageWidth, stageHeight);
                settingPopUp = new SettingPopUp(context, halfStageWidth, root);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_e2);

        context = StageActivity.this;
        root = (FrameLayout) findViewById(R.id.root);
        planner = new Planner(root, context);
        voiceRecorder = (VoiceRecorder) findViewById(R.id.voiceRecorder);
        voiceRecorder.setPlanner(planner);
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(voiceRecorder.handleTouch(motionEvent)){
                    return true;
                }

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    touchX = motionEvent.getX();
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    float distance = motionEvent.getX() - touchX;
                    float moveLimit = halfStageWidth / 10;
                    if(distance > moveLimit){
                        settingPopUp.show();
                        return true;
                    }else if(distance < 0 - moveLimit){
                        return true;
                    }
                }
                return true;
            }
        });

        /*btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planner.onStart();
            }
        });
        btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planner.onReset();
                GlbDataHolder.onReset();
            }
        });*/


        InitListener mTtsInitListener = new InitListener() {
            @Override
            public void onInit(int code) {
                System.out.println(this.getClass().toString() + ", InitListener init() code = " + code);
            }
        };
        mTts = SpeechSynthesizer.createSynthesizer(this, mTtsInitListener);;
        speaker = new Speaker(context, mTts, mTtsInitListener, new SynthesizerListener() {

            @Override
            public void onSpeakBegin() {
                //showTip("开始播放");
            }

            @Override
            public void onSpeakPaused() {
                //showTip("暂停播放");
            }

            @Override
            public void onSpeakResumed() {
                //showTip("继续播放");
            }

            @Override
            public void onBufferProgress(int percent, int beginPos, int endPos,
                                         String info) {
                // 合成进度
                //mPercentForBuffering = percent;
            }

            @Override
            public void onSpeakProgress(int percent, int beginPos, int endPos) {
                // 播放进度
                //mPercentForPlaying = percent;
            }

            @Override
            public void onCompleted(SpeechError error) {
                if (error == null) {
                    //showTip("播放完成");
                    planner.nextSpeech();
                } else if (error != null) {
                    //showTip(error.getPlainDescription(true));
                }
            }

            @Override
            public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            }
        });
        planner.setSpeaker(speaker);


        InitListener mInitListener = new InitListener() {

            @Override
            public void onInit(int code) {
                System.out.println(this.getClass().toString() + " SpeechRecognizer init() code = " + code);
                if (code != ErrorCode.SUCCESS) {
                    System.out.println("初始化失败，错误码：" + code);
                }
            }
        };

        mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
        ear = new Ear(context, mIat, mInitListener, new VoiceDetectListener() {
            @Override
            public void onSpeechEnd() {
                voiceRecorder.turn(false);
            }
        });
        planner.setEar(ear);

    }

    private SpeechSynthesizer mTts;
    private SpeechRecognizer mIat;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if( null != mTts ){
            // 退出时释放连接
            mTts.stopSpeaking();
            mTts.destroy();
        }

        if( null != mIat ){
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }

    }
}
