package com.zls.xfappmarket.e3.roles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zls.xfappmarket.R;
import com.zls.xfappmarket.e3.beans.MoveParam;
import com.zls.xfappmarket.e3.itf.Movable;
import com.zls.xfappmarket.e3.util.CommonUtil;
import com.zls.xfappmarket.e3.util.MoveAnimHelper;

import java.util.List;

/**
 * Created by oop on 2018/2/11.
 */

public abstract class MovableRole extends Role implements Movable{

    public MovableRole(Context context, int myWidth, int myHeight) {
        super(context, myWidth, myHeight);
    }


    @Override
    public void move(List<MoveParam> moveParamList) {
        MoveAnimHelper.helpAnimateMove(ui, moveParamList);
    }

}
