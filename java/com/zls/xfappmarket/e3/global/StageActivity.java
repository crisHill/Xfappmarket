package com.zls.xfappmarket.e3.global;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.zls.xfappmarket.R;
import com.zls.xfappmarket.e3.util.GlbDataHolder;

public class StageActivity extends Activity{

    private Context context;
    private ViewGroup root;
    private VoiceButton voiceButton;
    private float touchX;
    private SettingPopUp popup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_e3);
        context = StageActivity.this;
        root = (ViewGroup) findViewById(R.id.root);
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
                    float moveLimit = GlbDataHolder.halfStageWidth / 10;
                    if(distance > moveLimit){
                        popup.show();
                        return true;
                    }else if(distance < 0 - moveLimit){
                        return true;
                    }
                }
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        ViewTreeObserver observer = root.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                root.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                GlbDataHolder.halfStageWidth = root.getMeasuredWidth();
                GlbDataHolder.stageHeight = root.getMeasuredHeight();

                Planner.getIInstance(context).init(root);

                popup = SettingPopUp.generate(context, root);

            }
        });
    }

}
