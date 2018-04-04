package com.zls.xfappmarket.e3.roles;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zls.xfappmarket.R;
import com.zls.xfappmarket.e3.beans.MoveParam;
import com.zls.xfappmarket.e3.itf.Movable;
import com.zls.xfappmarket.e3.util.CommonUtil;
import com.zls.xfappmarket.e3.util.MoveAnimHelper;

import java.util.List;

/**
 * Created by oop on 2018/2/11.
 */

public class Stage extends MovableRole{

    private ImageView part1, part2;

    public Stage(Context context, int myWidth, int myHeight) {
        super(context, myWidth, myHeight);
    }

    /*@Override
    protected void init() {
        ui = LayoutInflater.from(context).inflate(R.layout.view_stage, null);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.width = myWidth * 2;
        lp.height = myHeight;
        ui.setLayoutParams(lp);

        part1 = (ImageView) ui.findViewById(R.id.part1);
        part2 = (ImageView) ui.findViewById(R.id.part2);

        ViewGroup.LayoutParams lp1 = part1.getLayoutParams();
        lp1.width = myWidth;
        lp1.height = myHeight;
        part1.setLayoutParams(lp1);


        ViewGroup.LayoutParams lp2 = part2.getLayoutParams();
        lp2.width = myWidth;
        lp2.height = myHeight;
        part2.setLayoutParams(lp2);
    }*/

    @Override
    protected void init() {
        ui = new RelativeLayout(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(myWidth * 2, myHeight);
        ui.setLayoutParams(lp);

        part1 = new ImageView(context);
        part2 = new ImageView(context);
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(myWidth, myHeight);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(myWidth, myHeight);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        part1.setLayoutParams(lp1);
        part2.setLayoutParams(lp2);

        part1.setImageResource(R.mipmap.bg1);
        part1.setScaleType(ImageView.ScaleType.FIT_XY);
        part2.setImageResource(R.mipmap.bg2);
        part2.setScaleType(ImageView.ScaleType.FIT_XY);

        ((ViewGroup)ui).addView(part1, 0);
        ((ViewGroup)ui).addView(part2, 1);
    }
}
