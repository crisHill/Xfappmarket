package com.zls.xfappmarket.e1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zls.xfappmarket.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oop on 2018/2/8.
 */

public class ActorGirl extends Actor {

     /*
    加载到bitmap 1583 * 873
    原始图片 1055 * 582
    */
    public static final int RAW_WIDTH = 1055;
    public static final int RAW_HEIGHT = 582;
    public static final int RAW_GIRL_WIDTH = 151;
    public static final int RAW_GIRL_HEIGHT = 291;

    private List<Bitmap> walkingRes = new ArrayList<>();
    private Bitmap standRes;
    private Bitmap curRes;
    private double ratio;
    private int walkResIndex = 0;

    public ActorGirl(ImageView ui, Context context){
        super.ui = ui;
        super.context = context;
        super.tag = this.getClass().getSimpleName();
    }

    private int getRealPos(int rawPos){
        return ((int)(rawPos * ratio));
    }

    @Override
    public void init(int halfStageWidth, int stageHeight) {
        super.halfStageWidth = halfStageWidth;
        super.stageHeight = stageHeight;
        super.sleepTime = Const.SLEEP_TIME_BOY;

        Bitmap boyRes = BitmapFactory.decodeResource(context.getResources(), R.mipmap.girl);
        int width = boyRes.getWidth();//1583
        ratio = width * 1.0 / RAW_WIDTH;//1.5

        //由于已知的 男孩图像width=151，height=291是原始尺寸，跟加载到手机上的尺寸不一样，所以需要换算；
        super.uiWidth = ((int)(RAW_GIRL_WIDTH * ratio));
        super.uiHeight = ((int)(RAW_GIRL_HEIGHT * ratio));

        ViewGroup.LayoutParams lp = ui.getLayoutParams();
        lp.width = uiWidth;
        lp.height = uiHeight;
        ui.setLayoutParams(lp);
        ui.setX(halfStageWidth-uiWidth + 30);
        ui.setY((float) (stageHeight * Const.BOY_INIT_MARGIN_RATIO_2_TOP - uiHeight));
        //ui.setBottom((int) (stageHeight * Const.GIRL_INIT_MARGIN_RATIO_2_TOP));


        walkingRes.add(getBitmapFromSrc(boyRes, 453, 0));
        walkingRes.add(getBitmapFromSrc(boyRes, 904, 0));
        walkingRes.add(getBitmapFromSrc(boyRes, 451, 0));
        walkingRes.add(getBitmapFromSrc(boyRes, 753, 0));
        //turnRes.add(getBitmapFromSrc(boyRes, 300, 0));
        standRes = getBitmapFromSrc(boyRes, 453, 291);

        curRes = standRes;
        ((ImageView)ui).setImageBitmap(curRes);
    }

    private Bitmap getBitmapFromSrc(Bitmap boyRes, int x, int y){
        return Bitmap.createBitmap(boyRes, getRealPos(x), getRealPos(y), uiWidth, uiHeight);
    }

    @Override
    public void startNextPhase(Informer informer) {
        super.informer =informer;

        int destX = ((int)(Const.RATIO_GIRL_X_TO[Var.curPhase] * super.halfStageWidth));
        int destY = (int) (super.getCurY() - ((int)(Const.RATIO_BOY_Y_DISTANCE[Var.curPhase] * super.stageHeight)));
        boolean toRight = false;
        boolean toBottom = false;
        int time = Const.TIMES[Var.curPhase];

        super.doMove(time, destX, destY, toRight, toBottom);
    }

    @Override
    public void updateBg() {
        if(walkResIndex < walkingRes.size() - 1){
            walkResIndex ++;
        }else {
            walkResIndex = 0;
        }
        curRes = walkingRes.get(walkResIndex);
        ((ImageView)ui).setImageBitmap(curRes);
    }

    @Override
    public void react2PhaseDone(){
        //super.react2PhaseDone();
        if(Var.curPhase == Const.PHASE_START_WALK_2_SCENE2){
            curRes = standRes;
            ((ImageView)ui).setImageBitmap(curRes);
        }
    }

}
