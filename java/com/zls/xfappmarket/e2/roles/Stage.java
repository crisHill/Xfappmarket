package com.zls.xfappmarket.e2.roles;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.zls.xfappmarket.R;

/**
 * Created by oop on 2018/2/11.
 */

public class Stage extends Actor{

    public Stage(Context context, View ui, int uiWidth, int uiHeight) {
        super(context, ui, uiWidth, uiHeight);
        init();
    }

    @Override
    protected void init() {
        ViewGroup.LayoutParams lp = ui.getLayoutParams();
        lp.width = uiWidth * 2;
        ui.setLayoutParams(lp);

        View stageBg1 = ui.findViewById(R.id.stageBg1);
        View stageBg2 = ui.findViewById(R.id.stageBg2);

        ViewGroup.LayoutParams lp1 = stageBg1.getLayoutParams();
        lp1.width = uiWidth;
        stageBg1.setLayoutParams(lp1);

        ViewGroup.LayoutParams lp2 = stageBg2.getLayoutParams();
        lp2.width = uiWidth;
        stageBg2.setLayoutParams(lp2);
    }

    @Override
    public void onPhaseEnd() {

    }

    @Override
    public void onReset() {
        ui.setX(0);
    }
}
