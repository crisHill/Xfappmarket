package com.zls.xfappmarket.e1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zls.xfappmarket.R;

public class StageActivity extends Activity {

    private int halfStageWidth, stageHeight;

    private FrameLayout root;
    private Button btnStart;

    private director director;

    @Override
    protected void onStart() {
        super.onStart();
        ViewTreeObserver observer = root.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                halfStageWidth = root.getMeasuredWidth();
                stageHeight = root.getMeasuredHeight();

                director.onGlobalLayoutFinished(halfStageWidth, stageHeight);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);



        root = (FrameLayout) findViewById(R.id.root);

        director = new director();

        ImageView boyUI = (ImageView) findViewById(R.id.boy);
        ActorBoy boy = new ActorBoy(boyUI, this);
        director.registerActor(boy);

        ImageView girlUI = (ImageView) findViewById(R.id.girl);
        ActorGirl girl = new ActorGirl(girlUI, this);
        director.registerActor(girl);

        LinearLayout stageBg = (LinearLayout) findViewById(R.id.stageBg);
        ActorStage stage = new ActorStage(stageBg, this);
        director.registerActor(stage);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Var.curPhase == -1){
                    director.startNextPhase();
                }
            }
        });

    }

}
