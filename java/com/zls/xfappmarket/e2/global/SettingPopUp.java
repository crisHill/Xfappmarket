package com.zls.xfappmarket.e2.global;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.zls.xfappmarket.R;

/**
 * Created by oop on 2018/2/28.
 */

public class SettingPopUp{

    private View popView;
    private PopupWindow popupWindow;
    private Button confirm;

    private View root;

    public SettingPopUp(Context context, int width, View root){
        this.root = root;

        popView = LayoutInflater.from(context).inflate(R.layout.setting_popup, null);
        popView.setFocusable(true);
        popView.setFocusableInTouchMode(true);
        popupWindow = new PopupWindow(popView, (int) (width * 0.5), ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popupwindow_anim_style_from_left);

        confirm = (Button) popView.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });
    }

    public void show(){
        popupWindow.showAtLocation(root, Gravity.LEFT, 0, 0);
    }

    public void hide(){
        popupWindow.dismiss();
    }

}
