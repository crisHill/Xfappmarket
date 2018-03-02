package com.zls.xfappmarket.e2.global;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zls.xfappmarket.R;
import com.zls.xfappmarket.e2.beans.Location;
import com.zls.xfappmarket.e2.data.Const;
import com.zls.xfappmarket.e2.roles.Actor;
import com.zls.xfappmarket.e2.roles.Boy;
import com.zls.xfappmarket.e2.roles.Girl;
import com.zls.xfappmarket.e2.roles.Human;
import com.zls.xfappmarket.e2.roles.Stage;
import com.zls.xfappmarket.e2.util.GlbDataHolder;

import java.util.ArrayList;
import java.util.List;

import static com.zls.xfappmarket.e2.data.Const.HUMAN_INIT_TO_TOP_RATIO;

/**
 * Created by oop on 2018/2/11.
 */

public class ActorCreator {

    public static Human createBoy(Context context, ViewGroup root){
        ImageView ui = (ImageView) root.findViewById(R.id.boy);

        //原始图片的尺寸读取到bitmap的尺寸发生了变化，而截图根据的是原始坐标，所以需要根据比例变换后再截取
        Bitmap rawRes = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boy);
        int width = rawRes.getWidth();
        int height = rawRes.getHeight();
        double ratioWidth = width * 1.0 / Const.RawDim.BOY_RES_WIDTH;
        double ratioHeight = height * 1.0 / Const.RawDim.BOY_RES_HEIGHT;

        int uiWidth = ((int)(Const.RawDim.UI_WIDTH * ratioWidth));
        int uiHeight = ((int)(Const.RawDim.UI_HEIGHT * ratioHeight));

        List<Bitmap> walkRes = new ArrayList<>();
        walkRes.add(getBitmapFromSrc(rawRes, 453, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));
        walkRes.add(getBitmapFromSrc(rawRes, 904, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));
        walkRes.add(getBitmapFromSrc(rawRes, 451, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));
        walkRes.add(getBitmapFromSrc(rawRes, 753, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));

        Bitmap stillRes = getBitmapFromSrc(rawRes, 453, 291, uiWidth, uiHeight, ratioWidth, ratioHeight);

        Location initLocation = new Location(0, (float) (HUMAN_INIT_TO_TOP_RATIO * GlbDataHolder.stageHeight - uiHeight));

        Human boy = new Boy(context, ui, uiWidth, uiHeight, walkRes, null, stillRes, initLocation);

        return boy;
    }
    public static Human createGirl(Context context, ViewGroup root){
        ImageView ui = (ImageView) root.findViewById(R.id.girl);

        //原始图片的尺寸读取到bitmap的尺寸发生了变化，而截图根据的是原始坐标，所以需要根据比例变换后再截取
        Bitmap rawRes = BitmapFactory.decodeResource(context.getResources(), R.mipmap.girl);
        int width = rawRes.getWidth();
        int height = rawRes.getHeight();
        double ratioWidth = width * 1.0 / Const.RawDim.GIRL_RES_WIDTH;
        double ratioHeight = height * 1.0 / Const.RawDim.GIRL_RES_HEIGHT;

        int uiWidth = ((int)(Const.RawDim.UI_WIDTH * ratioWidth));
        int uiHeight = ((int)(Const.RawDim.UI_HEIGHT * ratioHeight));

        List<Bitmap> turnRes = new ArrayList<>();
        turnRes.add(getBitmapFromSrc(rawRes, 151, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));
        turnRes.add(getBitmapFromSrc(rawRes, 906, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));
        turnRes.add(getBitmapFromSrc(rawRes, 0, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));
        turnRes.add(getBitmapFromSrc(rawRes, 302, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));
        turnRes.add(getBitmapFromSrc(rawRes, 453, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));
        turnRes.add(getBitmapFromSrc(rawRes, 604, 0, uiWidth, uiHeight, ratioWidth, ratioHeight));

        Bitmap stillRes = getBitmapFromSrc(rawRes, 755, 0, uiWidth, uiHeight, ratioWidth, ratioHeight);

        Location initLocation = new Location(GlbDataHolder.halfStageWidth - uiWidth, (float) (HUMAN_INIT_TO_TOP_RATIO * GlbDataHolder.stageHeight - uiHeight));

        Human girl = new Girl(context, ui, uiWidth, uiHeight, null, turnRes, stillRes, initLocation);

        return girl;
    }

    public static Stage createStage(Context context, ViewGroup root){
        View ui = root.findViewById(R.id.stageBg);
        Stage stage = new Stage(context, ui, GlbDataHolder.halfStageWidth, GlbDataHolder.stageHeight);
        return stage;
    }

    private static Bitmap getBitmapFromSrc(Bitmap boyRes, int rawX, int rawY, int uiWidth, int uiHeight, double ratioWidth, double ratioHeight){
        return Bitmap.createBitmap(boyRes, ((int)(rawX * ratioWidth)), ((int)(rawY * ratioHeight)), uiWidth, uiHeight);
    }

}
