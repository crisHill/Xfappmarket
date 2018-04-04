package com.zls.xfappmarket.e3.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.zls.xfappmarket.R;
import com.zls.xfappmarket.e3.beans.BgChangeParam;
import com.zls.xfappmarket.e3.beans.MoveParam;
import com.zls.xfappmarket.e3.data.Const;
import com.zls.xfappmarket.e3.roles.Human;
import com.zls.xfappmarket.e3.roles.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oop on 2018/3/6.
 */

public class CommonUtil {

    public static MoveParam getTotal(List<MoveParam> moveParamList){
        if(moveParamList == null || moveParamList.size() == 0){
            return null;
        }
        MoveParam result = new MoveParam();
        for (MoveParam param : moveParamList){
            result.setDx(result.getDx() + param.getDx());
            result.setDy(result.getDy() + param.getDy());
            result.setTime(result.getTime() + param.getTime());
        }
        return result;
    }

    public static Stage createStage(Context context){
        Stage stage = new Stage(context, GlbDataHolder.halfStageWidth, GlbDataHolder.stageHeight);
        return stage;
    }

    public static Human createBoy(Context context){
        //原始图片的尺寸读取到bitmap的尺寸发生了变化，而截图根据的是原始坐标，所以需要根据比例变换后再截取
        Bitmap rawRes = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boy);
        int width = rawRes.getWidth();
        int height = rawRes.getHeight();
        double ratioWidth = width * 1.0 / Const.RawDim.BOY_RES_WIDTH;
        double ratioHeight = height * 1.0 / Const.RawDim.BOY_RES_HEIGHT;

        int uiWidth = ((int)(Const.RawDim.UI_WIDTH * ratioWidth));
        int uiHeight = ((int)(Const.RawDim.UI_HEIGHT * ratioHeight));

        //走动时需要的图片
        List<BitmapDrawable> walkRes = new ArrayList<>();
        walkRes.add(getFromRaw(context, rawRes, 453, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));
        walkRes.add(getFromRaw(context, rawRes, 904, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));
        walkRes.add(getFromRaw(context, rawRes, 451, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));
        walkRes.add(getFromRaw(context, rawRes, 753, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));

        BitmapDrawable stillRes = getFromRaw(context, rawRes, 453, 291, uiWidth, uiHeight, ratioWidth, ratioHeight);

        float initX = 0;
        float initY = (float) (Const.HUMAN_INIT_TO_TOP_RATIO * GlbDataHolder.stageHeight - uiHeight);

        Human boy = new Human(context, uiWidth, uiHeight, stillRes, initX, initY);
        boy.initBgAndLocation();
        boy.setWalkBpRes(walkRes);
        return boy;
    }

    public static Human createGirl(Context context){
        //原始图片的尺寸读取到bitmap的尺寸发生了变化，而截图根据的是原始坐标，所以需要根据比例变换后再截取
        Bitmap rawRes = BitmapFactory.decodeResource(context.getResources(), R.mipmap.girl);
        int width = rawRes.getWidth();
        int height = rawRes.getHeight();
        double ratioWidth = width * 1.0 / Const.RawDim.GIRL_RES_WIDTH;
        double ratioHeight = height * 1.0 / Const.RawDim.GIRL_RES_HEIGHT;

        int uiWidth = ((int)(Const.RawDim.UI_WIDTH * ratioWidth));
        int uiHeight = ((int)(Const.RawDim.UI_HEIGHT * ratioHeight));

        //转身时需要的图片
        List<BitmapDrawable> turnRes = new ArrayList<>();
        turnRes.add(getFromRaw(context, rawRes, 151, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));
        turnRes.add(getFromRaw(context, rawRes, 906, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));
        turnRes.add(getFromRaw(context, rawRes, 0, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));
        turnRes.add(getFromRaw(context, rawRes, 302, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));
        turnRes.add(getFromRaw(context, rawRes, 453, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));
        turnRes.add(getFromRaw(context, rawRes, 604, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));

        BitmapDrawable stillRes = getFromRaw(context, rawRes, 755, 0, uiWidth, uiHeight, ratioWidth, ratioHeight);

        float initX = GlbDataHolder.halfStageWidth * 3 / 2;
        float initY = (float) ((Const.HUMAN_INIT_TO_TOP_RATIO - Const.BRIDGE_HEIGHT_RATIO) * GlbDataHolder.stageHeight - uiHeight);

        Human girl = new Human(context, uiWidth, uiHeight, stillRes, initX, initY);
        girl.initBgAndLocation();
        girl.setTurnBpRes(turnRes);

        return girl;
    }

    private static BitmapDrawable getFromRaw(Context context, Bitmap rawBitmap, int rawX, int rawY, int uiWidth, int uiHeight, double ratioWidth, double ratioHeight){
        Bitmap bitmap = Bitmap.createBitmap(rawBitmap, ((int)(rawX * ratioWidth)), ((int)(rawY * ratioHeight)), uiWidth, uiHeight);
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static List<MoveParam> getMoveParamList(int roleType, int fromPhase, int toPhase){
        if(fromPhase > toPhase){
            return null;
        }
        List<MoveParam> list = new ArrayList<>();
        for (int i = fromPhase; i <= toPhase; i++) {
            MoveParam mp = new MoveParam();

            mp.setTime(Const.PHASE_TIME[i]);
            mp.setDx(Const.MoveBy.X[roleType][i] * GlbDataHolder.halfStageWidth);
            mp.setDy(0 - Const.MoveBy.Y[roleType][i] * GlbDataHolder.stageHeight);

            list.add(mp);
        }
        return list;
    }

    public static BgChangeParam getBgChangeParam(List<BitmapDrawable> list, int fromPhase, int toPhase){
        if(list == null || list.size() == 0){
            return null;
        }

        BgChangeParam bgChangeParam = new BgChangeParam();
        bgChangeParam.setTempResList(list);
        bgChangeParam.setRefreshGapTime(Const.BG_REFRESH_GAP_TIME);
        bgChangeParam.setTime(getTime(fromPhase, toPhase));

        return bgChangeParam;
    }

    private static long getTime(int fromPhase, int toPhase){
        if(fromPhase > toPhase){
            return 0;
        }
        long time = 0;
        for (int i = fromPhase; i <= toPhase; i++) {
            time += Const.PHASE_TIME[i];
        }
        return time;
    }

}
