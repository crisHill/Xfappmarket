package com.zls.xfappmarket.e1;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zls.xfappmarket.R;

/**
 * Created by oop on 2018/2/8.
 */

public class ActorStage extends Actor {

    private ImageView stageBg1, stageBg2;

    public ActorStage(LinearLayout stageBg, Context context){
        super.ui = stageBg;
        super.context = context;
        this.stageBg1 = (ImageView) stageBg.findViewById(R.id.stageBg1);
        this.stageBg2 = (ImageView) stageBg.findViewById(R.id.stageBg2);
        super.tag = this.getClass().getSimpleName();
    }



    @Override
    public void init(int halfStageWidth, int stageHeight) {
        super.halfStageWidth = halfStageWidth;
        super.stageHeight = stageHeight;
        super.sleepTime = Const.SLEEP_TIME_STAGE;
        super.uiWidth = halfStageWidth;
        super.uiHeight = stageHeight;

        ViewGroup.LayoutParams lp = super.ui.getLayoutParams();
        lp.width = halfStageWidth * 2;
        ui.setLayoutParams(lp);

        ViewGroup.LayoutParams lp1 = stageBg1.getLayoutParams();
        lp1.width = halfStageWidth;
        stageBg1.setLayoutParams(lp1);

        ViewGroup.LayoutParams lp2 = stageBg2.getLayoutParams();
        lp2.width = halfStageWidth;
        stageBg2.setLayoutParams(lp2);
    }

    @Override
    public void startNextPhase(Informer informer) {
        super.informer =informer;

        int destX = (int) getCurX();
        int destY = (int) getCurY();
        boolean toRight = false;
        boolean toBottom = false;
        int time = Const.TIMES[Var.curPhase];

        if(Var.curPhase == Const.PHASE_START_WALK_2_SCENE2){
            destX = 0 - super.halfStageWidth;
        }

        super.doMove(time, destX, destY, toRight, toBottom);
    }

}
