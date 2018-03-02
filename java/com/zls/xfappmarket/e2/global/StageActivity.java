package com.zls.xfappmarket.e2.global;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.zls.xfappmarket.R;
import com.zls.xfappmarket.e2.util.Ear;
import com.zls.xfappmarket.e2.util.FlowerManager;
import com.zls.xfappmarket.e2.util.Speaker;

public class StageActivity extends Activity {

    private Context context;
    private FrameLayout root;
    private float touchX;
    private int halfStageWidth;
    private SettingPopUp settingPopUp;
    private VoiceButton voiceButton;

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

                Planner.getINSTANCE(context).onGlobalLayoutFinished(context, root, halfStageWidth, stageHeight);
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
        voiceButton = (VoiceButton) findViewById(R.id.voiceRecorder);

        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(voiceButton.handleTouch(motionEvent)){
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
                planner.startListen();
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


        Planner.getINSTANCE(context).setFlowerManager(new FlowerManager(root, context));
        Ear.getINSTANCE(context).bindVoiceButton(voiceButton);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Ear.getINSTANCE(context).onDestroy();
        Speaker.getINSTANCE(context).onDestroy();

    }
}
